package com.example.godgong;


import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.KeyStore;
import java.util.ArrayList;

public class Fragment_2 extends Fragment {
    String Title, emailId;
    private ArrayList<Dictionary> mArrayList2;
    private CustomAdapter mAdapter2;

    LinearLayoutManager mLinearLayoutManager2;
    private DatabaseReference mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frame_2p, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list2);

        initDatabase();
        DatabaseReference mDatabaseRef;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GodGong");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list2);
        mLinearLayoutManager2 = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager2);

        mArrayList2 = new ArrayList<>();

        mAdapter2 = new CustomAdapter( mArrayList2);
        mAdapter2.setOnItemClickListener(
                new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {

//                        Intent intent = new Intent( getActivity() , DetailActivity.class);
//                        startActivity(intent);
                        Dictionary temp =mArrayList2.get(pos);
                        String key = temp.getToken();
                        SharedPreferences test = getActivity().getSharedPreferences("test", MODE_PRIVATE);

                        SharedPreferences.Editor editor = test.edit();

                        editor.putString("key", key); //First라는 key값으로 infoFirst 데이터를 저장한다.



                        editor.commit();




                        Intent intent = new Intent( getActivity() , DetailActivity.class);
                        startActivity(intent);
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter2);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager2.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mLinearLayoutManager2 = new LinearLayoutManager(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference("GodGong");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mArrayList2.clear();
                for (DataSnapshot messageData : snapshot.getChildren()) {
                    KeyStore firebaseInstanceId;


                    Post post = messageData.getValue(Post.class);
                    emailId = post.getEmailId();
                    String Token = post.getToken();

                    if(post!=null) {
                        String Title = post.getTitle_et();
//                        String content = post.getContent_et();

                        Dictionary data = new Dictionary(post.getDate().substring(5,10), emailId, Title, Token);
//                        count++;
                        mArrayList2.add(0, data); //RecyclerView의 첫 줄에 i삽입
                    }




//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입



//                    ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(userid);
//                    ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(content);
//                    ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(crt_dt);


                    // 댓글 레이아웃에 custom_comment 의 디자인에 데이터를 담아서 추가
//                    comment_layout.addView(customView);


                }
                mAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("studypost").addValueEventListener(postListener);







        Button buttonInsert = (Button) rootView.findViewById(R.id.button_main_insert2);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( getActivity() , WritingStudyActivity.class);
                startActivity(intent);

//                Dictionary data = new Dictionary(count2+"","UserId" , "주제문","1");
//
//                mArrayList2.add(0, data); //RecyclerView의 첫 줄에 i삽입
////                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
//
//                mAdapter2.notifyDataSetChanged();
//                Intent intent = new Intent( getActivity() , WritingChatActivity.class);
//                startActivity(intent);
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
    @Override
    public void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }
}
