package team.educoin.transaction.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @description: 接受前端数据和请求 fabric 的数据传输对象
 * @author: Messi-Q
 * @create: 2019-05-27
 */
@ApiModel( value = "转账信息" )
public class TokenTransferDto implements Serializable {

    @ApiModelProperty( hidden = true )
    @JsonProperty( value = "$class" )
    private String className;
    @ApiModelProperty( example = "6.6" )
    private double transferNum;
    @ApiModelProperty( hidden = true )
    private String transferID;
    @ApiModelProperty( hidden = true )
    private String fromuser;
    @ApiModelProperty( example = "普通用户用例:test@qq.com / 机构用户用例:ZjuEducation@email.com" )
    private String to;

    public TokenTransferDto() {
    }

    public TokenTransferDto(String className, double transferNum, String transferID, String fromuser, String to) {
        this.className = className;
        this.transferNum = transferNum;
        this.transferID = transferID;
        this.fromuser = fromuser;
        this.to = to;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public double getTransferNum() {
        return transferNum;
    }

    public void setTransferNum(double transferNum) {
        this.transferNum = transferNum;
    }

    public String getTransferID() {
        return transferID;
    }

    public void setTransferID(String transferID) {
        this.transferID = transferID;
    }

    public String getFromuser() {
        return fromuser;
    }

    public void setFromuser(String fromuser) {
        this.fromuser = fromuser;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "TokenTransferDto{" +
                "className='" + className + '\'' +
                ", transferNum=" + transferNum +
                ", transferID='" + transferID + '\'' +
                ", fromuser='" + fromuser + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
