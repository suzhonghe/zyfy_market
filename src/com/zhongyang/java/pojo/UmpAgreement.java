package com.zhongyang.java.pojo;

import java.util.Date;

public class UmpAgreement {
    private String accountname;
    private boolean instant;

    /*
     * 无密投资协议:ZTBB0G00
     */
    private boolean invest;

    /*
     * 无密还款协议:ZHKB0H01
     */
    private boolean repay;

    /*
     * 借记卡快捷协议:ZKJP0700
     */
    private boolean debit;
    
    /*
     * 卡号暂存，UMP绑卡结果只包含经过遮挡的卡号
     */
    private String cardno;
    
    private Date timecreated;

    private Date timelastupdated;
    
    private String userid;

    public String getAccountname() {
        return accountname;
    }

    public void setAccountname(String accountname) {
        this.accountname = accountname == null ? null : accountname.trim();
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno == null ? null : cardno.trim();
    }

    public Boolean getDebit() {
        return debit;
    }

    public void setDebit(Boolean debit) {
        this.debit = debit;
    }

    public Boolean getInstant() {
        return instant;
    }

    public void setInstant(Boolean instant) {
        this.instant = instant;
    }

    public Boolean getInvest() {
        return invest;
    }

    public void setInvest(Boolean invest) {
        this.invest = invest;
    }

    public Boolean getRepay() {
        return repay;
    }

    public void setRepay(Boolean repay) {
        this.repay = repay;
    }

    public Date getTimecreated() {
        return timecreated;
    }

    public void setTimecreated(Date timecreated) {
        this.timecreated = timecreated;
    }

    public Date getTimelastupdated() {
        return timelastupdated;
    }

    public void setTimelastupdated(Date timelastupdated) {
        this.timelastupdated = timelastupdated;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }
}