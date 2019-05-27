package team.educoin.transaction.service.impl;

import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.educoin.transaction.dao.RechargeMapper;
import team.educoin.transaction.dao.TokenMapper;
import team.educoin.transaction.dao.UserConsumeMapper;
import team.educoin.transaction.dao.UserInfoMapper;
import team.educoin.transaction.dto.TokenTransferDto;
import team.educoin.transaction.dto.UserRechargeDto;
import team.educoin.transaction.fabric.UserFabricClient;
import team.educoin.transaction.pojo.FileInfo;
import team.educoin.transaction.pojo.Recharge;
import team.educoin.transaction.pojo.Token;
import team.educoin.transaction.pojo.UserInfo;
import team.educoin.transaction.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static team.educoin.transaction.util.UUIDutil.getUUID;

/**
 * description:
 *
 * @author: chenzhou04
 * @create: 2019-04-16
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserFabricClient userFabricClient;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private TokenMapper tokenMapper;
    @Autowired
    private UserConsumeMapper userConsumeMapper;

    @Override
    public Map<String, Object> getUserInfo() {
        Map<String, Object> userInfoMap = new HashMap<>();
        userInfoMap.put("fabricUserInfo", userFabricClient.getUser());
        userInfoMap.put("mysqlUserInfo", userInfoMapper.selectAllUser());
        return userInfoMap;
    }




    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 12:10 PM
     * @param id payment_id
     * @return team.educoin.transaction.pojo.Recharge
     * =============================================================
     */
    @Override
    public Recharge getRechargeRecordById(String id) {
        Recharge record = rechargeMapper.getRecordByPaymentId(id);
        return record;
    }
    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 12:10 PM
     * @param email 用户ID
     * @param flag 充值记录审核状态： 0 待审核/1 通过/2 拒绝
     * @return java.util.List<Recharge>
     * =============================================================
     */
    @Override
    public List<Recharge> getUserRechargeRecords(String email, int flag) {
        // 从 mysql 中查结果
        List<Recharge> records = rechargeMapper.getRecordsByIdAndFlag(email, flag);
        return records;
    }

    /**
     * ===================================================================
     * @desc 普通用户充值
     * @author PandaClark
     * @param email 充值账户ID
     * @param balance 充值金额
     * ===================================================================
     */
    @Override
    public boolean userRecharge(String email, double balance) {
        // 0. 请求第三方支付，返回第三方支付id，暂时用 UUID 代替
        String paymentID = getUUID();

        // 1. 向 fabric 发 post 请求
        UserRechargeDto dto = new UserRechargeDto("org.education.UserRecharge", balance, paymentID, "resource:org.education.User#" + email);
        // 测试抛异常的测试用例
        // UserRechargeDto dto = new UserRechargeDto("org.education.UserRecharge", balance, paymentID, "resource:org.education.User#" + email);

        try {
            UserRechargeDto response = userFabricClient.userRechargeFabric(dto);
        } catch ( Exception e ){
            e.printStackTrace();
            return false;
        }

        // 2. 将记录写入 mysql
        Recharge recharge = new Recharge(email, paymentID, "alipay",balance);
        int insert = rechargeMapper.addRecharge(recharge);
        return insert > 0;
    }

    /**
     * =============================================================
     * @author PandaClark
     * @date 2019/5/12 1:11 PM
     * @param email 用户ID
     * @return java.util.List<Token>
     * =============================================================
     */
    @Override
    public List<Token> getUserTransferRecords(String email) {
        // 从 mysql 中查询结果
        List<Token> records = tokenMapper.getRecordsById(email);
        return records;
    }


    /**
     * ===================================================================
     * @desc 普通用户向普通用户转账
     * @author PandaClark
     * @param dto 保存转账操作相关数据的对象
     * ===================================================================
     */
    @Override
    public boolean tokenTransferU2U(TokenTransferDto dto) {

        dto.setClassName("org.education.TokenTransferU_U");
        dto.setTransferID(getUUID());

        // 1. 向 fabric 发 post 请求
        try {
            // 能接收到返回值说明 fabric 200 OK
            TokenTransferDto responseDto = userFabricClient.tokenTransferU_UFabric(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 2. 将记录写入 mysql
        Token token = new Token(dto.getTransferID(), dto.getFromuser(), dto.getTo(), 0, dto.getTransferNum());
        int insert = tokenMapper.addTransfer(token);
        return insert > 0;
    }



    /**
     * ===================================================================
     * @desc 普通用户向机构用户转账
     * @author PandaClark
     * @param dto 保存转账操作相关数据的对象
     * ===================================================================
     */
    @Override
    public boolean tokenTransferU2C(TokenTransferDto dto) {
        dto.setClassName("org.education.TokenTransferU_C");
        dto.setTransferID(getUUID());

        // 1. 向 fabric 发 post 请求
        try {
            // 能接收到返回值说明 fabric 200 OK
            TokenTransferDto responseDto = userFabricClient.tokenTransferU_UFabric(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        // 2. 将记录写入 mysql
        Token token = new Token(dto.getTransferID(), dto.getFromuser(), dto.getTo(), 1, dto.getTransferNum());
        int insert = tokenMapper.addTransfer(token);
        return insert > 0;
    }

    @Override
    public UserInfo getUserById(String email) {
        UserInfo userInfo = userInfoMapper.selectRecordById(email);
        return userInfo;
    }

    @Override
    @Transactional
    public void userConsumeService(String email, String serviceID, FileInfo fileInfo) {
        // 扣除用户余额
        UserInfo user = userInfoMapper.selectRecordById(email);
        Double amount = user.getAccountBalance() - fileInfo.getFileReadPrice();
        userInfoMapper.updateBankAccountById(email, amount.toString());
        // 记录用户消费记录
        Map<String,Object> map = new HashMap<>();
        map.put("email",email);
        map.put("service_id",fileInfo.getId());
        map.put("file_title",fileInfo.getFileTitle());
        map.put("file_readPrice",fileInfo.getFileReadPrice());
        map.put("file_name",fileInfo.getFileName());
        userConsumeMapper.addRecord(map);
    }

    @Override
    public List<UserInfo> getUserList() {
        return userInfoMapper.selectAllRecords();
    }

    @Override
    public boolean registerUser(UserInfo userInfo) {
        int i = userInfoMapper.addRecord(userInfo);
        return i > 0;
    }

    @Override
    public boolean deleteUser(String email) {
        int i = userInfoMapper.deleteById(email);
        return i > 0;
    }

    @Override
    public boolean updateUserInfo(UserInfo userInfo) {
        int i = userInfoMapper.updateRecord(userInfo);
        return i > 0;
    }

}
