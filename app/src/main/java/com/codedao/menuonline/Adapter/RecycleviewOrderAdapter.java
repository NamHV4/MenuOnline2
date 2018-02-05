package com.codedao.menuonline.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codedao.menuonline.Model.Order;
import com.codedao.menuonline.R;

import java.util.List;

/**
 * Created by utnam on 2/5/2018.
 */

public class RecycleviewOrderAdapter extends RecyclerView.Adapter<RecycleviewOrderAdapter.MyViewHolder> {
    public RecycleviewOrderAdapter(List<Order> mOrderList) {
        this.mOrderList = mOrderList;
    }

    private List<Order> mOrderList;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_order, parent, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.txtDescription.setText(mOrderList.get(position).getmDescription());
        holder.txtTable.setText(mOrderList.get(position).getmTable());
        holder.txtSTT.setText(mOrderList.get(position).getmStt() + "");

    }

    @Override
    public int getItemCount() {
        return mOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtSTT, txtTable, txtDescription;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtSTT = itemView.findViewById(R.id.txtSTT);
            txtTable = itemView.findViewById(R.id.txtTable);
            txtDescription = itemView.findViewById(R.id.txtDescription);
        }
    }
}
