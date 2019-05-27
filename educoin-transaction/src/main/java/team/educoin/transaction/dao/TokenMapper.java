package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import team.educoin.transaction.pojo.Token;

import java.util.List;

/**
 * @description: 操作 token_transfer 表的接口
 * @author: Messi-Q
 * @create: 2019-05-27
 */
@Component
public interface TokenMapper {

    String TABLE_NAME = "user_transfer";
    String INSERT_FIELDS = "transfer_id, from_email, to_email, beneficiary_type, transfer_amount, update_time";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS;

    @Insert({"insert into ", TABLE_NAME, "(", INSERT_FIELDS,
            ") values (#{transferId},#{fromEmail},#{toEmail},#{beneficiaryType},#{transferAmount},#{updateTime})"})
    int addTransfer(Token token);

    @Select({"select ", SELECT_FIELDS, "from ", TABLE_NAME, "where from_email=#{email}"})
    List<Token> getRecordsById(@Param("email") String email);
}
