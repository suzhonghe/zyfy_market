package com.zhongyang.java.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class UmpSettleTenderRecord {
    private String orderid;

    private Date accountdate;

    private String action;

    private BigDecimal amount;

    private String inaccountid;

    private Date merdate;

    private String outaccountid;

    private Date settledate;

    private Date settletime;

    private String tenderaccountid;

    private String tenderid;

    private String transactionid;

    private String transfertype;

    public String getOrderid() {
        return orderid;
    }

    public void setOrderid(String orderid) {
        this.orderid = orderid == null ? null : orderid.trim();
    }

    public Date getAccountdate() {
        return accountdate;
    }

    public void setAccountdate(Date accountdate) {
        this.accountdate = accountdate;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getInaccountid() {
        return inaccountid;
    }

    public void setInaccountid(String inaccountid) {
        this.inaccountid = inaccountid == null ? null : inaccountid.trim();
    }

    public Date getMerdate() {
        return merdate;
    }

    public void setMerdate(Date merdate) {
        this.merdate = merdate;
    }

    public String getOutaccountid() {
        return outaccountid;
    }

    public void setOutaccountid(String outaccountid) {
        this.outaccountid = outaccountid == null ? null : outaccountid.trim();
    }

    public Date getSettledate() {
        return settledate;
    }

    public void setSettledate(Date settledate) {
        this.settledate = settledate;
    }

    public Date getSettletime() {
        return settletime;
    }

    public void setSettletime(Date settletime) {
        this.settletime = settletime;
    }

    public String getTenderaccountid() {
        return tenderaccountid;
    }

    public void setTenderaccountid(String tenderaccountid) {
        this.tenderaccountid = tenderaccountid == null ? null : tenderaccountid.trim();
    }

    public String getTenderid() {
        return tenderid;
    }

    public void setTenderid(String tenderid) {
        this.tenderid = tenderid == null ? null : tenderid.trim();
    }

    public String getTransactionid() {
        return transactionid;
    }

    public void setTransactionid(String transactionid) {
        this.transactionid = transactionid == null ? null : transactionid.trim();
    }

    public String getTransfertype() {
        return transfertype;
    }

    public void setTransfertype(String transfertype) {
        this.transfertype = transfertype == null ? null : transfertype.trim();
    }
}