package com.zhongyang.java.vo;

import com.zhongyang.java.system.SystemPro;

/**
 * Created by Matthew  on 2016/1/6.
 */
public class UserCert {
        private String mobile;//手机号
        private String ip;//IP地址
        private int mobileNumber;//当前获取验证码次数
        private int intervaltime;//系统规定获取验证码时间
        private int attemptNum;//系统规定获取验证码次数
        private long lastTime;//下次获取验证码时间

    public UserCert() {
        intervaltime= Integer.parseInt((String) SystemPro.getProperties().get("INTERVALTIME"));
        attemptNum= Integer.parseInt((String) SystemPro.getProperties().get("ATTEMPTNUM"));
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public int getIntervaltime() {
        return intervaltime;
    }

    public void setIntervaltime(int intervaltime) {
        this.intervaltime = intervaltime;
    }

    public long getLastTime() {
        return lastTime;
    }

    public void setLastTime(long lastTime) {
        this.lastTime = lastTime;
    }

    public int getAttemptNum() {
        return attemptNum;
    }

    public void setAttemptNum(int attemptNum) {
        this.attemptNum = attemptNum;
    }

    @Override
    public String toString() {
        return "UserCert{" +
                "mobile='" + mobile + '\'' +
                ", ip='" + ip + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", intervaltime=" + intervaltime +
                ", lastTime=" + lastTime +
                '}';
    }
}
