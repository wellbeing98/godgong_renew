package com.example.godgong;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Fragment_2 extends Fragment {
    private ArrayList<Dictionary> mArrayList2;
    private CustomAdapter mAdapter2;
    private int count2 = -1;
    LinearLayoutManager mLinearLayoutManager2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frame_2p, container, false);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list2);
        mLinearLayoutManager2 = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager2);


        mArrayList2 = new ArrayList<>();

        mAdapter2 = new CustomAdapter( mArrayList2);
        mRecyclerView.setAdapter(mAdapter2);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager2.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        Button buttonInsert = (Button) rootView.findViewById(R.id.button_main_insert2);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count2++;

                Dictionary data = new Dictionary(count2+"","UserId" , "주제문" );

                mArrayList2.add(0, data); //RecyclerView의 첫 줄에 i삽입
//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입

                mAdapter2.notifyDataSetChanged();             }
        });

        return rootView;
    }

}
