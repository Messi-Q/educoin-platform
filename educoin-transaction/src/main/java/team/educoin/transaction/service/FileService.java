package team.educoin.transaction.service;

import team.educoin.transaction.pojo.FileInfo;

import java.util.List;

/**
 * @description: 资源服务
 * @author: Messi-Q
 * @create: 2019-05-27
 */
public interface FileService {

    FileInfo getFileInfoById(String id);

    /**
     * =============================================================
     * @desc 查询可购买资源列表
     * @author PandaClark
     * @date 2019/5/16 1:15 PM
     * @return java.util.List<team.educoin.transaction.pojo.FileInfo>
     * =============================================================
     */
    List<FileInfo> getServiceList();

    /**
     * =============================================================
     * @desc 获取未审核资源列表
     * @author PandaClark
     * @date 2019/5/16 1:33 PM
     * @return java.util.List<team.educoin.transaction.pojo.FileInfo>
     * =============================================================
     */
    List<FileInfo> getUnCheckedServiceList();
    /**
     * =============================================================
     * @desc 获取已审核通过资源列表
     * @author PandaClark
     * @date 2019/5/16 1:33 PM
     * @return java.util.List<team.educoin.transaction.pojo.FileInfo>
     * =============================================================
     */
    List<FileInfo> getCheckedServiceList();

    List<FileInfo> getRejectServiceList();


    List<FileInfo> getUnCheckedServiceListById(String id);

    List<FileInfo> getCheckedServiceListById(String id);

    List<FileInfo> getRejectServiceListById(String id);

    boolean deleteService(String id);

    boolean updateFileReadPrice(String id, Double fileReadPrice);

    boolean updateFileOwnershipPrice(String id, Double fileOwnerShipPrice);

    boolean updateFileInfo(FileInfo fileInfo);

    boolean registerService(FileInfo fileInfo);
}
