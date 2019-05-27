package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.FileInfo;

import java.util.List;

/**
 * @description: mysql 表 file_info
 * @author: PandaClark
 * @create: 2019-05-16
 */
@Component
public interface FileInfoMapper {

    String TABLE_NAME = "file_info";
    String INSERT_FIELDS = "id,fileOwner,fileInitialProvider,fileTitle,fileImage,fileDescription,fileReadPrice,fileOwnerShipPrice,fileName,fileKeyWord,fileContentType,fileFormat,fileSize,fileChecked";
    String SELECT_FIELDS = INSERT_FIELDS;

    // 选出所有资源信息
    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME})
    List<FileInfo> selectAll();

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where id=#{id}"})
    FileInfo getRecordById(@Param("id") String id);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where fileChecked=#{flag}"})
    List<FileInfo> getRecordsByFlag(@Param("flag") int flag);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where fileOwner=#{email} and if_checked=#{flag}"})
    List<FileInfo> getRecordsByIdAndFlag(@Param("email") String email, @Param("flag") int flag);

    @Update({"update ", TABLE_NAME," set fileOwner=#{fileOwner} where id=#{id}"})
    int updateFileOwner(@Param("id")String id, @Param("fileOwner")String fileOwner);

    @Update({"update ", TABLE_NAME, "set adminEmail=#{admin},fileChecked=#{fileChecked} where id=#{id}"})
    int updateFileChecked(@Param("admin") String admin, @Param("id") String id, @Param("fileChecked") int fileChecked);

    @Update({"update ",TABLE_NAME," set fileReadPrice=#{fileReadPrice} where id=#{id}"})
    int updateFileReadPrice(@Param("id") String id, @Param("fileReadPrice") Double fileReadPrice);

    @Delete({"delete from ", TABLE_NAME," where id=#{id}"})
    int deleteRecordById(@Param("id") String id);

    //修改资源所有权价格
    @Update({"update ",TABLE_NAME," set fileOwnerShipPrice=#{fileOwnerShipPrice} where id=#{id}"})
    int updateFileOwnerShipPrice(@Param("id") String id, @Param("fileOwnerShipPrice") Double fileOwnerShipPrice);

    //修改资源信息
    @Update({"update ",TABLE_NAME," set fileTitle=#{fileTitle},fileImage=#{fileImage},fileDescription=#{fileDescription}," +
            "fileReadPrice=#{fileReadPrice},fileOwnerShipPrice=#{fileOwnerShipPrice},fileKeyWord=#{fileKeyWord}," +
            "fileContentType=#{fileContentType} where id=#{id}"})
    int updateFileInfo(FileInfo fileInfo);

    @Insert({"insert into ", TABLE_NAME, "(",INSERT_FIELDS,") values (#{id},#{fileOwner},#{fileInitialProvider},#{fileTitle},#{fileImage},#{fileDescription},#{fileReadPrice},#{fileOwnerShipPrice},#{fileName},#{fileKeyWord},#{fileContentType},#{fileFormat},#{fileSize},#{fileChecked})"})
    int addRecord(FileInfo fileInfo);

}
