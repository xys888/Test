package com.example.demo.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class AccountBillTradeBlotter implements Serializable {

    private static final long serialVersionUID = 537086753978366892L;

    private String abtbSeq;

    private String mainAccTransTxn;

    private String busiType;

    private Date busiDate;

    private String busiSys;

    private Date busiCreateTime;

    private Byte busiProgress;

    private String busiReason;

    private String busiObj;

    private Date accCreateTime;

    private Date accUpdateTime;

    private Byte status;

    private String busiCallbackUrl;

    private String bankCard;

    private String bankSysDate;

    private String bankSysTime;

    private String bankDealStatus;

    private String bankDealCode;

    private String bankDealMsg;

    private String bankSummary;

    private String outSysCode;

    private String outChnCode;

    private String depoNo;

    private String subDepoNo;

    private BigDecimal amt;

    private String oppoDepoNo;

    private String oppoSubDepoNo;

    private Byte transType;

    private String remark;

    private String bankTransSts;

    public String getAbtbSeq() {
        return abtbSeq;
    }

    public void setAbtbSeq(String abtbSeq) {
        this.abtbSeq = abtbSeq;
    }

    public String getMainAccTransTxn() {
        return mainAccTransTxn;
    }

    public void setMainAccTransTxn(String mainAccTransTxn) {
        this.mainAccTransTxn = mainAccTransTxn;
    }

    public String getBusiType() {
        return busiType;
    }

    public void setBusiType(String busiType) {
        this.busiType = busiType;
    }

    public Date getBusiDate() {
        return busiDate;
    }

    public void setBusiDate(Date busiDate) {
        this.busiDate = busiDate;
    }

    public String getBusiSys() {
        return busiSys;
    }

    public void setBusiSys(String busiSys) {
        this.busiSys = busiSys;
    }

    public Date getBusiCreateTime() {
        return busiCreateTime;
    }

    public void setBusiCreateTime(Date busiCreateTime) {
        this.busiCreateTime = busiCreateTime;
    }

    public Byte getBusiProgress() {
        return busiProgress;
    }

    public void setBusiProgress(Byte busiProgress) {
        this.busiProgress = busiProgress;
    }

    public String getBusiReason() {
        return busiReason;
    }

    public void setBusiReason(String busiReason) {
        this.busiReason = busiReason;
    }

    public String getBusiObj() {
        return busiObj;
    }

    public void setBusiObj(String busiObj) {
        this.busiObj = busiObj;
    }

    public Date getAccCreateTime() {
        return accCreateTime;
    }

    public void setAccCreateTime(Date accCreateTime) {
        this.accCreateTime = accCreateTime;
    }

    public Date getAccUpdateTime() {
        return accUpdateTime;
    }

    public void setAccUpdateTime(Date accUpdateTime) {
        this.accUpdateTime = accUpdateTime;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getBusiCallbackUrl() {
        return busiCallbackUrl;
    }

    public void setBusiCallbackUrl(String busiCallbackUrl) {
        this.busiCallbackUrl = busiCallbackUrl;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankSysDate() {
        return bankSysDate;
    }

    public void setBankSysDate(String bankSysDate) {
        this.bankSysDate = bankSysDate;
    }

    public String getBankSysTime() {
        return bankSysTime;
    }

    public void setBankSysTime(String bankSysTime) {
        this.bankSysTime = bankSysTime;
    }

    public String getBankDealStatus() {
        return bankDealStatus;
    }

    public void setBankDealStatus(String bankDealStatus) {
        this.bankDealStatus = bankDealStatus;
    }

    public String getBankDealCode() {
        return bankDealCode;
    }

    public void setBankDealCode(String bankDealCode) {
        this.bankDealCode = bankDealCode;
    }

    public String getBankDealMsg() {
        return bankDealMsg;
    }

    public void setBankDealMsg(String bankDealMsg) {
        this.bankDealMsg = bankDealMsg;
    }

    public String getBankSummary() {
        return bankSummary;
    }

    public void setBankSummary(String bankSummary) {
        this.bankSummary = bankSummary;
    }

    public String getOutSysCode() {
        return outSysCode;
    }

    public void setOutSysCode(String outSysCode) {
        this.outSysCode = outSysCode;
    }

    public String getOutChnCode() {
        return outChnCode;
    }

    public void setOutChnCode(String outChnCode) {
        this.outChnCode = outChnCode;
    }

    public String getDepoNo() {
        return depoNo;
    }

    public void setDepoNo(String depoNo) {
        this.depoNo = depoNo;
    }

    public String getSubDepoNo() {
        return subDepoNo;
    }

    public void setSubDepoNo(String subDepoNo) {
        this.subDepoNo = subDepoNo;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getOppoDepoNo() {
        return oppoDepoNo;
    }

    public void setOppoDepoNo(String oppoDepoNo) {
        this.oppoDepoNo = oppoDepoNo;
    }

    public String getOppoSubDepoNo() {
        return oppoSubDepoNo;
    }

    public void setOppoSubDepoNo(String oppoSubDepoNo) {
        this.oppoSubDepoNo = oppoSubDepoNo;
    }

    public Byte getTransType() {
        return transType;
    }

    public void setTransType(Byte transType) {
        this.transType = transType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBankTransSts() {
        return bankTransSts;
    }

    public void setBankTransSts(String bankTransSts) {
        this.bankTransSts = bankTransSts;
    }

//    @Override
//    public String toString() {
//        return ReflectionToStringBuilder.toString(this);
//    }
}
