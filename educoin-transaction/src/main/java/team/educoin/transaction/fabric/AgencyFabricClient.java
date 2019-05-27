package team.educoin.transaction.fabric;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import team.educoin.transaction.dto.AgencyWithdrawDto;

import java.util.Map;


/**
 * @description: 机构 fabric 访问
 * @author: PandaClark
 * @create: 2019-05-13
 */
@Component
@FeignClient(value = "agencyFabricClient",url="${educoin.fabric.url}")
@RequestMapping("/api")
public interface AgencyFabricClient {


    /**
     * =============================================================
     * @desc 机构用户提现
     * @author PandaClark
     * @date 2019/5/13 12:32 PM
     * @param
     * @return team.educoin.transaction.dto.UserRechargeDto
     * =============================================================
     */
    @RequestMapping( value = "/CompanyWithdraw", method = RequestMethod.POST )
    AgencyWithdrawDto companyWithdrawFabric(@RequestBody AgencyWithdrawDto agencyWithdrawDto) throws Exception;

    /**
     * =============================================================
     * @desc 审核机构用户提现：通过
     * @author PandaClark
     * @date 2019/5/13 3:48 PM
     * @param rechargeInfo 待审核提现记录信息
     * @return java.util.Map<java.lang.String , java.lang.String>
     * =============================================================
     */
    @RequestMapping( value = "/CheckCompanyWithdraw", method = RequestMethod.POST )
    Map<String,String> CheckAgencyWithdrawFabric(@RequestBody Map<String,String> rechargeInfo);

    /**
     * =============================================================
     * @desc 审核机构用户提现：拒绝
     * @author PandaClark
     * @date 2019/5/13 4:06 PM
     * @param rechargeInfo 待审核提现记录信息
     * @return java.util.Map<String, String>
     * =============================================================
     */
    @RequestMapping( value = "/RejectCompanyWithdraw", method = RequestMethod.POST )
    Map<String,Object> RejectAgencyWithdrawFabric(@RequestBody Map<String,String> rechargeInfo);

    /**
     * =============================================================
     * @desc 机构用户购买所有权
     * @author PandaClark
     * @date 2019/5/17 11:37 AM
     * @return java.util.Map<java.lang.String , java.lang.Object>
     * =============================================================
     */
    @RequestMapping( value = "/CompanyBuyOnwership", method = RequestMethod.POST )
    Map<String, Object> agencyBuyOwnership(Map<String, String> values) throws Exception;

    @RequestMapping( value = "/RegisterCompany", method = RequestMethod.POST )
    Map<String, Object> registerCompany(Map<String, Object> map);

    @RequestMapping( value = "/Company/{id}", method = RequestMethod.DELETE )
    Map<String, Object> deleteAgency(@PathVariable("id") String id);
}