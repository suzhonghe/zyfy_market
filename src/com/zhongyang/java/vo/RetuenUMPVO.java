package com.zhongyang.java.vo;

/**
 * Created by Matthew on 2016/1/25.
 */
public class RetuenUMPVO {
    private String user_id;
    private String ret_code;
    private String ret_msg;


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public RetuenUMPVO(String user_id, String ret_code, String ret_msg) {
        this.user_id = user_id;
        this.ret_code = ret_code;
        this.ret_msg = ret_msg;
    }

    public RetuenUMPVO() {
    }
}
