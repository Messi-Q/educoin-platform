package team.educoin.transaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.educoin.common.controller.CommonResponse;
import team.educoin.transaction.dto.CentralBankDto;
import team.educoin.transaction.dto.ContractDto;
import team.educoin.transaction.fabric.AdminFabricClient;
import team.educoin.transaction.fabric.AgencyFabricClient;
import team.educoin.transaction.fabric.FileFabricClient;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.pojo.Recharge;
import team.educoin.transaction.pojo.Withdraw;
import team.educoin.transaction.service.AdminService;
import team.educoin.transaction.service.AgencyService;
import team.educoin.transaction.service.FileService;
import team.educoin.transaction.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description: 管理员Controller
 * @author: Messi-Q
 * @create: 2019-05-27
 */
@RestController
@RequestMapping("/admin")
@Api(value = "Admin API 接口", tags = "admin", description = "admin API 接口")
public class AdminController {


    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private FileService fileService;
    @Autowired
    private AdminFabricClient adminFabricClient;
    @Autowired
    private AgencyFabricClient agencyFabricClient;
    @Autowired
    private FileFabricClient fileFabricClient;

    String admin = "clark@zju.incas";


    /**
     * =============================================================
     * @desc 管理员获取所有待审核用户充值列表
     * @author PandaClark
     * @date 2019/5/13 5:40 PM
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员获取所有未审核用户充值记录")
    @ResponseBody
    @RequestMapping( value = "/rechargeList", method = RequestMethod.GET )
    public CommonResponse unCheckedRechargeList(){
        List<Recharge> rechargeList = adminService.getUnCheckedRechargeList();
        CommonResponse res = new CommonResponse(0, "success", rechargeList);
        return res;
    }

    /**
     * =============================================================
     * @desc 管理员获取所有待审核机构提现列表
     * @author PandaClark
     * @date 2019/5/13 5:53 PM
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员获取所有未审核机构提现记录")
    @ResponseBody
    @RequestMapping( value = "/withdrawList", method = RequestMethod.GET )
    public CommonResponse unCheckedWithdrawList(){
        List<Withdraw> withdrawList = adminService.getUnCheckedWithdrawList();
        CommonResponse res = new CommonResponse(0, "success", withdrawList);
        return res;
    }


    /**
     * =============================================================
     * @desc 同意用户充值
     * @author PandaClark
     * @date 2019/5/12 5:11 PM
     * @param rechargeId 充值记录ID
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员同意用户充值")
    @ResponseBody
    @RequestMapping( value = "/rechargeY", method = RequestMethod.POST )
    public CommonResponse acceptUserRecharge( @RequestParam("rechargeId") String rechargeId ){

        CommonResponse res = new CommonResponse();
        // 先默认失败
        res.setStatus(1);
        res.setMessage("failed");
        // mysql 查出相关记录信息
        Recharge record = userService.getRechargeRecordById(rechargeId);
        if ( record == null){
            res.setData("没有改充值记录");
        } else if ( record.getIfChecked() != 0 ){
            res.setMessage("该记录已被审核过了！请勿重复审核");
        } else {
            Map<String, String> rechargeInfo = new HashMap<>();
            rechargeInfo.put("$class","org.education.CheckUserRecharge");
            rechargeInfo.put("rechargeID",record.getPaymentId());
            rechargeInfo.put("paymentid",record.getPaymentMethod());
            // fabric 发 post
            Map<String, String> result = adminFabricClient.CheckUserRechargeFabric(rechargeInfo);
            System.out.println(result);
            // 修改 mysql 字段
            adminService.acceptUserRecharge(record.getPaymentId(), admin);
            res.setStatus(0);
            res.setMessage("success");
            res.setData("已审核通过");
        }
        return res;
    }

    /**
     * =============================================================
     * @desc 拒绝用户充值
     * @author PandaClark
     * @date 2019/5/12 5:17 PM
     * @param rechargeId 充值记录ID
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员拒绝用户充值")
    @ResponseBody
    @RequestMapping( value = "/rechargeR", method = RequestMethod.POST )
    public CommonResponse rejectUserRecharge( @RequestParam("rechargeId") String rechargeId ){

        CommonResponse res = new CommonResponse();
        // 先默认失败
        res.setStatus(1);
        res.setMessage("failed");
        // mysql 查出相关记录信息
        Recharge record = userService.getRechargeRecordById(rechargeId);
        if ( record == null){
            res.setData("没有该充值记录");
        } else if ( record.getIfChecked() != 0 ){
            res.setMessage("该记录已被审核过了！请勿重复审核");
        } else {
            Map<String, String> rechargeInfo = new HashMap<>();
            rechargeInfo.put("$class","org.education.RejectUserRecharge");
            rechargeInfo.put("rechargeID",record.getPaymentId());
            // fabric 发 post
            Map<String, String> result = adminFabricClient.RejectUserRechargeFabric(rechargeInfo);
            System.out.println(result);
            // 修改 mysql 字段
            adminService.rejectUserRecharge(record.getPaymentId(), admin);
            res.setStatus(0);
            res.setMessage("success");
            res.setData("已审核拒绝");
        }
        return res;
    }


    /**
     * =============================================================
     * @desc 同意机构用户提现
     * @author PandaClark
     * @date 2019/5/12 5:17 PM
     * @param withdrawId 提现记录的 payment_id
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员同意机构用户提现")
    @ResponseBody
    @RequestMapping( value = "/withdrawY", method = RequestMethod.POST )
    public CommonResponse acceptCompanyWithdraw( @RequestParam("withdrawId") String withdrawId){

        CommonResponse res = new CommonResponse();
        // 先默认失败
        res.setStatus(1);
        res.setMessage("failed");
        // mysql 查出相关记录信息
        Withdraw record = agencyService.getWithdrawRecordById(withdrawId);
        if ( record == null){
            res.setData("没有该提现记录");
        } else if ( record.getIfChecked() != 0 ){
            res.setMessage("该记录已被审核过了！请勿重复审核");
        } else {
            Map<String, String> rechargeInfo = new HashMap<>();
            rechargeInfo.put("$class","org.education.CheckCompanyWithdraw");
            rechargeInfo.put("tokenWithdrawID",record.getPaymentId());
            rechargeInfo.put("paymentid",record.getPaymentMethod());
            // fabric 发 post
            Map<String, String> result = agencyFabricClient.CheckAgencyWithdrawFabric(rechargeInfo);
            System.out.println(result);
            // 修改 mysql 字段
            adminService.acceptCompanyWithdraw(record.getPaymentId(), admin);
            res.setStatus(0);
            res.setMessage("success");
            res.setData("已审核通过");
        }
        return res;
    }


    /**
     * =============================================================
     * @desc 拒绝机构用户提现
     * @author PandaClark
     * @date 2019/5/12 5:18 PM
     * @param withdrawId 提现记录的 payment_id
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员拒绝机构用户提现")
    @ResponseBody
    @RequestMapping( value = "/withdrawR", method = RequestMethod.POST )
    public CommonResponse rejectCompanyWithdraw( @RequestParam("withdrawId") String withdrawId){

        CommonResponse res = new CommonResponse();
        // 先默认失败
        res.setStatus(1);
        res.setMessage("failed");
        // mysql 查出相关记录信息
        Withdraw record = agencyService.getWithdrawRecordById(withdrawId);
        if ( record == null){
            res.setData("没有改提现记录");
        } else if ( record.getIfChecked() != 0 ){
            res.setMessage("该记录已被审核过了！请勿重复审核");
        } else {
            Map<String, String> rechargeInfo = new HashMap<>();
            rechargeInfo.put("$class","org.education.RejectCompanyWithdraw");
            rechargeInfo.put("tokenWithdrawID",record.getPaymentId());
            // fabric 发 post
            Map<String, Object> result = agencyFabricClient.RejectAgencyWithdrawFabric(rechargeInfo);
            System.out.println(result);
            // 修改 mysql 字段
            adminService.rejectCompanyWithdraw(record.getPaymentId(), admin);
            res.setStatus(0);
            res.setMessage("success");
            res.setData("已审核拒绝");
        }
        return res;
    }


    /**
     * =============================================================
     * @desc 查看中央账户
     * @author PandaClark
     * @date 2019/5/12 5:26 PM
     * =============================================================
     */
    @ApiOperation(value = "获取中央账户信息")
    @ResponseBody
    @RequestMapping( value = "/centralBank", method = RequestMethod.GET )
    public CommonResponse centralBank(){
        CommonResponse res = new CommonResponse();

        CentralBankDto info = adminService.getCentralBankInfo();

        res.setStatus(0);
        res.setMessage("success");
        res.setData(info);
        return res;
    }

    /**
     * =============================================================
     * @desc 查看权益分配合约
     * @author PandaClark
     * @date 2019/5/12 5:27 PM
     * =============================================================
     */
    @ApiOperation(value = "获取权益分配合约")
    @ResponseBody
    @RequestMapping( value = "/contract", method = RequestMethod.GET )
    public CommonResponse contract(){
        CommonResponse res = new CommonResponse();

        ContractDto info = adminService.getContractInfo();
        res.setStatus(0);
        res.setMessage("success");
        res.setData(info);
        return res;
    }


    /**
     * =============================================================
     * @desc 查看所有资源列表
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "查看所有资源列表", notes = "查看所有资源列表")
    @ResponseBody
    @RequestMapping( value = "/resourcelist", method = RequestMethod.GET )
    public CommonResponse resourceList(){
        List<FileInfo> files = fileService.getServiceList();
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }

    /**
     * =============================================================
     * @desc 管理员查看待审核列表
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员查看待审核列表", notes = "管理员查看待审核列表")
    @ResponseBody
    @RequestMapping( value = "/resourcelistW", method = RequestMethod.GET )
    public CommonResponse resourceListW(){
        List<FileInfo> files = fileService.getUnCheckedServiceList();
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }

    /**
     * =============================================================
     * @desc 管理员查看审核通过记录
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员查看审核通过记录", notes = "管理员查看审核通过记录")
    @ResponseBody
    @RequestMapping( value = "/resourcelistY", method = RequestMethod.GET )
    public CommonResponse resourceListY(){
        List<FileInfo> files = fileService.getCheckedServiceList();
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }

    /**
     * =============================================================
     * @desc 管理员查看审核拒绝记录
     * @author PandaClark
     * @date 2019/5/16 1:53 PM
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员查看审核拒绝记录", notes = "管理员查看审核拒绝记录")
    @ResponseBody
    @RequestMapping( value = "/resourcelistR", method = RequestMethod.GET )
    public CommonResponse resourceListR(){
        List<FileInfo> files = fileService.getRejectServiceList();
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }


    /**
     * =============================================================
     * @desc 管理员审核通过资源
     * @author PandaClark
     * @date 2019/5/16 10:16 PM
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员审核通过资源", notes = "管理员查看审核拒绝记录")
    @ResponseBody
    @RequestMapping( value = "/serviceY", method = RequestMethod.GET )
    public CommonResponse checkService( @RequestParam("id") String id ){

        CommonResponse res = null;

        try {
            FileInfo fileInfo = fileService.getFileInfoById(id);
            // 机构用户上传资源时，信息不上链，基本信息只存在数据库里，只有审核通过的资源才上链
            Map<String, Object> map = new HashMap<>();
            map.put("$class","org.education.RegisterService");
            map.put("serviceID", fileInfo.getId());
            map.put("serviceName", fileInfo.getFileTitle());
            map.put("readPrice", fileInfo.getFileReadPrice());
            map.put("ownershipPrice", fileInfo.getFileOwnerShipPrice());
            map.put("company", fileInfo.getFileInitialProvider());

            fileFabricClient.registerService(map);
            adminService.acceptService(admin, id);
            res = new CommonResponse(0, "success", "注册新资源");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }

        return res;
    }

    /**
     * =============================================================
     * @desc 管理员审核拒绝资源
     * @author PandaClark
     * @date 2019/5/16 10:16 PM
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "管理员审核拒绝资源", notes = "管理员查看审核拒绝记录")
    @ResponseBody
    @RequestMapping( value = "/serviceR", method = RequestMethod.GET )
    public CommonResponse rejectService( @RequestParam("id") String id ){
        adminService.rejectService(admin, id);
        CommonResponse res = new CommonResponse(0, "success", "已审核拒绝");
        return res;
    }

    /*
     * =============================================================
     * @Description 管理员提取被侵权的资源水印信息(TODO)
     * @Author Messi-Q
     * @Date 19:32 2019-05-27
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     **/
    @ApiOperation(value = "管理员处理资源侵权", notes = "管理员提取被侵权的资源水印信息")
    @ResponseBody
    @RequestMapping( value = "/extracInfo/{id}", method = RequestMethod.GET )
    public CommonResponse extractServiceInfo() {
        CommonResponse res = new CommonResponse(0, "success", "提取成功");
        return res;
    }
}



















