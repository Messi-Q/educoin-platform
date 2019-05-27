package team.educoin.transaction.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.educoin.transaction.dao.AdminInfoMapper;
import team.educoin.transaction.dao.FileInfoMapper;
import team.educoin.transaction.dao.RechargeMapper;
import team.educoin.transaction.dao.WithdrawMapper;
import team.educoin.transaction.dto.CentralBankDto;
import team.educoin.transaction.dto.ContractDto;
import team.educoin.transaction.fabric.AdminFabricClient;
import team.educoin.transaction.pojo.*;
import team.educoin.transaction.service.AdminService;

import java.util.List;

/**
 * @description:
 * @author: PandaClark
 * @create: 2019-05-12
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminFabricClient adminFabricClient;
    @Autowired
    private RechargeMapper rechargeMapper;
    @Autowired
    private WithdrawMapper withdrawMapper;
    @Autowired
    private AdminInfoMapper adminInfoMapper;
    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Override
    public CentralBankDto getCentralBankInfo() {
        List<CentralBankDto> centralBankDtos = adminFabricClient.getCentralBankInfo();
        return centralBankDtos.get(0);
    }

    @Override
    public ContractDto getContractInfo() {
        List<ContractDto> contractInfos = adminFabricClient.getContractInfo();
        return contractInfos.get(0);
    }

    @Override
    public List<Recharge> getUnCheckedRechargeList() {
        List<Recharge> list = rechargeMapper.getRecordsByFlag(0);
        return list;
    }

    @Override
    public List<Withdraw> getUnCheckedWithdrawList() {
        List<Withdraw> list = withdrawMapper.getRecordsByFlag(0);
        return list;
    }

    @Override
    public void acceptUserRecharge(String paymentId, String adminEmail) {
        rechargeMapper.updateRecordByPaymentId(paymentId, adminEmail, 1);
    }

    @Override
    public void rejectUserRecharge(String paymentId, String adminEmail) {
        rechargeMapper.updateRecordByPaymentId(paymentId, adminEmail, 2);
    }

    @Override
    public void acceptCompanyWithdraw(String paymentId, String admin) {
        withdrawMapper.updateRecordByPaymentId(paymentId, admin, 1);
    }

    @Override
    public void rejectCompanyWithdraw(String paymentId, String admin) {
        withdrawMapper.updateRecordByPaymentId(paymentId, admin, 2);
    }

    @Override
    public AdminInfo getAdminById(String email) {
        AdminInfo adminInfo = adminInfoMapper.selectRecordById(email);
        return adminInfo;
    }

    @Override
    public void acceptService(String admin, String id) {
        fileInfoMapper.updateFileChecked(admin, id, 1);
    }

    @Override
    public void rejectService(String admin, String id) {
        fileInfoMapper.updateFileChecked(admin, id, 2);
    }

    @Override
    public List<AdminInfo> getAdminList() {
        return adminInfoMapper.selectAllRecords();
    }

    @Override
    public boolean registerRegulator(AdminInfo adminInfo) {
        int i = adminInfoMapper.addRecord(adminInfo);
        return i > 0;
    }

    @Override
    public boolean deleteAdmin(String email) {
        int i = adminInfoMapper.deleteById(email);
        return i > 0;
    }

}
