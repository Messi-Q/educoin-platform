package team.educoin.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * @description: 权益分配合约
 * @author: PandaClark
 * @create: 2019-05-12
 */
public class ContractDto implements Serializable {

    @JsonProperty(value = "$class")
    private String className;
    private String contractID;
    private double platformPer;
    private double companyPer;

    public ContractDto() {
    }

    public ContractDto(String className, String contractID, double platformPer, double companyPer) {
        this.className = className;
        this.contractID = contractID;
        this.platformPer = platformPer;
        this.companyPer = companyPer;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getContractID() {
        return contractID;
    }

    public void setContractID(String contractID) {
        this.contractID = contractID;
    }

    public double getPlatformPer() {
        return platformPer;
    }

    public void setPlatformPer(double platformPer) {
        this.platformPer = platformPer;
    }

    public double getCompanyPer() {
        return companyPer;
    }

    public void setCompanyPer(double companyPer) {
        this.companyPer = companyPer;
    }

    @Override
    public String toString() {
        return "ContractDto{" +
                "className='" + className + '\'' +
                ", contractID='" + contractID + '\'' +
                ", platformPer=" + platformPer +
                ", companyPer=" + companyPer +
                '}';
    }
}
