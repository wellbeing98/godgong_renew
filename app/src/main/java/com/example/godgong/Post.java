package com.example.godgong;


import android.widget.ImageView;


class Post {

    public String title_et;
    public String content_et;
    public int starCount = 0;
    public ImageView image;
    private String emailId;   //이메일 아이디
    private String token;
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

    public void setContent_et(String content_et) {
        this.content_et = content_et;
    }

    public String getTitle_et() {
        return title_et;
    }

    public String getContent_et() {
        return content_et;
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
    }

//    public void setEmailId(String emailId) {
//        this.emailId = emailId;
//    }
}
