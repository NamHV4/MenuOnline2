package com.codedao.menuonline.Host;

import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;

import com.codedao.menuonline.Adapter.RecyclerviewBlockAdapter;
import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.Model.DailyData;
import com.codedao.menuonline.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class MainScreenHost extends AppCompatActivity implements RecyclerviewBlockItemClick {

    private ArrayList<Block> mListBlocks = new ArrayList<>();
    private RecyclerView mRcvBlock;
    private RecyclerviewBlockAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private BarChart mBarChart;
    private PieChart mPieChart;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static final String DAILY_DATA = "dailyData";
    private static final String CUSTOMER = "customer";
    private ArrayList<BarEntry> mListBars;

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

        db.collection(DAILY_DATA).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                mListBars = new ArrayList<>();
                final ArrayList<DailyData> listDailyDatas=new ArrayList<>();
                if (task.isSuccessful()) {

                    for (DocumentSnapshot document : task.getResult()) {

                        listDailyDatas.add(new DailyData(document.getId(),Float.parseFloat(document.get(CUSTOMER).toString())));

                    }
                    for (int i = 0; i <listDailyDatas.size() ; i++) {
                        mListBars.add(new BarEntry((float)i,listDailyDatas.get(i).getCustomer()));
                    }
                    BarDataSet barDataSet = new BarDataSet(mListBars, "Customer per day");
                    BarData barData = new BarData(barDataSet);
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

                    mBarChart.setData(barData);
                    mBarChart.animateY(5000);
                    XAxis xAxis=mBarChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setTextSize(5f);
                    xAxis.setValueFormatter(new IAxisValueFormatter() {
                        @Override
                        public String getFormattedValue(float value, AxisBase axis) {
                            return listDailyDatas.get((int)value).getDay();
                        }
                    });
                    mBarChart.getDescription().setEnabled(false);
                    xAxis.setGranularity(1f);
                    mBarChart.invalidate();
                    Log.e("ádasd",listDailyDatas.size()+"");

                }
                else{
                    Log.e(DAILY_DATA, "Error getting documents.", task.getException());
                }

            }
        });

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
