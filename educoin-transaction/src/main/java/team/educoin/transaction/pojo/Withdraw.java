package team.educoin.transaction.pojo;

import java.util.Date;

/**
 * @description: 普通 Java 类，对应 company_withdraw 数据库表
 * @author: PandaClark
 * @create: 2019-05-13
 */
public class Withdraw {

    private int id;
    private String email;
    private String adminEmail;
    private String paymentId;
    private String paymentMethod;
    private int ifChecked;
    private Double withdrawAmount;
    private Date checkTime;
    private Date createTime;
    private Date updateTime;

    public Withdraw() {
    }

    public Withdraw(String email, String paymentId, String paymentMethod, Double withdrawAmount) {
        this.email = email;
        this.paymentId = paymentId;
        this.paymentMethod = paymentMethod;
        this.withdrawAmount = withdrawAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getIfChecked() {
        return ifChecked;
    }

    public void setIfChecked(int ifChecked) {
        this.ifChecked = ifChecked;
    }

    public Double getWithdrawAmount() {
        return withdrawAmount;
    }

    public void setWithdrawAmount(Double withdrawAmount) {
        this.withdrawAmount = withdrawAmount;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
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
        return "Withdraw{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", ifChecked=" + ifChecked +
                ", withdrawAmount=" + withdrawAmount +
                ", checkTime=" + checkTime +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
