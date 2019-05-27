package team.educoin.transaction.dao;

import org.apache.ibatis.annotations.Insert;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @description: agency_consume
 * @author: PandaClark
 * @create: 2019-05-16
 */
@Component
public interface AgencyConsumeMapper {

    String TABLE_NAME = "agency_consume";
    String INSERT_FIELDS = "email, service_id, file_title, file_ownerPrice, file_name";
    String SELECT_FIELDS = "id, " + INSERT_FIELDS + ", create_time, update_time";

    @Insert({"insert into ", TABLE_NAME, "(",INSERT_FIELDS,") values (#{email},#{service_id},#{file_title},#{file_ownerPrice},#{file_name})"})
    int addRecord(Map<String,Object> map);
}
