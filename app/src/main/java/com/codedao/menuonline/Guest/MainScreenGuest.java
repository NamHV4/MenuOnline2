package com.codedao.menuonline.Guest;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.codedao.menuonline.Adapter.RecyclerviewTableAdapter;
import com.codedao.menuonline.Interface.RecyclerviewTableItemClick;
import com.codedao.menuonline.Model.Table;
import com.codedao.menuonline.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.Random;

public class MainScreenGuest extends AppCompatActivity implements RecyclerviewTableItemClick {
    RecyclerView rcvTable;
    ArrayList<Table> listTables;
    RecyclerviewTableAdapter adapter;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rcvTable.setLayoutManager(new GridLayoutManager(this, 6));
            adapter.notifyDataSetChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rcvTable.setLayoutManager(new GridLayoutManager(this, 3));
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_guest2);
        getSupportActionBar().hide();
        initView();
        dummyData();

    }

    private void dummyData() {
        listTables = new ArrayList<>();
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            rcvTable.setLayoutManager(new GridLayoutManager(this, 3));
        }else{
            rcvTable.setLayoutManager(new GridLayoutManager(this, 6));
        }

        for (int i = 0; i < 15; i++) {
            int a = randomMaxChair();
            listTables.add(new Table(i + 1, randomEmptyChair(a), a));
        }
        adapter = new RecyclerviewTableAdapter(listTables, this, this);
        rcvTable.setDrawingCacheEnabled(true);
        rcvTable.setHasFixedSize(true);
        rcvTable.setItemViewCacheSize(15);
        rcvTable.setAdapter(adapter);
    }

    private void initView() {
        rcvTable = findViewById(R.id.rcvTable);
    }

    private int randomMaxChair() {
        Random r = new Random();
        return r.nextInt(5) + 2;

    }

    private int randomEmptyChair(int x) {
        Random r = new Random();
        return r.nextInt(x);

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
