package com.example.godgong;


public class Dictionary {


    private String num;
    private String userId;
    private String Korean;
    private String token;
    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }
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
    public Dictionary(String num, String userId, String korean,String token) {
        this.num = num;
        this.userId = userId;
        this.Korean = korean;
        this.token = token;
    }
}
