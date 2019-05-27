package team.educoin.transaction.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( value = "用户信息")
public class UserInfo {
    private String email;
    private String password;
    @ApiModelProperty( hidden = true )
    private String fingerprint;
    @ApiModelProperty( hidden = true )
    private String iris;
    private String qq;
    @ApiModelProperty( hidden = true )
    private String identityCard;
    @ApiModelProperty( hidden = true )
    private String buyerType;
    private Integer age;
    private String sexual;
    private String educationLevel;
    private String address;
    @ApiModelProperty( hidden = true )
    private Double accountBalance;
    @ApiModelProperty( hidden = true )
    private String bankAccount;


    public UserInfo() {
    }

    public UserInfo(String email, String qq, String identityCard, String buyerType, Integer age, String sexual, String educationLevel, String address, String bankAccount) {
        this.email = email;
        this.qq = qq;
        this.identityCard = identityCard;
        this.buyerType = buyerType;
        this.age = age;
        this.sexual = sexual;
        this.educationLevel = educationLevel;
        this.address = address;
        this.bankAccount = bankAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public String getIris() {
        return iris;
    }

    public void setIris(String iris) {
        this.iris = iris;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSexual() {
        return sexual;
    }

    public void setSexual(String sexual) {
        this.sexual = sexual;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }
}
