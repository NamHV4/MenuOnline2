package com.codedao.menuonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 01/02/2018.
 */

public class RecyclerviewBlockAdapter extends RecyclerView.Adapter<RecyclerviewBlockAdapter.Viewholder> {
    ArrayList<Block> mListBlocks;
    LayoutInflater mLayoutInflater;
    Context mContext;
    RecyclerviewBlockItemClick recyclerviewBlockItemClick;

    public RecyclerviewBlockAdapter(ArrayList<Block> listBlock, Context context, RecyclerviewBlockItemClick recyclerviewBlockItemClick) {
        this.mListBlocks = listBlock;
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.recyclerviewBlockItemClick = recyclerviewBlockItemClick;
    }

    @Override
    public RecyclerviewBlockAdapter.Viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.block_item, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerviewBlockAdapter.Viewholder holder, final int position) {
        holder.blockCounter.setText(mListBlocks.get(position).getCounter() + "");
        holder.blockContent.setText(mListBlocks.get(position).getContent());
        switch (position % 7) {
            case 0:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.tear));
                break;
            case 1:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.cyan));
                break;
            case 2:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.yellow));
                break;
            case 3:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.deepOrange));
                break;
            case 4:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.indigo));
                break;
            case 5:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                break;
            case 6:
                holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.purple));
                break;

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerviewBlockItemClick.onItemCick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListBlocks.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView blockCounter, blockContent;

        public Viewholder(View itemView) {
            super(itemView);
            blockContent = itemView.findViewById(R.id.blockContent);
            blockCounter = itemView.findViewById(R.id.blockCounter);
        }
    }
}
