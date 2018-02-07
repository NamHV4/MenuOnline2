package com.codedao.menuonline.Host;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;

import com.codedao.menuonline.Adapter.RecyclerviewBlockAdapter;
import com.codedao.menuonline.Adapter.ViewpagerCustomAdapter;
import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.Model.DailyData;
import com.codedao.menuonline.Model.Meal;
import com.codedao.menuonline.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
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

import java.util.ArrayList;
import java.util.Random;

import static com.codedao.menuonline.DbCommon.COST;
import static com.codedao.menuonline.DbCommon.CUSTOMER;
import static com.codedao.menuonline.DbCommon.DAILY_DATA;
import static com.codedao.menuonline.DbCommon.MEAL;
import static com.codedao.menuonline.DbCommon.REVENUE;


public class MainScreenHost extends AppCompatActivity implements RecyclerviewBlockItemClick {

    private final String TAG ="NamHV4";
    private ArrayList<Block> mListBlocks = new ArrayList<>();
    private RecyclerView mRcvBlock;
    private RecyclerviewBlockAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private BarChart mBarChart;
    private PieChart mPieChart;
    private LineChart mLineChart;
    private FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    private ArrayList<BarEntry> mListBars;
    private ArrayList<PieEntry> mListEntries;
    private ArrayList<DailyData> mListDailyDatas;
    private ArrayList<Meal> mListMeals;
    private NavigationTabStrip mTabStrip;
    private ViewPager mViewpager;
    private IDataTransferInterface iDataTransferInterface;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTransformEffect();
//        getSupportActionBar().hide();
        getSupportActionBar().setElevation(0f);
        Log.d(TAG, "onCreate: ");
        initView();
        initTabStrip();
        initViewpager();
        mTabStrip.setViewPager(mViewpager);
        retriveDataFromCloud();

        //NamHV4
        RevenueFragment fragment = new RevenueFragment();
        iDataTransferInterface = fragment;

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.viewpager, fragment, "fragmentTag")
                .commit();

//        initBarChart();
//        initPieChart();
        initRcv();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d(TAG, "onAttachFragment: fragment"+fragment);
        super.onAttachFragment(fragment);
//        if (fragment instanceof IDataTransferInterface){
//            iDataTransferInterface = (IDataTransferInterface) fragment;
//        }

    }

    private void initViewpager() {
        mViewpager.setAdapter(new ViewpagerCustomAdapter(getApplicationContext(), getSupportFragmentManager()));
        mViewpager.setCurrentItem(0);
    }

    private void initTabStrip() {
        mTabStrip.setTitles("Revenue", "Customer", "Meal");
        mTabStrip.setInactiveColor(Color.WHITE);

    }

    private void retriveDataFromCloud() {
        Log.d(TAG, "retriveDataFromCloud: ");
        mListDailyDatas = new ArrayList<>();
        mDatabase.collection(DAILY_DATA).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot document : task.getResult()) {
                        mListDailyDatas.add(new DailyData(document.getId(), Float.parseFloat(document.get(CUSTOMER).toString()), Float.parseFloat(document.get(REVENUE).toString())));
                        initBarChart();
                        initLineChart();
                    }
                    if (iDataTransferInterface != null) {
                        Log.d(TAG, "onComplete() called with: onTransfer");
                        iDataTransferInterface.onTransfer(mListDailyDatas);
                    }
            } else {
                    Log.e(DAILY_DATA, getString(R.string.get_daily_error), task.getException());
                }
            }
        });

        mListMeals = new ArrayList<>();
        mDatabase.collection(MEAL).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        mListMeals.add(new Meal(documentSnapshot.getId(), Float.parseFloat(documentSnapshot.get(COST).toString())));
                        initPieChart();
                    }
                } else {
                    Log.e(DAILY_DATA, getString(R.string.get_meal_error), task.getException());
                }
            }
        });

    }


    private void initLineChart() {
        ArrayList<Entry> listEntries = new ArrayList<>();
        for (int i = 0; i < mListDailyDatas.size(); i++) {
            listEntries.add(new Entry((float) i, mListDailyDatas.get(i).getRevenue()));
        }
        LineDataSet lineDataSet = new LineDataSet(listEntries, "Daily revenue");
        LineData lineData = new LineData(lineDataSet);

        XAxis xAxis = mLineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(5f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mListDailyDatas.get((int) value).getDay();
            }
        });
        mLineChart.setData(lineData);
        mLineChart.animateY(5000);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.getDescription().setEnabled(false);
        mLineChart.invalidate();
    }

    private void initBarChart() {

        mListBars = new ArrayList<>();

        for (int i = 0; i < mListDailyDatas.size(); i++) {
            mListBars.add(new BarEntry((float) i, mListDailyDatas.get(i).getCustomer()));
        }
        BarDataSet barDataSet = new BarDataSet(mListBars, getString(R.string.customer_per_day));
        BarData barData = new BarData(barDataSet);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        mBarChart.setData(barData);
        mBarChart.animateY(5000);
        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(5f);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return mListDailyDatas.get((int) value).getDay();
            }
        });
        mBarChart.getDescription().setEnabled(false);
        xAxis.setGranularity(1f);
        mBarChart.invalidate();


    }

    private void initPieChart() {
        mListEntries = new ArrayList<>();
        for (int i = 0; i < mListMeals.size(); i++) {
            mListEntries.add(new PieEntry(mListMeals.get(i).getCost(), mListMeals.get(i).getName()));

        }
        PieDataSet pieDataSet = new PieDataSet(mListEntries, getString(R.string.meal_cost));
        pieDataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setTransparentCircleRadius(40f);
        mPieChart.setHoleRadius(35f);
//        mPieChart.setDrawSlicesUnderHole(false);
        pieDataSet.setSliceSpace(3f);
        PieData pieData = new PieData(pieDataSet);
//        mPieChart.setUsePercentValues(true);
        mPieChart.setData(pieData);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setRotationEnabled(true);
//        pieData.setValueFormatter(new IValueFormatter() {
//            @Override
//            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
//                return null;
//            }
//        });
        mPieChart.animateY(5000);
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


    private void initView() {
        mRcvBlock = findViewById(R.id.rcvBlock);
        mBarChart = findViewById(R.id.chart);
        mPieChart = findViewById(R.id.pie);
        mLineChart = findViewById(R.id.line);
        mTabStrip = findViewById(R.id.tabStrip);
        mViewpager = findViewById(R.id.viewpager);
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

public interface IDataTransferInterface{
        void onTransfer(ArrayList<DailyData> listDailyData);
}
}
