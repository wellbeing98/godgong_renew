package com.example.godgong;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
//import java.util.Dictionary;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }
    private ArrayList<Dictionary> mList;
    private OnItemClickListener mItemClickListener = null;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.mItemClickListener = listener;
    }



    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView num;
        protected TextView userId;
        protected TextView korean;


        public CustomViewHolder(View view) {
            super(view);
            this.num = (TextView) view.findViewById(R.id.num_listitem);
            this.userId = (TextView) view.findViewById(R.id.userId_listitem);
            this.korean = (TextView) view.findViewById(R.id.korean_listitem);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos!= RecyclerView.NO_POSITION){
                        if(mItemClickListener != null){
                            mItemClickListener.onItemClick(view, pos);

                        }
                    }
                }
            });
        }

    }


    public CustomAdapter(ArrayList<Dictionary> list) {
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_list, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;

    }




    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.num.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.userId.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
        viewholder.korean.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);

        viewholder.num.setGravity(Gravity.CENTER);
        viewholder.userId.setGravity(Gravity.CENTER);
        viewholder.korean.setGravity(Gravity.CENTER);



        viewholder.num.setText(mList.get(position).getNum());
        viewholder.userId.setText(mList.get(position).getUserId());
        viewholder.korean.setText(mList.get(position).getKorean());
    }



    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
