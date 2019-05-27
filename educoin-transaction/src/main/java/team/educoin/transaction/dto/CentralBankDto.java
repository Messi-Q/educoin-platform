package team.educoin.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @description: 中央账户
 * @author: PandaClark
 * @create: 2019-05-12
 */
public class CentralBankDto implements Serializable {

    @JsonProperty(value = "$class")
    private String className;
    private double totalIssueToken;
    private double totalWithdrawToken;
    private String email;
    private double accountBalance;

    public CentralBankDto() {
    }

    public CentralBankDto(String className, double totalIssueToken, double totalWithdrawToken, String email, double accountBalance) {
        this.className = className;
        this.totalIssueToken = totalIssueToken;
        this.totalWithdrawToken = totalWithdrawToken;
        this.email = email;
        this.accountBalance = accountBalance;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getTotalIssueToken() {
        return totalIssueToken;
    }

    public void setTotalIssueToken(double totalIssueToken) {
        this.totalIssueToken = totalIssueToken;
    }

    public double getTotalWithdrawToken() {
        return totalWithdrawToken;
    }

    public void setTotalWithdrawToken(double totalWithdrawToken) {
        this.totalWithdrawToken = totalWithdrawToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "CentralBankDto{" +
                "className='" + className + '\'' +
                ", totalIssueToken=" + totalIssueToken +
                ", totalWithdrawToken=" + totalWithdrawToken +
                ", email='" + email + '\'' +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
