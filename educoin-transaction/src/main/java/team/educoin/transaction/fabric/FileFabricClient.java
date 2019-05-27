package team.educoin.transaction.fabric;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: PandaClark
 * @create: 2019-05-16
 */
@Component
@FeignClient(value = "fileFabricClient",url="${educoin.fabric.url}")
@RequestMapping("/api")
public interface FileFabricClient {

    // 注册资源
    @RequestMapping(value = "/RegisterService", method = RequestMethod.POST)
    String registerService(@RequestBody Map<String, Object> info);

    // 删除资源
    @RequestMapping(value = "/Service/{id}", method = RequestMethod.DELETE)
    Map<String,Object> deleteService(@PathVariable("id") String id);

    // 修改资源信息
    @RequestMapping(value = "/Service/{id}", method = RequestMethod.PUT)
    String updateService(@PathVariable("id") String id, @RequestBody Map<String,Object> info);

    // 更新资源所有权价格
    @RequestMapping(value = "/UpdateServiceownershipPrice", method = RequestMethod.POST)
    String updateServiceOwnershipPrice(@RequestBody Map<String, Object> info);

    // 更新资源阅读权价格
    @RequestMapping(value = "/UpdateServicereadPrice", method = RequestMethod.POST)
    String updateServiceReadPrice(@RequestBody Map<String,Object> info);

}
