package com.codedao.menuonline.Host;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.widget.Toast;

import com.codedao.menuonline.Adapter.RecyclerviewBlockAdapter;
import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.R;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.Random;

import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.ColumnChartView;

public class MainScreenHost extends AppCompatActivity implements RecyclerviewBlockItemClick {
    FancyGifDialog.Builder fancyGifDialog;
    Chart chart;
    ArrayList<Block> listBlock = new ArrayList<>();
    RecyclerView rcvBlock;
    RecyclerviewBlockAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTransformEffect();
        getSupportActionBar().hide();
        initView();
//        dummyDataLinechart();
        for (int i = 0; i < 15; i++) {
            Random r = new Random();
            listBlock.add(new Block(r.nextInt(1000), "content " + i));
        }
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rcvBlock.setLayoutManager(layoutManager);
        adapter = new RecyclerviewBlockAdapter(listBlock, this, this);
        rcvBlock.setAdapter(adapter);
    }

    private void dummyDataLinechart() {
        ArrayList<PointValue> listValues = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            Random r = new Random();
            listValues.add(new PointValue(r.nextInt(10), r.nextInt(10)));
        }
        chart = new ColumnChartView(this);

    }

    private void initView() {
        rcvBlock = findViewById(R.id.rcvBlock);
//        chart=findViewById(R.id.chart);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void getTransformEffect() {
        Explode explode = new Explode();
        explode.setDuration(500);
        getWindow().setExitTransition(explode);
        getWindow().setEnterTransition(explode);
    }

    @Override
    public void onItemCick(int position) {
        final FancyGifDialog.Builder fancyGifDialog = new FancyGifDialog.Builder(this).setGifResource(R.drawable.mountain)
                .setMessage(listBlock.get(position).getContent()).setNegativeBtnText("Cancel")
                .setTitle(listBlock.get(position).getCounter() + "")
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

        fancyGifDialog.build();
    }
}
