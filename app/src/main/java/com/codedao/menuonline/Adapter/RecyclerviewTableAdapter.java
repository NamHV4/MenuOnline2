package com.codedao.menuonline.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codedao.menuonline.Interface.RecyclerviewTableItemClick;
import com.codedao.menuonline.Model.Table;
import com.codedao.menuonline.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 30/01/2018.
 */

public class RecyclerviewTableAdapter extends RecyclerView.Adapter<RecyclerviewTableAdapter.ViewHolder> {
    ArrayList<Table> listTables;
    LayoutInflater layoutInflater;
    Context context;
    RecyclerviewTableItemClick recyclerviewTableItemClick;

    public RecyclerviewTableAdapter(ArrayList<Table> listTables, Context context, RecyclerviewTableItemClick recyclerviewTableItemClick) {
        this.listTables = listTables;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.recyclerviewTableItemClick = recyclerviewTableItemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.table_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.index.setText(context.getString(R.string.table) + " " + listTables.get(position).getIndex());
        holder.table.setImageResource(listTables.get(position).getEmtyChair() == 0 ? R.drawable.full_table : R.drawable.emty_table);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recyclerviewTableItemClick.onItemCick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTables.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView index;
        ImageView table;

        public ViewHolder(View itemView) {
            super(itemView);
            index = itemView.findViewById(R.id.tvIndex);
            table = itemView.findViewById(R.id.imgTable);
        }
    }
}
