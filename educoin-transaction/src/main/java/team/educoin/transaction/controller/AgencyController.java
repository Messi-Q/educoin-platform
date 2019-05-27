package team.educoin.transaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import team.educoin.common.controller.CommonResponse;
import team.educoin.transaction.fabric.AgencyFabricClient;
import team.educoin.transaction.fabric.FileFabricClient;
import team.educoin.transaction.pojo.AgencyInfo;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.pojo.UserInfo;
import team.educoin.transaction.pojo.Withdraw;
import team.educoin.transaction.service.AgencyService;
import team.educoin.transaction.service.FileService;
import team.educoin.transaction.util.FileUtil;
import team.educoin.transaction.util.UUIDutil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description: 处理机构用户访问
 * @author: PandaClark
 * @create: 2019-05-13
 */
@RestController
@RequestMapping("/agency")
@Api(value = "Agency API 接口", tags = "agency", description = "agency API 接口")
public class AgencyController {

    private String email = "ZjuEducation@email.com";

    @Autowired
    private AgencyService agencyService;
    @Autowired
    private FileService fileService;
    @Autowired
    private AgencyFabricClient agencyFabricClient;
    @Autowired
    private FileFabricClient fileFabricClient;

    @ApiOperation(value = "获取所有审核通过的提现记录")
    @RequestMapping( value = "/withdrawList", method = RequestMethod.GET )
    public CommonResponse checkedWithdrawRecords(){
        List<Withdraw> records = agencyService.getAgencyWithdrawRecords(email, 0);
        CommonResponse res = new CommonResponse(0, "success", records);
        return res;
    }

    @ApiOperation(value = "机构用户提现")
    @RequestMapping( value = "/withdraw", method = RequestMethod.POST )
    public CommonResponse withdraw(@RequestParam("amount") double amount) {
        CommonResponse res = new CommonResponse();

        // email 应当从 session 中拿，此处只是测试
        boolean success = agencyService.companyWithdraw(email, amount);
        if (success){
            res.setStatus(0);
            res.setMessage("success");
            res.setData("提现成功");
        } else {
            res.setStatus(1);
            res.setMessage("failed");
            res.setData("提现失败");
        }
        return res;
    }

    /**
     * =============================================================
     * @desc 机构用户查看待审核的资源列表
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "机构用户查看待审核的资源列表", notes = "机构用户查看待审核的资源列表")
    @RequestMapping( value = "/service/unchecked", method = RequestMethod.GET )
    public CommonResponse resourceListW(){
        List<FileInfo> files = fileService.getUnCheckedServiceListById(email);
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }

    /**
     * =============================================================
     * @desc 机构用户查看已审核通过的资源列表
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "机构用户查看已审核通过的资源列表", notes = "机构用户查看已审核通过的资源列表")
    @RequestMapping( value = "/service/checked", method = RequestMethod.GET )
    public CommonResponse resourceListY(){
        List<FileInfo> files = fileService.getCheckedServiceListById(email);
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }

    /**
     * =============================================================
     * @desc 机构用户查看已审核拒绝的资源列表
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "机构用户查看已审核拒绝的资源列表", notes = "机构用户查看已审核拒绝的资源列表")
    @RequestMapping( value = "/service/reject", method = RequestMethod.GET )
    public CommonResponse resourceListR(){
        List<FileInfo> files = fileService.getRejectServiceListById(email);
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }

    /**
     * =============================================================
     * @desc 机构用户购买所有权
     * @author PandaClark
     * @date 2019/5/15 7:11 PM
     * @param
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "机构用户购买所有权")
    @RequestMapping( value = "/service/consume/{id}", method = RequestMethod.POST )
    public CommonResponse consume( @PathVariable("id") String id ){
        CommonResponse res = new CommonResponse();
        AgencyInfo agency = agencyService.getAgencyById(email);
        FileInfo fileInfo = fileService.getFileInfoById(id);
        if (fileInfo.getFileReadPrice() > agency.getAccountBalance() ){
            res.setStatus(1);
            res.setMessage("failed");
            res.setData("余额不足");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("$class","org.education.CompanyBuyOnwership");
            map.put("serviceID",id);
            map.put("company",email);
            try {
                Map<String, Object> map1 = agencyFabricClient.agencyBuyOwnership(map);
                agencyService.agencyBuyOwnership(email,id,fileInfo);
                res.setStatus(0);
                res.setMessage("success");
                res.setData(map1);
            } catch (Exception e) {
                e.printStackTrace();
                res.setStatus(1);
                res.setMessage("failed");
                res.setData(e.getMessage());
            }
        }
        return res;
    }


    /**
     * =============================================================
     * @desc  删除资源
     * @author PandaClark
     * @date 2019/5/17 11:46 AM
     * @param id 资源ID
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/service/delete/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "删除资源", notes = "根据资源ID删除资源")
    public CommonResponse deleteService(@PathVariable("id") String id) {
        CommonResponse res = null;
        try {
            fileFabricClient.deleteService(id);
            fileService.deleteService(id);
            res = new CommonResponse(0, "success", "删除成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }


    /**
     * =============================================================
     * @desc 修改资源信息
     * @author PandaClark
     * @date 2019/5/17 12:44 PM
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/service/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改资源信息", notes = "根据资源ID修改资源信息")
    public CommonResponse updateServiceInfo(@PathVariable("id") String id,
                                            @RequestParam("fileTitle") String fileTitle,
                                            @RequestParam("fileImage") String fileImage,
                                            @RequestParam("fileDescription") String fileDescription,
                                            @RequestParam("fileReadPrice") Double fileReadPrice,
                                            @RequestParam("fileOwnerShipPrice") Double fileOwnerShipPrice,
                                            @RequestParam("fileKeyWord") String fileKeyWord,
                                            @RequestParam("fileContentType") String fileContentType) {

        CommonResponse res = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("$class","org.education.Service");
            map.put("serviceID",id);
            map.put("serviceName",fileTitle);
            map.put("readPrice",fileReadPrice);
            map.put("ownershipPrice",fileOwnerShipPrice);
            map.put("company",email);

            fileFabricClient.updateService(id, map);

            FileInfo fileInfo = new FileInfo(id, fileTitle, fileImage, fileDescription, fileReadPrice, fileOwnerShipPrice, fileKeyWord, fileContentType);
            fileService.updateFileInfo(fileInfo);

            res = new CommonResponse(0, "success", "修改资源信息成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;

    }


    /**
     * =============================================================
     * @desc 修改资源阅读权价格
     * @author PandaClark
     * @date 2019/5/17 12:09 PM
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/service/updateReadPrice/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改资源阅读权价", notes = "根据资源ID修改资源阅读权价")
    public CommonResponse updateReadPrice(@PathVariable("id") String id,
                                          @RequestParam("fileReadPrice") Double fileReadPrice) {

        CommonResponse res = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("$class","org.education.UpdateServicereadPrice");
            map.put("serviceID",id);
            map.put("readPrice",fileReadPrice);

            fileFabricClient.updateServiceReadPrice(map);
            fileService.updateFileReadPrice(id,fileReadPrice);
            res = new CommonResponse(0, "success", "修改资源阅读权价格成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }


    /**
     * =============================================================
     * @desc 修改资源所有权价格
     * @author PandaClark
     * @date 2019/5/17 12:31 PM
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/service/updateOwnershipPrice/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "修改资源所有权价", notes = "根据资源ID修改资源所有权价")
    public CommonResponse updateServiceOwnerShipPrice(@PathVariable("id") String id,
                                              @RequestParam("fileOwnerShipPrice") Double fileOwnerShipPrice) {

        CommonResponse res = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("$class","org.education.UpdateServiceownershipPrice");
            map.put("serviceID",id);
            map.put("ownershipPrice",fileOwnerShipPrice);

            fileFabricClient.updateServiceOwnershipPrice(map);
            fileService.updateFileOwnershipPrice(id,fileOwnerShipPrice);
            res = new CommonResponse(0, "success", "修改资源所有权价格成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;

    }



    /**
     * =============================================================
     * @desc 注册新资源
     * @author Messi-Q
     * @date Modified by PandaClark in 2019/5/17 6:34 PM
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ApiOperation(value = "上传资源", notes = "上传资源文件，提交资源基本信息")
    public CommonResponse uploadService(@RequestParam("fileTitle") String fileTitle,
                                        @RequestParam("fileImage") String fileImage,
                                        @RequestParam("fileDescription") String fileDescription,
                                        @RequestParam("fileReadPrice") Double fileReadPrice,
                                        @RequestParam("fileOwnerShipPrice") Double fileOwnerShipPrice,
                                        @RequestParam("fileKeyWord") String fileKeyWord,
                                        @RequestParam("fileContentType") String fileContentType,
                                        @RequestParam("fileInitialProvider") String fileInitialProvider,
                                        @RequestParam MultipartFile file) throws IOException {

        CommonResponse res = null;

        if (file.getSize() == 0 || StringUtils.isEmpty(fileTitle) || StringUtils.isEmpty(fileImage) || StringUtils.isEmpty(fileDescription) || StringUtils.isEmpty(fileReadPrice)
                || StringUtils.isEmpty(fileOwnerShipPrice) || StringUtils.isEmpty(fileKeyWord) || StringUtils.isEmpty(fileContentType) || StringUtils.isEmpty(fileInitialProvider))
        {
            res = new CommonResponse(1,"failed","请补全资源信息再提交");
        }


        // 获取文件相关信息
        // 获取文件的MD5码
        String fileMD5 = DigestUtils.md5DigestAsHex(file.getBytes());
        // 获取文件的后缀名
        String fileName = file.getOriginalFilename();
        String type = fileName.substring(fileName.lastIndexOf("."));
        // 获取文件ID，随机产生
        String fileId = UUIDutil.getUUID();


        // 文件上传操作
        Files.copy(file.getInputStream(), Paths.get(FileUtil.UPLOAD_DIR,fileName), StandardCopyOption.REPLACE_EXISTING);


        // 资源注册操作
        FileInfo fileInfo = new FileInfo(fileId, fileInitialProvider, fileInitialProvider, fileTitle, fileImage,
                fileDescription, fileReadPrice, fileOwnerShipPrice, fileName, fileKeyWord, fileContentType,
                type, FileUtil.getFormatSize(file.getSize()),0);

        try {
            // 机构用户上传资源时，信息不上链，基本信息只存在数据库里，只有审核通过的资源才上链
            // Map<String, Object> map = new HashMap<>();
            // map.put("$class","org.education.RegisterService");
            // map.put("serviceID", fileId);
            // map.put("serviceName", fileTitle);
            // map.put("readPrice", fileReadPrice);
            // map.put("ownershipPrice", fileOwnerShipPrice);
            // map.put("company", fileInitialProvider);
            //
            // fileFabricClient.registerService(map);
            fileService.registerService(fileInfo);
            res = new CommonResponse(0, "success", "注册新资源成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }

        return res;

    }



    /**
     * =============================================================
     * @desc 批量上传资源文件
     * @author Messi-Q
     * @date Modified by PandaClark in 2019/5/17 6:34 PM
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/uploadBatch", method = RequestMethod.POST)
    @ApiOperation(value = "批量上传资源", notes = "批量上传资源文件")
    public CommonResponse uploadBatch(HttpServletRequest request) throws IOException {

        CommonResponse res = new CommonResponse(0,"success","批量上传资源文件成功");
        String errMsg = "";

        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        File path = new File(ResourceUtils.getURL("classpath:").getPath());
        File upload = new File(path.getAbsolutePath(), "upload/");
        if (!upload.exists()) upload.mkdirs();

        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(upload.getPath() + file.getOriginalFilename())));//设置文件路径及名字
                    stream.write(bytes);// 写入
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    res.setStatus(1);
                    res.setMessage("failed");
                    errMsg += "第 " + i + " 个文件上传失败 ==> " + e.getMessage();
                }
            } else {
                res.setStatus(1);
                res.setMessage("failed");
                errMsg += "第 " + i + " 个文件上传失败因为文件为空";
            }
        }
        if ( !StringUtils.isEmpty(errMsg) ){
            res.setData(errMsg);
        }
        return res;
    }


    /**
     * =============================================================
     * @desc 根据文件id下载文件
     * @author Messi-Q
     * @date Modified by PandaClark in 2019/5/17 6:34 PM
     * @return java.lang.String
     * =============================================================
     */
    @RequestMapping(value = "/download/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "下载资源", notes = "根据文件id下载文件")
    public CommonResponse downloadService(@PathVariable("id") String id, HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException, UnsupportedEncodingException {

        CommonResponse res = new CommonResponse(0, "success", "资源下载成功");

        // 根据文件id获取文件名
        FileInfo fileInfo = fileService.getFileInfoById(id);
        String filename = fileInfo.getFileName();


        response.setContentType("application/force-download");  //设置强制下载不打开
        response.addHeader("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));// 设置文件名

        try {
            // 文件下载操作
            StreamUtils.copy(new FileInputStream(new File(FileUtil.UPLOAD_DIR) + "/" + filename), response.getOutputStream());
        } catch (Exception e){
            e.printStackTrace();
            res.setStatus(1);
            res.setMessage("failed");
            res.setData(e.getMessage());
        }

        return res;

    }

    // @RequestMapping(value = "/testup", method = RequestMethod.POST)
    // @ApiOperation(value = "上传资源测试", notes = "上传资源测试")
    // public String uploadTest(@RequestParam MultipartFile file,HttpServletResponse response) throws IOException {
    //
    //     // Files.copy(file.getInputStream(),new File("").toPath(), StandardCopyOption.REPLACE_EXISTING);
    //     // StreamUtils.copy(new FileInputStream(new File("")),response.getOutputStream());
    //
    //     // int dotPos = file.getOriginalFilename().lastIndexOf(".");
    //     // String fileName = file.getOriginalFilename().substring(dotPos + 1).toLowerCase();
    //     String filename = file.getOriginalFilename();
    //     Files.copy(file.getInputStream(), Paths.get(FileUtil.UPLOAD_DIR,filename), StandardCopyOption.REPLACE_EXISTING);
    //
    //     return "cha kan finder";
    // }

    // @RequestMapping(value = "/testdown", method = RequestMethod.POST)
    // @ApiOperation(value = "下载资源测试", notes = "下载资源测试")
    // public String downloadTest(@RequestParam("name") String filename,HttpServletResponse response) throws IOException {
    //
    //     response.setContentType("application/force-download");  //设置强制下载不打开
    //     response.addHeader("Content-Disposition", "attachment;fileName=" + new String(filename.getBytes("UTF-8"), "iso-8859-1"));// 设置文件名
    //
    //     try {
    //         StreamUtils.copy(new FileInputStream(new File(FileUtil.UPLOAD_DIR) + "/" + filename), response.getOutputStream());
    //     } catch (Exception e){
    //         e.printStackTrace();
    //     }
    //
    //     return "下载";
    // }

}
