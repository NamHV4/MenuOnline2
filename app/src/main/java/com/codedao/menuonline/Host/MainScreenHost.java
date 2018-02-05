package com.codedao.menuonline.Host;

import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.widget.FrameLayout;

import com.codedao.menuonline.Adapter.RecyclerviewBlockAdapter;
import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.Random;


public class MainScreenHost extends AppCompatActivity implements RecyclerviewBlockItemClick {

    ArrayList<Block> listBlock = new ArrayList<>();
    RecyclerView rcvBlock;
    RecyclerviewBlockAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    BarChart chart;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTransformEffect();
        getSupportActionBar().hide();
        initView();
        initChart();
        for (int i = 0; i < 15; i++) {
            Random r = new Random();
            listBlock.add(new Block(r.nextInt(1000), "content " + i));
        }
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        } else {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        }
        rcvBlock.setLayoutManager(layoutManager);
        adapter = new RecyclerviewBlockAdapter(listBlock, this, this);
        rcvBlock.setAdapter(adapter);
    }

    private void initChart() {

        ArrayList<BarEntry> listBar = new ArrayList<>();
        listBar.add(new BarEntry(0f, 10f));
        listBar.add(new BarEntry(1f, 15f));
        listBar.add(new BarEntry(2f, 11f));
        listBar.add(new BarEntry(3f, 9f));
        listBar.add(new BarEntry(4f, 13f));
        listBar.add(new BarEntry(5f, 5f));
        listBar.add(new BarEntry(6f, 7f));
        listBar.add(new BarEntry(7f, 11f));
        listBar.add(new BarEntry(8f, 11f));
        listBar.add(new BarEntry(9f, 6f));

        BarDataSet barDataSet = new BarDataSet(listBar, "Example");

        BarData barData = new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        chart.setData(barData);
        chart.animateY(5000);
        chart.invalidate();
    }

    private void initView() {
        rcvBlock = findViewById(R.id.rcvBlock);
        chart = findViewById(R.id.chart);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            adapter.notifyDataSetChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            adapter.notifyDataSetChanged();
        }
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
