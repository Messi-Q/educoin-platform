package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.UserInfo;

import java.util.List;

/**
 * description:
 * @author: Messi-Q
 * @create: 2019-05-27
 */
@Component
public interface UserInfoMapper {

    List<UserInfo> selectAllUser();


    @Select("select * from user_info")
    List<UserInfo> selectAllRecords();

    // @Select({"select email, account_balance from user_info where email=#{email}"})
    @Select({"select * from user_info where email=#{email}"})
    UserInfo selectRecordById(@Param("email") String email);

    @Update({"update user_info set bankAccount=#{amount} where email=#{id}"})
    int updateBankAccountById(@Param("id") String id, @Param("amount") String amount);

    @Insert("insert into user_info(email,password) values(#{email},#{password})")
    int addRecord(UserInfo userInfo);

    @Delete("delete from user_info where email = #{email}")
    int deleteById(@Param("email") String email);

    @Update("update user_info set qq=#{qq},identityCard=#{identityCard},buyerType=#{buyerType},age=#{age},sexual=#{sexual},educationLevel=#{educationLevel},address=#{address},bankAccount=#{bankAccount} where email=#{email}")
    int updateRecord(UserInfo userInfo);
}
