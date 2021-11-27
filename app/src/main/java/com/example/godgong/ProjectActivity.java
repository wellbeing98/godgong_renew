package com.example.godgong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Date;

public class ProjectActivity extends AppCompatActivity{

    // 파이어베이스 데이터베이스 연동
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
    //private DatabaseReference databaseReference = database.getReference();

    // 사용할 컴포넌트 선언
    String idkey="";
    TextView title_tv, content_tv, date_tv, id_tv;
    LinearLayout register_layout;
//    EditText comment_et;
    Button reg_button;
    Button cancel_button;
    Button chat_room;
    ImageView im_project;

    // 선택한 게시물의 번호
    String board_seq = "";

    // 유저아이디 변수
    String userid = "";
    String writer="";
    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;
    String email="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);
        mFirebaseAuth = FirebaseAuth.getInstance();

        // 컴포넌트 초기화
        title_tv = findViewById(R.id.title_tv);
        content_tv = findViewById(R.id.content_tv);
        date_tv = findViewById(R.id.date_tv);
        id_tv = findViewById(R.id.id_tv);
        im_project = findViewById(R.id.im_project);
        chat_room = findViewById(R.id.chatroom);
        register_layout = findViewById(R.id.register_layout);
//        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);
        cancel_button = findViewById(R.id.cancel_button);
        board_seq = getIntent().getStringExtra("board_seq");
        userid = getIntent().getStringExtra("userid");

        mDatabase = FirebaseDatabase.getInstance().getReference("GodGong");
//
//        // 해당 게시물의 데이터 불러오기
        SharedPreferences test = getSharedPreferences("test", MODE_PRIVATE);

        String key = test.getString("key","DEFAULT");

        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);
                writer = post.getWriterId();
                String date = post.getDate();
                String title = post.getTitle_et();
                String body = post.getContent_et();
                email = post.getEmailId();
                //ImageView image = post.getImage();
                String uid  = firebaseUser.getUid();

                title_tv.setText(title);
                content_tv.setText(body);
                date_tv.setText(date);
                id_tv.setText(email);
                //im_   id.seti
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                StorageReference pathReference = storageReference.child("images");
                if(pathReference == null){
                    Toast.makeText(ProjectActivity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();}
                else{
                    Toast.makeText(ProjectActivity.this, "저장소에 사진이 있습니다.", Toast.LENGTH_SHORT).show();


                    StorageReference submitProfile = storageReference.child("images/"+email);


                    Glide.with(ProjectActivity.this /* context */)
                            .load(submitProfile)
                            .into(im_project);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("projectposts").child(key).addListenerForSingleValueEvent(postListener);

        // 댓글을 뿌릴 LinearLayout 자식뷰 모두 제거



        ValueEventListener registerListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                register_layout.removeAllViews();

                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {

                    // custom_comment 를 불러오기 위한 객체
                    LayoutInflater layoutInflater = LayoutInflater.from(ProjectActivity.this);

                    View customView = layoutInflater.inflate(R.layout.custom_register, null);

                    Register Emailid = commentSnapshot.getValue(Register.class);


                    if (Emailid != null) {

                        String userid = Emailid.getId();


                        ((TextView) customView.findViewById(R.id.regi_userid_tv)).setText(userid);
                        ImageView profimage =customView.findViewById(R.id.profimage);
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageReference = storage.getReference();
                        StorageReference pathReference = storageReference.child("images");
                        if(pathReference == null){
                            Toast.makeText(ProjectActivity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();}
                        else{
                            Toast.makeText(ProjectActivity.this, "저장소에 사진이 있습니다.", Toast.LENGTH_SHORT).show();


                            StorageReference submitProfile = storageReference.child("images/"+userid);


                            Glide.with(ProjectActivity.this /* context */)
                                    .load(submitProfile)
                                    .into(profimage);
                        }

//                    Toast.makeText(DetailActivity.this, com.getId(), Toast.LENGTH_SHORT).show();


                        // 댓글 레이아웃에 custom_comment 의 디자인에 데이터를 담아서 추가
                        register_layout.addView(customView);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("register").child(key).addValueEventListener(registerListener);

//        mDatabase.child("posts").child("key").push().setValue(comment);





        reg_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
//                if (!comment_et.getText().toString().equals("")) {

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String getTime = sdf.format(date);

//                    String com = comment_et.getText().toString();

                    String getId = firebaseUser.getEmail();

                    Register userId = new Register(getId, getTime);
//

                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();


                    idkey = mDatabase.child("register").child(key).push().getKey();



                    mDatabase.child("register").child(key).child(idkey).setValue(userId);

//                if(pathReference == null){
//                    Toast.makeText(ProjectActivity.this, "저장소에 사진이 없습니다.", Toast.LENGTH_SHORT).show();}
//                else{
//                    Toast.makeText(ProjectActivity.this, "저장소에 사진이 있습니다.", Toast.LENGTH_SHORT).show();
//
//
//                    StorageReference submitProfile = storageReference.child("images/"+firebaseUser.getEmail());
//
//
//                    Glide.with(this /* context */)
//                            .load(submitProfile)
//                            .into(im_id);
//                }
                    //댓글 입력창의 글자는 공백으로 만듦
//                    comment_et.setText("");

                    // 소프트 키보드 숨김처리
//                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                    imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);

                    // 토스트메시지 출력
                    Toast.makeText(ProjectActivity.this, "프로젝트에 참여되었습니다.", Toast.LENGTH_SHORT).show();

                    // 댓글 변환시 불러오는 함수


                }


//            }
        });
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference dataref = mDatabase.child("register").child(key).child(idkey);
                dataref.removeValue();

                Toast.makeText(ProjectActivity.this, "프로젝트에 참여를 취소하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        chat_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id_tv.getText().toString().equals("") || title_tv.getText().toString().equals(""))
                    return;

                Intent intent = new Intent(ProjectActivity.this, ChatActivity.class);
                intent.putExtra("chatName", title_tv.getText().toString());
                intent.putExtra("userName", id_tv.getText().toString());
                startActivity(intent);
            }
        });



    }
}
