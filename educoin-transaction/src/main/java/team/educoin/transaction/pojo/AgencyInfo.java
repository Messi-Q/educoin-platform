package team.educoin.transaction.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel( value = "机构信息" )
public class AgencyInfo {
    private String email;
    private String password;
    @ApiModelProperty( hidden = true )
    private String fingerprint;
    @ApiModelProperty( hidden = true )
    private String iris;
    @ApiModelProperty( hidden = true )
    private String registrationNumber;
    private String address;
    private String businessScope;
    @ApiModelProperty( hidden = true )
    private String yycode;
    private String type;
    private String time;
    private String businessTerm;
    private String qq;
    @ApiModelProperty( hidden = true )
    private String identityCard;
    private String legalRepresentative;
    private Integer age;
    private String sexual;
    private String educationLevel;
    @ApiModelProperty( hidden = true )
    private Double accountBalance;
    private String bankAccount;


    public AgencyInfo() {
    }

    public AgencyInfo(String email, String registrationNumber, String address, String businessScope, String yycode, String type, String qq, String identityCard, String legalRepresentative, String bankAccount) {
        this.email = email;
        this.registrationNumber = registrationNumber;
        this.address = address;
        this.businessScope = businessScope;
        this.yycode = yycode;
        this.type = type;
        this.qq = qq;
        this.identityCard = identityCard;
        this.legalRepresentative = legalRepresentative;
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

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    public String getYycode() {
        return yycode;
    }

    public void setYycode(String yycode) {
        this.yycode = yycode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBusinessTerm() {
        return businessTerm;
    }

    public void setBusinessTerm(String businessTerm) {
        this.businessTerm = businessTerm;
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

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
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
