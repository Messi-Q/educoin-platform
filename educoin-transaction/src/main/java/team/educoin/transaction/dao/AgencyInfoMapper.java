package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.AgencyInfo;

import java.util.List;

/**
 * @description: agency_info
 * @author: PandaClark
 * @create: 2019-05-16
 */
@Component
public interface AgencyInfoMapper {

    // @Select({"select email, account_balance from agency_info where email=#{email}"})
    @Select({"select * from agency_info where email=#{email}"})
    AgencyInfo selectRecordById(@Param("email") String email);

    @Update({"update agency_info set accountBalance=#{amount} where email=#{id}"})
    int updateBankAccountById(@Param("id") String id, @Param("amount") String amount);

    @Select("select * from agency_info")
    List<AgencyInfo> selectAllRecords();

    @Insert("insert into agency_info(email,password,registrationNumber) values(#{email},#{password},#{registrationNumber})")
    int addRecord(AgencyInfo agencyInfo);

    @Delete("delete from agency_info where email = #{email}")
    int deleteById(@Param("email") String email);

    @Update("update agency_info set registrationNumber=#{registrationNumber},address=#{address},businessScope=#{businessScope},yycode=#{yycode},type=#{type},qq=#{qq},legalRepresentative=#{legalRepresentative},identityCard=#{identityCard},bankAccount=#{bankAccount} where email=#{email}")
    int updateRecord(AgencyInfo agencyInfo);
}
