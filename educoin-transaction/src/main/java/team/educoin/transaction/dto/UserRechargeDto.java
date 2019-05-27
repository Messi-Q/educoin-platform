package team.educoin.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @description: 接受前端数据和请求 fabric 的数据传输对象
 * @author: Messi-Q
 * @create: 2019-05-27
 */
public class UserRechargeDto implements Serializable {

    @JsonProperty( value = "$class" )
    private String className;
    private double tokenNum;
    private String rechargeID;
    private String user;

    public UserRechargeDto() {
    }

    public UserRechargeDto(String className, double tokenNum, String rechargeID, String user) {
        this.className = className;
        this.tokenNum = tokenNum;
        this.rechargeID = rechargeID;
        this.user = user;
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

    public String getRechargeID() {
        return rechargeID;
    }

    public void setRechargeID(String rechargeID) {
        this.rechargeID = rechargeID;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserRechargeDto{" +
                "className='" + className + '\'' +
                ", tokenNum=" + tokenNum +
                ", rechargeID='" + rechargeID + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
