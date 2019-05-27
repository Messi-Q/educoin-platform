package team.educoin.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.educoin.transaction.dao.FileInfoMapper;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.service.FileService;

import java.util.List;

/**
 * @description:
 * @author: PandaClark
 * @create: 2019-05-16
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public FileInfo getFileInfoById(String id) {
        FileInfo file = fileInfoMapper.getRecordById(id);
        return file;
    }

    /**
     * 管理员查看所有资源列表，不管是否被审核
     * @return
     */
    @Override
    public List<FileInfo> getServiceList() {
        List<FileInfo> list = fileInfoMapper.selectAll();
        return list;
    }

    /**
     * 管理员查看待审核列表
     * @return
     */
    @Override
    public List<FileInfo> getUnCheckedServiceList() {
        List<FileInfo> list = fileInfoMapper.getRecordsByFlag(0);
        return list;
    }

    /**
     * 管理员查看通过审核的资源列表
     * 普通用户只能查看通过审核的资源列表
     * @return
     */
    @Override
    public List<FileInfo> getCheckedServiceList() {
        List<FileInfo> list = fileInfoMapper.getRecordsByFlag(1);
        return list;
    }

    /**
     * 审核拒绝的资源列表
     * @return
     */
    @Override
    public List<FileInfo> getRejectServiceList() {
        List<FileInfo> list = fileInfoMapper.getRecordsByFlag(2);
        return list;
    }

    /**
     * 机构用户查看待审核的资源列表
     * @param id
     * @return
     */
    public List<FileInfo> getUnCheckedServiceListById(String id){
        List<FileInfo> list = fileInfoMapper.getRecordsByIdAndFlag(id, 0);
        return list;
    }

    /**
     * 机构用户查看已审核通过的资源列表
     * @param id
     * @return
     */
    public List<FileInfo> getCheckedServiceListById(String id){
        List<FileInfo> list = fileInfoMapper.getRecordsByIdAndFlag(id, 1);
        return list;
    }

    /**
     * 机构用户查看已审核拒绝的资源列表
     * @param id
     * @return
     */
    public List<FileInfo> getRejectServiceListById(String id){
        List<FileInfo> list = fileInfoMapper.getRecordsByIdAndFlag(id, 2);
        return list;
    }

    @Override
    public boolean deleteService(String id) {
        int res = fileInfoMapper.deleteRecordById(id);
        return res > 0;
    }

    @Override
    public boolean updateFileReadPrice(String id, Double fileReadPrice) {
        int i = fileInfoMapper.updateFileReadPrice(id, fileReadPrice);
        return i > 0;
    }

    @Override
    public boolean updateFileOwnershipPrice(String id, Double fileOwnerShipPrice) {
        int i = fileInfoMapper.updateFileOwnerShipPrice(id, fileOwnerShipPrice);
        return i > 0;
    }

    @Override
    public boolean updateFileInfo(FileInfo fileInfo) {
        int i = fileInfoMapper.updateFileInfo(fileInfo);
        return i > 0;
    }

    @Override
    public boolean registerService(FileInfo fileInfo) {
        int i = fileInfoMapper.addRecord(fileInfo);
        return i > 0;
    }

}
