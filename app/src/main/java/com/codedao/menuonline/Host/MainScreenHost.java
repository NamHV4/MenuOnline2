package com.codedao.menuonline.Host;

import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;

import com.codedao.menuonline.Adapter.RecyclerviewBlockAdapter;
import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.Random;


public class MainScreenHost extends AppCompatActivity implements RecyclerviewBlockItemClick {

    ArrayList<Block> mListBlocks = new ArrayList<>();
    RecyclerView mRcvBlock;
    RecyclerviewBlockAdapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    BarChart mBarChart;
    PieChart mPieChart;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTransformEffect();
        getSupportActionBar().hide();
        initView();
        initBarChart();
        initPieChart();
        initRcv();

    }

    private void initPieChart() {
        mPieChart.setRotationEnabled(true);
        ArrayList<PieEntry> listEntries = new ArrayList<>();
        listEntries.add(new PieEntry(0f, 15f));
        listEntries.add(new PieEntry(1f, 35f));
        listEntries.add(new PieEntry(2f, 22f));
        listEntries.add(new PieEntry(3f, 10f));
        listEntries.add(new PieEntry(4f, 20f));
        listEntries.add(new PieEntry(5f, 30f));

        PieDataSet pieDataSet = new PieDataSet(listEntries, "");
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setTransparentCircleRadius(40f);
        mPieChart.setHoleRadius(35f);
//        mPieChart.setDrawSlicesUnderHole(false);
        pieDataSet.setSliceSpace(3f);
        PieData pieData = new PieData(pieDataSet);
        mPieChart.setUsePercentValues(true);
        mPieChart.setData(pieData);
        mPieChart.invalidate();


    }

    private void initRcv() {
        for (int i = 0; i < 15; i++) {
            Random r = new Random();
            mListBlocks.add(new Block(r.nextInt(1000), "content " + i));
        }
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        } else {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        }
        mRcvBlock.setLayoutManager(mLayoutManager);
        mAdapter = new RecyclerviewBlockAdapter(mListBlocks, this, this);
        mRcvBlock.setAdapter(mAdapter);
    }

    private void initBarChart() {

        ArrayList<BarEntry> listBar = new ArrayList<>();
        listBar.add(new BarEntry(0f, 10f, "Jan"));
        listBar.add(new BarEntry(1f, 15f, "Feb"));
        listBar.add(new BarEntry(2f, 11f, "Mar"));
        listBar.add(new BarEntry(3f, 9f, "Apr"));
        listBar.add(new BarEntry(4f, 13f, "May"));
        listBar.add(new BarEntry(5f, 5f, "Jun"));
        listBar.add(new BarEntry(6f, 7f, "Jul"));
        listBar.add(new BarEntry(7f, 11f, "Aug"));
        listBar.add(new BarEntry(8f, 11f, "Sep"));
        listBar.add(new BarEntry(9f, 6f, "Oct"));

        BarDataSet barDataSet = new BarDataSet(listBar, "Example");

        BarData barData = new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        mBarChart.setData(barData);
        mBarChart.animateY(5000);
        mBarChart.invalidate();
    }

    private void initView() {
        mRcvBlock = findViewById(R.id.rcvBlock);
        mBarChart = findViewById(R.id.chart);
        mPieChart = findViewById(R.id.pie);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mAdapter.notifyDataSetChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mAdapter.notifyDataSetChanged();
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
                .setMessage(mListBlocks.get(position).getContent()).setNegativeBtnText("Cancel")
                .setTitle(mListBlocks.get(position).getCounter() + "")
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
