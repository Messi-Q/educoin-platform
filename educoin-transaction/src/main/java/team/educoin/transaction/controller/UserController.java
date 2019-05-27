package team.educoin.transaction.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import team.educoin.common.controller.CommonResponse;
import team.educoin.transaction.dto.TokenTransferDto;
import team.educoin.transaction.fabric.FileFabricClient;
import team.educoin.transaction.fabric.UserFabricClient;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.pojo.Recharge;
import team.educoin.transaction.pojo.Token;
import team.educoin.transaction.pojo.UserInfo;
import team.educoin.transaction.service.FileService;
import team.educoin.transaction.service.UserService;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
@Api(value = "User API 接口", tags = "user", description = "user API 接口")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserFabricClient userFabricClient;



    private String email = "test1@qq.com";

    /**
     * 测试服务器IP 和 数据库 是否能通
     */
    @RequestMapping( value = "/alluser", method = RequestMethod.GET)
    public CommonResponse testFabricRequest() {
        CommonResponse res = new CommonResponse();
        res.setStatus(0);
        res.setMessage("success");
        res.setData(userService.getUserInfo());
        return res;
    }


    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 12:20 PM
     * @param []
     * @return CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "获取所有审核通过的充值记录")
    @RequestMapping( value = "/getRechargesY", method = RequestMethod.GET )
    public CommonResponse getRechargesY(){
        // email 应当从 session 中拿，此处只是测试
        String email = "test1@qq.com";
        List<Recharge> recharges = userService.getUserRechargeRecords(email, 0);
        CommonResponse res = new CommonResponse(0, "success", recharges);
        return res;
    }

    /**
     * @author PandaClark
     * @desc 普通用户充值
     * @param balance 充值金额
     */
    @ApiOperation(value = "用户充值", notes = "用户充值接口")
    @RequestMapping( value = "/recharge", method = RequestMethod.POST )
    public CommonResponse recharge(@RequestParam("balance") double balance) {
        CommonResponse res = new CommonResponse();

        // email 应当从 session 中拿，此处只是测试
        String email = "test1@qq.com";
        boolean success = userService.userRecharge(email, balance);
        if (success){
            res.setStatus(0);
            res.setMessage("success");
            res.setData("充值成功");
        } else {
            res.setStatus(1);
            res.setMessage("failed");
            res.setData("充值失败");
        }
        return res;
    }


    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 1:20 PM
     * @param []
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "获取所有转账记录")
    @RequestMapping( value = "/transferList", method = RequestMethod.GET )
    public CommonResponse getTransferRecords(){
        CommonResponse res = new CommonResponse();
        // email 应当从 session 中拿，此处只是测试
        String email = "test1@qq.com";
        List<Token> transfers = userService.getUserTransferRecords(email);
        res.setStatus(0);
        res.setMessage("success");
        res.setData(transfers);
        return res;
    }

    /**
     * @author PandaClark
     * @desc 普通用户向普通用户转账
     * @param transferDto 将转账信息存储到 transferDto 对象中
     * @return
     */
    @ApiOperation(value = "普通用户向普通用户转账", notes = "普通用户向普通用户转账接口")
    @RequestMapping( value = "/transferu2u", method = RequestMethod.POST )
    public CommonResponse transferU2U(@RequestBody TokenTransferDto transferDto){
        CommonResponse res = new CommonResponse();

        // email 应当从 session 中拿，此处只是测试
        String email = "test1@qq.com";

        transferDto.setFromuser(email);

        boolean success = userService.tokenTransferU2U(transferDto);
        if (success){
            res.setStatus(0);
            res.setMessage("success");
            res.setData("充值成功");
        } else {
            res.setStatus(1);
            res.setMessage("failed");
            res.setData("充值失败");
        }
        return res;
    }


    /**
     * @author PandaClark
     * @desc 普通用户向机构用户转账
     * @param transferDto 将转账信息存储到 transferDto 对象中
     * @return
     */
    @ApiOperation(value = "普通用户向机构用户转账", notes = "普通用户向机构用户转账接口")
    @RequestMapping( value = "/transferu2c", method = RequestMethod.POST )
    public CommonResponse transferU2C(@RequestBody TokenTransferDto transferDto){
        CommonResponse res = new CommonResponse();

        // email 应当从 session 中拿，此处只是测试
        String email = "test1@qq.com";

        transferDto.setFromuser(email);

        boolean success = userService.tokenTransferU2C(transferDto);
        if (success){
            res.setStatus(0);
            res.setMessage("success");
            res.setData("充值成功");
        } else {
            res.setStatus(1);
            res.setMessage("failed");
            res.setData("充值失败");
        }
        return res;
    }

    /**
     * =============================================================
     * @desc 查询可购买资源列表
     * @author PandaClark
     * @date 2019/5/16 12:39 PM
     * @param
     * @return
     * =============================================================
     */
    @ApiOperation(value = "查询可购买资源列表", notes = "查询可购买资源列表")
    @RequestMapping( value = "/myresourcelist", method = RequestMethod.GET )
    public CommonResponse resourceList(){
        List<FileInfo> files = fileService.getCheckedServiceList();
        CommonResponse res = new CommonResponse(0, "success", files);
        return res;
    }


    /**
     * =============================================================
     * @desc 普通用户购买阅读权
     * @author PandaClark
     * @date 2019/5/15 7:11 PM
     * @param
     * @return team.educoin.common.controller.CommonResponse
     * =============================================================
     */
    @ApiOperation(value = "普通用户购买阅读权")
    @RequestMapping( value = "/consume", method = RequestMethod.POST )
    public CommonResponse consume( @RequestParam("serviceID") String serviceID ){
        CommonResponse res = new CommonResponse();
        UserInfo user = userService.getUserById(email);
        FileInfo fileInfo = fileService.getFileInfoById(serviceID);
        if (fileInfo.getFileReadPrice() > user.getAccountBalance() ){
            res.setStatus(1);
            res.setMessage("failed");
            res.setData("余额不足");
        } else {
            Map<String, String> map = new HashMap<>();
            map.put("$class","org.education.UserConsumeService");
            map.put("serviceID",serviceID);
            map.put("user",email);
            try {
                Map<String, Object> map1 = userFabricClient.userConsumeService(map);
                userService.userConsumeService(email,serviceID,fileInfo);
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


    @RequestMapping( value = "/test", method = RequestMethod.GET)
    public CommonResponse test(){
        CommonResponse response = new CommonResponse();
        Map<String, String> map = new HashMap<>();
        map.put("$class","org.education.UserConsumeService");
        map.put("serviceID","ceshi12345678");
        map.put("user","test2@qq.com");

        try {
            Map<String, Object> res = userFabricClient.userConsumeService(map);
            System.out.println("response map: "+ res);
        } catch (Exception e) {
            System.out.println("exception occurs");
            e.printStackTrace();
        }

        return response;
    }
}
