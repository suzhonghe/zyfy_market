package com.zhongyang.java.vo;

/**
 * Created by Matthew on 2016/1/22.
 */
public class Agreement {
    private String user_id;
    private String account_id;
    private String user_bind_agreement_list;


    public Agreement(String user_id, String account_id, String user_bind_agreement_list) {
        this.user_id = user_id;
        this.account_id = account_id;
        this.user_bind_agreement_list = user_bind_agreement_list;
    }

    public Agreement() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getUser_bind_agreement_list() {
        return user_bind_agreement_list;
    }

    public void setUser_bind_agreement_list(String user_bind_agreement_list) {
        this.user_bind_agreement_list = user_bind_agreement_list;
    }

    @Override
    public String toString() {
        return "Agreements{" +
                "user_id='" + user_id + '\'' +
                ", account_id='" + account_id + '\'' +
                ", user_bind_agreement_list='" + user_bind_agreement_list + '\'' +
                '}';
    }
}
