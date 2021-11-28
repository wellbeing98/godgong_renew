package com.example.godgong;


import android.widget.ImageView;


class Post {

    public String title_et;
    public String content_et;
    public String zoomid_et;
    public String zoompwd_et;
    public int starCount = 0;
    public ImageView image;
    private String emailId;   //이메일 아이디
    private String token;
    private String Date;
    private String writerId;

    public java.lang.String getWriterId() {
        return writerId;
    }
    public void setWriterId(String writerId){
        this.writerId = writerId;
    }

    public String getDate(){
        return Date;
    }
    public void setDate(String Date){
        this.Date = Date;
    }

    public String getToken(){
        return token;
    }

    public void setToken(String token){
       this.token = token;
    }
    public Post() {

    }
    public void setImage(ImageView image){
        this.image = image;
    }
    public ImageView getImage(){
        return image;
    }
    public void setTitle_et(String title_et) {
        this.title_et = title_et;
    }

    public void setContent_et(String content_et) { this.content_et = content_et;}
    public void setZoomId_et(String zoomid_et) {this.zoomid_et = zoomid_et;}
    public void setZoomPwd_et(String zoompwd_et) {this.zoompwd_et = zoompwd_et;}

    public String getTitle_et() {
        return title_et;
    }

    public String getContent_et() {
        return content_et;
    }

    public String getZoomid_et() {
        return zoomid_et;
    }

    public String getZoompwd_et() {
        return zoompwd_et;
    }
    public String getEmailId(){return emailId;}
    public void setEmailId(String emailId){this.emailId = emailId;}
    private String idToken;   //파이어베이스 Uid (고유 토큰정보)

    private String password;  //비밀번호


    public void setIdToken(String uid) {
    }

    public Post(String userid, String title_et, String content_et) {

        this.title_et = title_et;
        this.content_et = content_et;
        this.zoomid_et = zoomid_et;
        this.zoompwd_et =zoompwd_et;
    }

//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
}
