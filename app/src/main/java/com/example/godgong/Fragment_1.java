package com.example.godgong;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment_1 extends Fragment {
    String Title,emailId;
    private ArrayList<Dictionary> mArrayList;
    private CustomAdapter mAdapter;
    private int count = -1;
    LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    //-----
    private DatabaseReference mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;

    private ArrayAdapter<Dictionary> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frame_1p, container, false);

        RecyclerView mRecyclerView;




        initDatabase();
        DatabaseReference mDatabaseRef;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GodGong");
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();

        mAdapter = new CustomAdapter(mArrayList);
        mAdapter.setOnItemClickListener(
                new CustomAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int pos) {

                        Intent intent = new Intent( getActivity() , DetailActivity.class);
                        startActivity(intent);
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        mLinearLayoutManager = new LinearLayoutManager(getActivity());




        mDatabase = FirebaseDatabase.getInstance().getReference("GodGong");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                mArrayList.clear();
                for (DataSnapshot messageData : snapshot.getChildren()) {

                    EmailTitle emailTitle = messageData.getValue(EmailTitle.class);
                    emailId = emailTitle.getEmailId();
                    for (DataSnapshot messagePost  : snapshot.child("post").getChildren()) {
                            Post post = messagePost.getValue(Post.class);
                            if(post!=null) {
                                String Title = post.getTitle_et();
//                        String content = post.getContent_et();

                                Dictionary data = new Dictionary(count + "", emailId, Title);
                                mArrayList.add(0, data); //RecyclerView의 첫 줄에 i삽입
                            }
                        }



//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입



//                    ((TextView) customView.findViewById(R.id.cmt_userid_tv)).setText(userid);
//                    ((TextView) customView.findViewById(R.id.cmt_content_tv)).setText(content);
//                    ((TextView) customView.findViewById(R.id.cmt_date_tv)).setText(crt_dt);


                        // 댓글 레이아웃에 custom_comment 의 디자인에 데이터를 담아서 추가
//                    comment_layout.addView(customView);


                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        mDatabase.child("UserAccount").addValueEventListener(postListener);






        Button buttonInsert = (Button) rootView.findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity() , WritingActivity.class);
                startActivity(intent);


                count++;

                String userId = "";

//                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DataSnapshot> task) {
//                        if (!task.isSuccessful()) {
//                            Log.e("firebase", "Error getting data", task.getException());
//                            Toast.makeText(getActivity(), "에러.", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Log.d("firebase",String.valueOf(task.getResult().getValue()));
//                            Toast.makeText(getActivity(), "성공.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//                Dictionary data = new Dictionary(count+"",firebaseUser.getEmail() , Title );
//
//                mArrayList.add(0, data); //RecyclerView의 첫 줄에 i삽입
////                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입
//
//
//
//                mAdapter.notifyDataSetChanged();


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

