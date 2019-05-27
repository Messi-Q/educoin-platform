package team.educoin.transaction.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.educoin.common.controller.CommonResponse;
import team.educoin.transaction.fabric.AdminFabricClient;
import team.educoin.transaction.fabric.AgencyFabricClient;
import team.educoin.transaction.fabric.UserFabricClient;
import team.educoin.transaction.pojo.AdminInfo;
import team.educoin.transaction.pojo.AgencyInfo;
import team.educoin.transaction.pojo.UserInfo;
import team.educoin.transaction.service.AdminService;
import team.educoin.transaction.service.AgencyService;
import team.educoin.transaction.service.UserService;
import team.educoin.transaction.util.UUIDutil;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 登录注册接口
 * @author: PandaClark
 * @create: 2019-05-23
 */
@RestController
@Api(value = "注册登录 API 接口", tags = "login", description = "注册登录 API 接口")
public class LoginController {


    @Autowired
    private UserService userService;
    @Autowired
    private AgencyService agencyService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserFabricClient userFabricClient;
    @Autowired
    private AgencyFabricClient agencyFabricClient;
    @Autowired
    private AdminFabricClient adminFabricClient;


    @ApiOperation(value = "普通用户注册")
    @RequestMapping( value = "/register/user", method = RequestMethod.POST )
    public CommonResponse userRegister(@RequestBody UserInfo userInfo){

        CommonResponse res = null;

        try {
            Map<String,Object> map = new HashMap<>();
            map.put("$class", "org.education.RegisterUser");
            map.put("email", userInfo.getEmail());
            map.put("password", userInfo.getPassword());
            userFabricClient.registerUser(map);
            userService.registerUser(userInfo);
            res = new CommonResponse(0, "success", "注册成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }

    @ApiOperation(value = "机构用户注册")
    @RequestMapping( value = "/register/agency", method = RequestMethod.POST )
    public CommonResponse agencyRegister(@RequestBody AgencyInfo agencyInfo){
        CommonResponse res = null;
        agencyInfo.setRegistrationNumber(UUIDutil.getUUID());
        try {
            Map<String,Object> map = new HashMap<>();
            map.put("$class", "org.education.RegisterCompany");
            map.put("email", agencyInfo.getEmail());
            map.put("password", agencyInfo.getPassword());
            map.put("registrationNumber", agencyInfo.getRegistrationNumber());
            agencyFabricClient.registerCompany(map);
            agencyService.registerCompany(agencyInfo);
            res = new CommonResponse(0, "success", "注册成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }

    @ApiOperation(value = "管理员用户注册")
    @RequestMapping( value = "/register/admin", method = RequestMethod.POST )
    public CommonResponse adminRegister(@RequestBody AdminInfo adminInfo){
        CommonResponse res = null;

        try {
            Map<String,Object> map = new HashMap<>();
            map.put("$class", "org.education.RegisterUser");
            map.put("email", adminInfo.getEmail());
            map.put("password", adminInfo.getPassword());
            adminFabricClient.registerRegulator(map);
            adminService.registerRegulator(adminInfo);
            res = new CommonResponse(0, "success", "注册成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }

    @ApiOperation(value = "普通用户登录")
    @RequestMapping( value = "/login/user", method = RequestMethod.POST )
    public CommonResponse userLogin(@RequestParam("email") String email, @RequestParam("password") String password){
        CommonResponse res = null;
        UserInfo user = userService.getUserById(email);
        if (user == null){
            res = new CommonResponse(1,"failed","该用户未注册");
            return res;
        }
        if ( !user.getPassword().equals(password) ){
            res = new CommonResponse(1,"failed","密码错误");
            return res;
        }
        res = new CommonResponse(0,"success","登录成功");
        return res;
    }


    @ApiOperation(value = "机构用户登录")
    @RequestMapping( value = "/login/agency", method = RequestMethod.POST )
    public CommonResponse agencyLogin(@RequestParam("email") String email, @RequestParam("password") String password){
        CommonResponse res = null;
        AgencyInfo agency = agencyService.getAgencyById(email);
        if (agency == null){
            res = new CommonResponse(1,"failed","该用户未注册");
            return res;
        }
        if ( !agency.getPassword().equals(password) ){
            res = new CommonResponse(1,"failed","密码错误");
            return res;
        }
        res = new CommonResponse(0,"success","登录成功");
        return res;
    }

    @ApiOperation(value = "管理员用户登录")
    @RequestMapping( value = "/login/admin", method = RequestMethod.POST )
    public CommonResponse adminLogin(@RequestParam("email") String email, @RequestParam("password") String password){
        CommonResponse res = null;
        AdminInfo admin = adminService.getAdminById(email);
        if (admin == null){
            res = new CommonResponse(1,"failed","该用户未注册");
            return res;
        }
        if ( !admin.getPassword().equals(password) ){
            res = new CommonResponse(1,"failed","密码错误");
            return res;
        }
        res = new CommonResponse(0,"success","登录成功");
        return res;
    }


    @ApiOperation(value = "查看所有普通用户")
    @RequestMapping( value = "/userlist/user", method = RequestMethod.GET )
    public CommonResponse userList(){
        CommonResponse res = new CommonResponse();
        res.setStatus(0);
        res.setMessage("success");
        res.setData(userService.getUserList());
        return res;
    }


    @ApiOperation(value = "查看所有机构用户")
    @RequestMapping( value = "/userlist/agency", method = RequestMethod.GET )
    public CommonResponse agencyList(){
        CommonResponse res = new CommonResponse();
        res.setStatus(0);
        res.setMessage("success");
        res.setData(agencyService.getAgencyList());
        return res;
    }


    @ApiOperation(value = "查看所有管理员用户")
    @RequestMapping( value = "/userlist/admin", method = RequestMethod.GET )
    public CommonResponse adminList(){
        CommonResponse res = new CommonResponse();
        res.setStatus(0);
        res.setMessage("success");
        res.setData(adminService.getAdminList());
        return res;
    }


    @ApiOperation(value = "删除普通用户")
    @RequestMapping( value = "/deleteuser/user/{email}", method = RequestMethod.DELETE )
    public CommonResponse deleteUSer(@PathVariable("email") String email){
        CommonResponse res = null;
        try {
            userFabricClient.deleteUser(email);
            userService.deleteUser(email);
            res = new CommonResponse(0, "success", "删除用户成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }


    @ApiOperation(value = "删除机构用户")
    @RequestMapping( value = "/deleteuser/agency/{email}", method = RequestMethod.DELETE )
    public CommonResponse deleteAgency(@PathVariable("email") String email){
        CommonResponse res = null;
        try {
            agencyFabricClient.deleteAgency(email);
            agencyService.deleteAgency(email);
            res = new CommonResponse(0, "success", "删除用户成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }


    @ApiOperation(value = "删除管理员用户")
    @RequestMapping( value = "/deleteuser/admin/{email}", method = RequestMethod.DELETE )
    public CommonResponse deleteAdmin(@PathVariable("email") String email){
        CommonResponse res = null;
        try {
            adminFabricClient.deleteAdmin(email);
            adminService.deleteAdmin(email);
            res = new CommonResponse(0, "success", "删除用户成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1, "failed", e.getMessage());
        }
        return res;
    }


    @ApiOperation(value = "修改普通用户信息")
    @RequestMapping( value = "/modifyuser/user/{email}", method = RequestMethod.POST )
    public CommonResponse modifyUser(@PathVariable("email") String email,
                                     @RequestParam("qq") String qq,
                                     @RequestParam("identityCard") String identityCard,
                                     @RequestParam("buyerType") String buyerType,
                                     @RequestParam("age") Integer age,
                                     @RequestParam("sexual") String sexual,
                                     @RequestParam("educationLevel") String educationLevel,
                                     @RequestParam("address") String address,
                                     @RequestParam("bankAccount") String bankAccount){

        UserInfo userInfo = new UserInfo(email, qq, identityCard, buyerType, age, sexual, educationLevel, address, bankAccount);
        CommonResponse res = null;
        try {
            userService.updateUserInfo(userInfo);
            res = new CommonResponse(0,"success","修改信息成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1,"failed",e.getMessage());
        }
        return res;
    }


    @ApiOperation(value = "修改机构用户信息")
    @RequestMapping( value = "/modifyuser/agency/{email}", method = RequestMethod.POST )
    public CommonResponse modifyAgency(@PathVariable("email") String email,
                                       @RequestParam("registrationNumber") String registrationNumber,
                                       @RequestParam("address") String address,
                                       @RequestParam("businessScope") String bussinessScope,
                                       @RequestParam("yycode") String yycode,
                                       @RequestParam("type") String type,
                                       @RequestParam("qq") String qq,
                                       @RequestParam("legalRepresentative") String legalRepresentative,
                                       @RequestParam("identityCard") String identityCard,
                                       @RequestParam("bankAccount") String bankAccount){

        AgencyInfo agencyInfo = new AgencyInfo(email,registrationNumber, address, bussinessScope, yycode, type,qq,legalRepresentative,identityCard, bankAccount);
        CommonResponse res = null;
        try {
            agencyService.updateAgencyInfo(agencyInfo);
            res = new CommonResponse(0,"success","修改信息成功");
        } catch (Exception e){
            e.printStackTrace();
            res = new CommonResponse(1,"failed",e.getMessage());
        }
        return res;
    }
}
