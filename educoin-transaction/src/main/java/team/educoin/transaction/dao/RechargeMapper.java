package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.Recharge;

import java.util.List;
/**
 * @description: 操作 recharge_apply 表的接口
 * @author: PandaClark
 * @create: 2019-04-28
 */
@Component
public interface RechargeMapper {

    String TABLE_NAME = "user_recharge";
    String INSERT_FIELDS = "email, admin_email, payment_id, payment_method, if_checked, recharge_amount, " +
            "check_time, update_time";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{email},#{adminEmail},#{paymentId},#{paymentMethod},#{ifChecked},#{rechargeAmount},#{checkTime},#{updateTime})"})
    int addRecharge(Recharge recharge);


    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where if_checked=#{flag}"})
    List<Recharge> getRecordsByFlag(@Param("flag") int flag);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where email=#{email} and if_checked=#{flag}"})
    List<Recharge> getRecordsByIdAndFlag(@Param("email") String email, @Param("flag") int flag);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where payment_id=#{paymentId}"})
    Recharge getRecordByPaymentId(@Param("paymentId") String paymentId);

    @Update({"update ", TABLE_NAME, "set admin_email=#{adminEmail},if_checked=#{checkCode} where payment_id=#{paymentId}"})
    int updateRecordByPaymentId(@Param("paymentId") String paymentId, @Param("adminEmail") String adminEmail, @Param("checkCode") int checkCode);
}


