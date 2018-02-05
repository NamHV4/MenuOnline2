package com.codedao.menuonline.Host;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codedao.menuonline.Adapter.RecyclerviewTableAdapter;
import com.codedao.menuonline.Interface.RecyclerviewTableItemClick;
import com.codedao.menuonline.Model.Table;
import com.codedao.menuonline.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;

public class IndexScreenHost extends AppCompatActivity implements RecyclerviewTableItemClick {

    private RecyclerView mRecyclerView;
    private RecyclerviewTableAdapter mRecycleviewOrderAdapter;
    private ArrayList<Table> listTables;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
            mRecycleviewOrderAdapter.notifyDataSetChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
            mRecycleviewOrderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_screen_host);
        mRecyclerView = findViewById(R.id.rcViewData);
        dummyData();

    }

    private void dummyData() {
        listTables = new ArrayList<>();
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 6));
        }

        for (int i = 0; i < 100; i++) {
            listTables.add(new Table(i + 1, i, i));
        }
        mRecycleviewOrderAdapter = new RecyclerviewTableAdapter(listTables, this, this);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(15);
        mRecyclerView.setAdapter(mRecycleviewOrderAdapter);
    }


    @Override
    public void onItemCick(int position) {
        int emptyChair = listTables.get(position).getEmtyChair();
        final FancyGifDialog.Builder fancyGifDialog = new FancyGifDialog.Builder(this).setGifResource(R.drawable.eat)
                .setMessage("Empty chair: " + emptyChair).setNegativeBtnText("Cancel").setTitle("Wanna do somethin'")
                .OnNegativeClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .OnPositiveClicked(new FancyGifDialogListener() {
                    @Override
                    public void OnClick() {

                    }
                })
                .isCancellable(true);
        fancyGifDialog.setPositiveBtnText(emptyChair == 0 ? "Check out" : "Check in");
        fancyGifDialog.build();
    }
}
