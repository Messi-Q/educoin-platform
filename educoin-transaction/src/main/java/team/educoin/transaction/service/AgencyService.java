package team.educoin.transaction.service;

import org.springframework.stereotype.Service;
import team.educoin.transaction.pojo.AgencyInfo;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.pojo.UserInfo;
import team.educoin.transaction.pojo.Withdraw;

import java.util.List;
import java.util.Map;

/**
 * @description: 机构Service
 * @author: PandaClark
 * @create: 2019-05-12
 */
public interface AgencyService {


    /**
     * =============================================================
     * @desc 机构用户提现
     * @author PandaClark
     * @date 2019/5/13 12:01 PM
     * @param email 提现ID
     * @param amount 提现金额
     * @return boolean
     * =============================================================
     */
    boolean companyWithdraw(String email, double amount);

    /**
     * =============================================================
     * @desc 根据 payment_id 查出一条提现记录
     * @author PandaClark
     * @date 2019/5/13 3:56 PM
     * @param id payment_id
     * @return team.educoin.transaction.pojo.Withdraw
     * =============================================================
     */
    Withdraw getWithdrawRecordById(String id);

    /**
     * =============================================================
     * @desc 获取指定审核状态的转账记录
     * @author PandaClark
     * @date 2019/5/13 6:00 PM
     * @param email 机构 ID
     * @param flag 转账记录审核状态 0 待审核/1 通过/2 拒绝
     * @return java.util.List<team.educoin.transaction.pojo.Withdraw>
     * =============================================================
     */
    List<Withdraw> getAgencyWithdrawRecords(String email, int flag);

    AgencyInfo getAgencyById(String email);

    void agencyBuyOwnership(String email, String serviceID, FileInfo fileInfo);

    List<AgencyInfo> getAgencyList();

    boolean registerCompany(AgencyInfo agencyInfo);

    boolean deleteAgency(String email);

    boolean updateAgencyInfo(AgencyInfo agencyInfo);
}
