package com.example.godgong;


public class Dictionary {


    private String num;
    private String userId;
    private String Korean;

    public String getNum() {
        return num;
    }

    public void setId(String num) {
        this.num = num;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getKorean() {
        return Korean;
    }

    public void setKorean(String korean) {
        Korean = korean;
    }
    public Dictionary(String num, String userId, String korean) {
        this.num = num;
        this.userId = userId;
        Korean = korean;
    }
}
