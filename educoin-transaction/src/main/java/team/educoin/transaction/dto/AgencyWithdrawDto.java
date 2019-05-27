package team.educoin.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @description: fabric post 请求数据传输对象
 * @author: PandaClark
 * @create: 2019-05-13
 */
public class AgencyWithdrawDto implements Serializable {

    @JsonProperty( value = "$class" )
    private String className;
    private double tokenNum;
    private String tokenWithdrawID;
    private String company;

    public AgencyWithdrawDto() {
    }

    public AgencyWithdrawDto(String className, double tokenNum, String tokenWithdrawID, String company) {
        this.className = className;
        this.tokenNum = tokenNum;
        this.tokenWithdrawID = tokenWithdrawID;
        this.company = company;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getTokenNum() {
        return tokenNum;
    }

    public void setTokenNum(double tokenNum) {
        this.tokenNum = tokenNum;
    }

    public String getTokenWithdrawID() {
        return tokenWithdrawID;
    }

    public void setTokenWithdrawID(String tokenWithdrawID) {
        this.tokenWithdrawID = tokenWithdrawID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
