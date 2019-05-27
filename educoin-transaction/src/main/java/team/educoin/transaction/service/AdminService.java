package team.educoin.transaction.service;

import team.educoin.transaction.dto.CentralBankDto;
import team.educoin.transaction.dto.ContractDto;
import team.educoin.transaction.pojo.*;

import java.util.List;

/**
 * @description: 管理员Service
 * @author: PandaClark
 * @create: 2019-05-12
 */
public interface AdminService {


    /**
     * =============================================================
     * @desc 查看中央账户
     * @author PandaClark
     * @date 2019/5/12 5:44 PM
     * @return CentralBankDto 中央账户信息的数据传输对象
     * =============================================================
     */
    CentralBankDto getCentralBankInfo();

    /**
     * =============================================================
     * @desc 查看权益分配合约
     * @author PandaClark
     * @date 2019/5/12 5:55 PM
     * @return ContractDto
     * =============================================================
     */
    ContractDto getContractInfo();

    /**
     * =============================================================
     * @desc 获取所有待审核用户充值列表
     * @author PandaClark
     * @date 2019/5/13 5:46 PM
     * @return java.util.List<team.educoin.transaction.pojo.Recharge>
     * =============================================================
     */
    List<Recharge> getUnCheckedRechargeList();


    /**
     * =============================================================
     * @desc 获取所有待审核用户充值列表
     * @author PandaClark
     * @date 2019/5/13 5:48 PM
     * @return java.util.List<Withdraw>
     * =============================================================
     */
    List<Withdraw> getUnCheckedWithdrawList();

    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 9:52 PM
     * @return void
     * =============================================================
     */
    void acceptUserRecharge(String paymentId, String adminEmail);

    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 9:52 PM
     * @return void
     * =============================================================
     */
    void rejectUserRecharge(String paymentId, String adminEmail);

    /**
     * =============================================================
     * @desc 同意机构用户提现：只操作数据库
     * @author PandaClark
     * @date 2019/5/13 4:21 PM
     * @param paymentId, admin
     * @return void
     * =============================================================
     */
    void acceptCompanyWithdraw(String paymentId, String admin);

    /**
     * =============================================================
     * @desc 拒绝机构用户提现：只操作数据库
     * @author PandaClark
     * @date 2019/5/13 4:21 PM
     * @param paymentId, admin
     * @return void
     * =============================================================
     */
    void rejectCompanyWithdraw(String paymentId, String admin);

    AdminInfo getAdminById(String admin);

    /**
     * =============================================================
     * @desc 服务审核通过
     * @author PandaClark
     * @date 2019/5/17 11:07 AM
     * @return void
     * =============================================================
     */
    void acceptService(String admin, String id);

    /**
     * =============================================================
     * @desc 服务审核拒绝
     * @author PandaClark
     * @date 2019/5/17 11:07 AM
     * @return void
     * =============================================================
     */
    void rejectService(String admin, String id);

    List<AdminInfo> getAdminList();

    boolean registerRegulator(AdminInfo adminInfo);

    boolean deleteAdmin(String email);
}
