package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.AdminInfo;

import java.util.List;

/**
 * @description: admin_info
 * @author: PandaClark
 * @create: 2019-05-16
 */
@Component
public interface AdminInfoMapper {

    // @Select({"select email, account_balance from admin_info where email=#{email}"})
    @Select({"select * from admin_info where email=#{email}"})
    AdminInfo selectRecordById(String email);

    @Select("select * from admin_info")
    List<AdminInfo> selectAllRecords();

    @Insert("insert into admin_info(email,password) values(#{email},#{password})")
    int addRecord(AdminInfo adminInfo);

    @Delete("delete from admin_info where email = #{email}")
    int deleteById(String email);
}
