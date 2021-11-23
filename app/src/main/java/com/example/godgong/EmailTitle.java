package com.example.godgong;




import android.widget.ImageView;

import java.util.List;


class EmailTitle {

    private Post post;
//    public int starCount = 0;
    private String title_et;

    private String emailId;   //이메일 아이디

//    public void setImage(ImageView image){
//        this.image = image;
//    }
//    public ImageView getImage(){
//        return image;
//    }
//    public void setTitle_et(String title_et) {
//        post.setTitle_et(title_et);
//    }
//
//    public void setContent_et(String content_et) {
//        post.setContent_et(content_et);
//    }





    public String getEmailId(){return emailId;}

    private String password;  //비밀번호
    public  Post getPost(){
        return post;
    }
    public void setPost(Post post){
        this.post = post;
    }

    public void setIdToken(String uid) {
    }

    public EmailTitle(String emailId, Post post) {
        this.emailId = emailId;
        this.post = post;
    }
    public EmailTitle() {

    }
 
}
