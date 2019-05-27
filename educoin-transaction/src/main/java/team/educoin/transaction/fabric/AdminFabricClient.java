package team.educoin.transaction.fabric;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import team.educoin.transaction.dto.CentralBankDto;
import team.educoin.transaction.dto.ContractDto;

import java.util.List;
import java.util.Map;

/**
 * @description: 管理员fabric接口
 * @author: PandaClark
 * @create: 2019-05-12
 */
@Component
@FeignClient(value = "adminFabricClient",url="${educoin.fabric.url}")
@RequestMapping("/api")
public interface AdminFabricClient {

    @RequestMapping( value = "/CentralBank", method = RequestMethod.GET )
    List<CentralBankDto> getCentralBankInfo();

    @RequestMapping( value = "/Contract", method = RequestMethod.GET )
    List<ContractDto> getContractInfo();

    /**
     * =============================================================
     * @desc 审核普通用户充值：同意
     * @author PandaClark
     * @date 2019/5/13 3:49 PM
     * @param rechargeInfo 待审核充值记录信息
     * @return java.util.Map<String, String>
     * =============================================================
     */
    @RequestMapping( value = "/CheckUserRecharge", method = RequestMethod.POST )
    Map<String,String> CheckUserRechargeFabric(@RequestBody Map<String,String> rechargeInfo);

    /**
     * =============================================================
     * @desc 审核普通用户充值：拒绝
     * @author PandaClark
     * @date 2019/5/13 4:04 PM
     * @param rechargeInfo 待审核充值记录信息
     * @return java.util.Map<String, String>
     * =============================================================
     */
    @RequestMapping( value = "/RejectUserRecharge", method = RequestMethod.POST )
    Map<String,String> RejectUserRechargeFabric(@RequestBody Map<String,String> rechargeInfo);


    @RequestMapping( value = "/RegisterRegulator", method = RequestMethod.POST )
    Map<String, Object> registerRegulator(Map<String, Object> map);


    @RequestMapping( value = "/Regulator/{id}", method = RequestMethod.DELETE )
    Map<String, Object> deleteAdmin(@PathVariable("id") String id);

}
