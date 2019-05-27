package team.educoin.transaction.pojo;

import java.util.Date;

/**
 * @description: 普通 Java 类，对应 token_transfer 数据库表
 * @author: PandaClark
 * @create: 2019-04-27
 */
public class Token {
    private int id;
    private String transferId;
    private String fromEmail;
    private String toEmail;
    private int beneficiaryType;
    private double transferAmount;
    private Date createTime;
    private Date updateTime;

    public Token() {
    }

    public Token(String transferId, String fromEmail, String toEmail, int beneficiaryType, double transferAmount) {
        this.transferId = transferId;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;
        this.beneficiaryType = beneficiaryType;
        this.transferAmount = transferAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public void setFromEmail(String fromEmail) {
        this.fromEmail = fromEmail;
    }

    public String getToEmail() {
        return toEmail;
    }

    public void setToEmail(String toEmail) {
        this.toEmail = toEmail;
    }

    public int getBeneficiaryType() {
        return beneficiaryType;
    }

    public void setBeneficiaryType(int beneficiaryType) {
        this.beneficiaryType = beneficiaryType;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", transferId='" + transferId + '\'' +
                ", fromEmail='" + fromEmail + '\'' +
                ", toEmail='" + toEmail + '\'' +
                ", beneficiaryType=" + beneficiaryType +
                ", transferAmount=" + transferAmount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
