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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    private ArrayList<Dictionary> mArrayList = new ArrayAdapter<Dictionary>();
    private CustomAdapter mAdapter;
    private int count = -1;
    LinearLayoutManager mLinearLayoutManager;
    private FirebaseAuth mFirebaseAuth;
    //-----
    private FirebaseDatabase mDatabase;
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
        ;
        List<Object> Array = new ArrayList<Object>();
        RecyclerView listView =   (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list);

        initDatabase();
        DatabaseReference mDatabaseRef;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("GodGong");
        mArrayList = new ArrayAdapter<Dictionary>();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mAdapter = new CustomAdapter(mArrayList);

        mReference = mDatabase.getReference().child("UserAccount").child(firebaseUser.getUid()); // 변경값을 확인할 child 이름
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mArrayList.clear();
                for (DataSnapshot messageData : dataSnapshot.getChildren()) {

                    // child 내에 있는 데이터만큼 반복합니다.
                    messageData.getValue()
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



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



        Button buttonInsert = (Button) rootView.findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getActivity() , WritingActivity.class);
                startActivity(intent);
                mFirebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                count++;
                DatabaseReference mDatabaseRef= FirebaseDatabase.getInstance().getReference("GodGong");
                String userId = "";

                mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).child("emailId").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                            Toast.makeText(getActivity(), "에러.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Log.d("firebase",String.valueOf(task.getResult().getValue()));
                            Toast.makeText(getActivity(), "성공.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                Dictionary data = new Dictionary(count+"",userId , "주제문" );

                mArrayList.add(0, data); //RecyclerView의 첫 줄에 i삽입
//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입



                mAdapter.notifyDataSetChanged();


            }
        });

        return rootView;
    }
    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("god");
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
    protected void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }

}

