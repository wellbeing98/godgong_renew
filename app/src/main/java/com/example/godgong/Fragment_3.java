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

public class Fragment_3 extends Fragment {
    private ArrayList<Dictionary> mArrayList3;
    private CustomAdapter mAdapter3;
    private int count3 = -1;
    LinearLayoutManager mLinearLayoutManager3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.frame_3p, container, false);
        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_main_list3);
        mLinearLayoutManager3 = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager3);


        mArrayList3 = new ArrayList<>();

        mAdapter3 = new CustomAdapter(mArrayList3);
        mRecyclerView.setAdapter(mAdapter3);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager3.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);



        Button buttonInsert = (Button) rootView.findViewById(R.id.button_main_insert3);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count3++;

                Dictionary data = new Dictionary(count3+"","UserId" , "주제문" );

                mArrayList3.add(0, data); //RecyclerView의 첫 줄에 i삽입
//                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입

                mAdapter3.notifyDataSetChanged();             }
        });
        return rootView;
    }

}

