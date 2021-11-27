package com.example.godgong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyStore;
import java.util.ArrayList;


public class Fragment_3 extends Fragment {
    String Title,emailId;
    private ArrayList<Dictionary> mArrayList3;
    private CustomAdapter mAdapter3;
    private int count3 = -1;
    LinearLayoutManager mLinearLayoutManager3;
    private FirebaseAuth mFirebaseAuth;
    //-----
    private DatabaseReference mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frame_3p, container, false);


        initDatabase();
        DatabaseReference mDatabaseRef;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GodGong");

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list3);
        mLinearLayoutManager3 = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager3);


        mArrayList3 = new ArrayList<>();

        mAdapter3 = new CustomAdapter( mArrayList3);
        mAdapter3.setOnItemClickListener(
                new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {
                        Dictionary temp =mArrayList3.get(pos);
                        String key = temp.getToken();
                        SharedPreferences test = getActivity().getSharedPreferences("test", MODE_PRIVATE);

                        SharedPreferences.Editor editor = test.edit();

                        editor.putString("key", key); //First라는 key값으로 infoFirst 데이터를 저장한다.



                        editor.commit();
                        Intent intent = new Intent( getActivity() , ProjectActivity.class);
                        startActivity(intent);
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter3);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager3.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mLinearLayoutManager3 = new LinearLayoutManager(getActivity());

        mDatabase = FirebaseDatabase.getInstance().getReference("GodGong");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mArrayList3.clear();
                for (DataSnapshot messageData : snapshot.getChildren()) {
                    KeyStore firebaseInstanceId;


                    Post post = messageData.getValue(Post.class);
                    emailId = post.getEmailId();
                    String Token = post.getToken();

                    if(post!=null) {
                        String Title = post.getTitle_et();
//                        String content = post.getContent_et();

                        Dictionary data = new Dictionary(post.getDate().substring(5,10), emailId, Title, Token);

                        mArrayList3.add(0, data); //RecyclerView의 첫 줄에 i삽입
                    }




//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입



//                    ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(userid);
//                    ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(content);
//                    ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(crt_dt);


                    // 댓글 레이아웃에 custom_comment 의 디자인에 데이터를 담아서 추가
//                    comment_layout.addView(customView);


                }
                mAdapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("projectposts").addValueEventListener(postListener);



















        Button buttonInsert = (Button) rootView.findViewById(R.id.button_main_insert3);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                count3++;
//
//                Dictionary data = new Dictionary(count3+"","UserId" , "주제문" ,"1");
//
//                mArrayList3.add(0, data); //RecyclerView의 첫 줄에 i삽입
////                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
//
//                mAdapter3.notifyDataSetChanged();
                Intent intent = new Intent( getActivity() , WritingProjectActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
    private void initDatabase() {



        mReference = FirebaseDatabase.getInstance().getReference("log");
        mReference.child("log").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }
    public void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }
}


