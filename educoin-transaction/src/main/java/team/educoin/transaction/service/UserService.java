package team.educoin.transaction.service;

import team.educoin.transaction.dto.TokenTransferDto;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.pojo.Recharge;
import team.educoin.transaction.pojo.Token;
import team.educoin.transaction.pojo.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * description:
 * @author: Messi-Q
 * @create: 2019-05-27
 */
public interface UserService {
    /**
     * 测试服务器是否能通：从 fabric 和 mysql 中读出数据
     * @return
     */
    Map<String, Object> getUserInfo();

    /**
     * =============================================================
     * @desc 根据 id 返回一条充值记录
     * @author PandaClark
     * @date 2019/5/12 8:09 PM
     * @param id 充值记录id
     * @return team.educoin.transaction.pojo.Token
     * =============================================================
     */
    Recharge getRechargeRecordById(String id);

    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 12:10 PM
     * @param email 用户ID
     * @param flag 充值记录审核状态： 0 待审核/1 通过/2 拒绝
     * @return java.util.List<team.educoin.transaction.pojo.Recharge>
     * =============================================================
     */
    List<Recharge> getUserRechargeRecords(String email, int flag);

    /**
     * ===================================================================
     * @desc 普通用户充值
     * @author PandaClark
     * @param email 充值账户ID
     * @param balance 充值金额
     * ===================================================================
     */
    boolean userRecharge(String email, double balance);

    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 1:10 PM
     * @param email 用户ID
     * @return java.util.List<team.educoin.transaction.pojo.Token>
     * =============================================================
     */
    List<Token> getUserTransferRecords(String email);

    /**
     * ===================================================================
     * @desc 普通用户向普通用户转账
     * @author PandaClark
     * @param dto 保存转账操作相关数据的对象
     * ===================================================================
     */
    boolean tokenTransferU2U(TokenTransferDto dto);


    /**
     * ===================================================================
     * @desc 普通用户向机构用户转账
     * @author PandaClark
     * @param dto 保存转账操作相关数据的对象
     * ===================================================================
     */
    boolean tokenTransferU2C(TokenTransferDto dto);

    /**
     * =============================================================
     * @desc 返回用户余额信息
     * @author PandaClark
     * @date 2019/5/16 6:26 PM
     * @return team.educoin.transaction.pojo.UserInfo
     * =============================================================
     */
    UserInfo getUserById(String email);

    void userConsumeService(String email, String serviceID, FileInfo fileInfo);

    List<UserInfo> getUserList();

    boolean registerUser(UserInfo userInfo);

    boolean deleteUser(String email);

    boolean updateUserInfo(UserInfo userInfo);
}
