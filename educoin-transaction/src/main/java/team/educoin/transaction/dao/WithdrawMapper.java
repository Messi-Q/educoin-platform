package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.Withdraw;

import java.util.List;

/**
 * @description: 操作 company_withdraw 表的接口
 * @author: PandaClark
 * @create: 2019-05-13
 */
@Component
public interface WithdrawMapper {

    String TABLE_NAME = "agency_withdraw";
    String INSERT_FIELDS = "email, admin_email, payment_id, payment_method, if_checked, withdraw_amount, " +
            "check_time, update_time";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{email},#{adminEmail},#{paymentId},#{paymentMethod},#{ifChecked},#{withdrawAmount},#{checkTime},#{updateTime})"})
    int addRecord(Withdraw withdraw);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where if_checked=#{flag}"})
    List<Withdraw> getRecordsByFlag(@Param("flag") int flag);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where email=#{email} and if_checked=#{flag}"})
    List<Withdraw> getRecordsByIdAndFlag(@Param("email") String email, @Param("flag") int flag);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where payment_id=#{paymentId}"})
    Withdraw getRecordByPaymentId(String id);

    @Update({"update ", TABLE_NAME, "set admin_email=#{adminEmail},if_checked=#{checkCode} where payment_id=#{paymentId}"})
    int updateRecordByPaymentId(@Param("paymentId") String paymentId, @Param("adminEmail") String adminEmail, @Param("checkCode") int checkCode);
}
