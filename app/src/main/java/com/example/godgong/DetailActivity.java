package com.example.godgong;

import android.content.Context;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;


public class DetailActivity extends AppCompatActivity {

    // 파이어베이스 데이터베이스 연동
    //private FirebaseDatabase database = FirebaseDatabase.getInstance();

    //DatabaseReference는 데이터베이스의 특정 위치로 연결하는 거라고 생각하면 된다.
    //현재 연결은 데이터베이스에만 딱 연결해놓고
    //키값(테이블 또는 속성)의 위치 까지는 들어가지는 않은 모습이다.
    //private DatabaseReference databaseReference = database.getReference();

    // 사용할 컴포넌트 선언
    TextView title_tv, content_tv, date_tv, id_tv;
    LinearLayout comment_layout;
    EditText comment_et;
    Button reg_button;
    ImageView im_id;

    // 선택한 게시물의 번호
    String board_seq = "";

    // 유저아이디 변수
    String userid = "";

    FirebaseAuth mFirebaseAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mFirebaseAuth = FirebaseAuth.getInstance();

        // 컴포넌트 초기화
        title_tv = findViewById(R.id.title_tv);
        content_tv = findViewById(R.id.content_tv);
        date_tv = findViewById(R.id.date_tv);
        id_tv = findViewById(R.id.id_tv);
        im_id = findViewById(R.id.im_id);

        comment_layout = findViewById(R.id.comment_layout);
        comment_et = findViewById(R.id.comment_et);
        reg_button = findViewById(R.id.reg_button);

        board_seq = getIntent().getStringExtra("board_seq");
        userid = getIntent().getStringExtra("userid");

        mDatabase = FirebaseDatabase.getInstance().getReference("GodGong");
//
//        // 해당 게시물의 데이터 불러오기


        FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();

        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);

                //String date = post.get();
                String title = post.getTitle_et();
                String body = post.getContent_et();
                //ImageView image = post.getImage();
                //String uid  = firebaseUser.getUid();

                title_tv.setText(title);
                content_tv.setText(body);
                //date_tv.setText(date);
                //id_tv.setText(uid);
                //im_id.seti

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("UserAccount").child(firebaseUser.getUid()).child("post").addListenerForSingleValueEvent(postListener);

        // 댓글을 뿌릴 LinearLayout 자식뷰 모두 제거


        ValueEventListener commentListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                comment_layout.removeAllViews();

                for (DataSnapshot commentSnapshot : snapshot.getChildren()) {

                    // custom_comment 를 불러오기 위한 객체
                    LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);

                    View customView = layoutInflater.inflate(R.layout.custom_comment, null);

                    Comment com = commentSnapshot.getValue(Comment.class);


                    if (com != null) {

                        String userid = com.getId();
                        String content = com.getComment();
                        String crt_dt = com.getDate();

                        ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(userid);
                        ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(content);
                        ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(crt_dt);

//                    Toast.makeText(DetailActivity.this, com.getId(), Toast.LENGTH_SHORT).show();


                        // 댓글 레이아웃에 custom_comment 의 디자인에 데이터를 담아서 추가
                        comment_layout.addView(customView);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("UserAccount").child(firebaseUser.getUid()).child("post").child("comment").addValueEventListener(commentListener);






        reg_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                if (!comment_et.getText().toString().equals("")) {

                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    String getTime = sdf.format(date);

                    String com = comment_et.getText().toString();

                    String getId = firebaseUser.getEmail();

                    Comment comment = new Comment(getId, getTime, com);

                    FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                    mDatabase.child("UserAccount").child(firebaseUser.getUid()).child("post").child("comment").push().setValue(comment);


                    //댓글 입력창의 글자는 공백으로 만듦
                    comment_et.setText("");

                    // 소프트 키보드 숨김처리
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(comment_et.getWindowToken(), 0);

                    // 토스트메시지 출력
                    Toast.makeText(DetailActivity.this, "댓글이 등록되었습니다.", Toast.LENGTH_SHORT).show();

                    // 댓글 변환시 불러오는 함수


                } else {
                    Toast.makeText(DetailActivity.this, "댓글이 존재하지 않습니다", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }



}