package team.educoin.transaction.fabric;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import team.educoin.transaction.dto.TokenTransferDto;
import team.educoin.transaction.dto.UserRechargeDto;
import team.educoin.transaction.pojo.FabricUserInfo;

import java.util.List;
import java.util.Map;

/**
 * description:
 *
 * @author: chenzhou04
 * @create: 2019-04-16
 */
@Component
@FeignClient(value = "userFabricClient",url="${educoin.fabric.url}")
@RequestMapping("/api")
public interface UserFabricClient {
    /**
     * 测试：获取所有用户
     * @return
     */
    @RequestMapping(value = "/user",method = RequestMethod.GET)
    List<FabricUserInfo> getUser();

    /**
     * ===================================================================
     * @desc 用户充值请求 fabric
     * @author PandaClark
     * @date 2019/5/7 6:56 PM
     * @param userRechargeDto
     * @return team.educoin.transaction.dto.UserRechargeDto
     * ===================================================================
     */
    @RequestMapping( value = "/UserRecharge", method = RequestMethod.POST )
    UserRechargeDto userRechargeFabric(@RequestBody UserRechargeDto userRechargeDto) throws Exception;

    /**
     * ===================================================================
     * @desc 普通用户向普通用户转账请求 fabric
     * @author PandaClark
     * @date 2019/5/7 6:56 PM
     * @param tokenTransferDto
     * @return team.educoin.transaction.dto.UserRechargeDto
     * ===================================================================
     */
    @RequestMapping( value = "/TokenTransferU_U", method = RequestMethod.POST )
    TokenTransferDto tokenTransferU_UFabric(@RequestBody TokenTransferDto tokenTransferDto) throws Exception;

    /**
     * ===================================================================
     * @desc 普通用户向机构用户转账请求 fabric
     * @author PandaClark
     * @date 2019/5/7 6:56 PM
     * @param tokenTransferDto
     * @return team.educoin.transaction.dto.UserRechargeDto
     * ===================================================================
     */
    @RequestMapping( value = "/TokenTransferU_C", method = RequestMethod.POST )
    TokenTransferDto tokenTransferU_CFabric(@RequestBody TokenTransferDto tokenTransferDto) throws Exception;


    /**
     * =============================================================
     * @desc 普通用户购买阅读权
     * @author PandaClark
     * @date 2019/5/17 11:38 AM
     * @return java.util.Map<java.lang.String       ,       java.lang.Object>
     * =============================================================
     */
    @RequestMapping( value = "/UserConsumeService", method = RequestMethod.POST )
    Map<String, Object> userConsumeService(Map<String,String> values) throws Exception;

    @RequestMapping( value = "/RegisterUser", method = RequestMethod.POST )
    Map<String, Object> registerUser(Map<String, Object> map) throws Exception;

    @RequestMapping( value = "/User/{id}", method = RequestMethod.DELETE )
    Map<String, Object> deleteUser(@PathVariable("id") String id);
}
