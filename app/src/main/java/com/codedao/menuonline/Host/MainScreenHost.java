package com.codedao.menuonline.Host;

import android.app.Fragment;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.codedao.menuonline.Adapter.RecyclerviewBlockAdapter;
import com.codedao.menuonline.Adapter.ViewpagerCustomAdapter;
import com.codedao.menuonline.BaseActivity;
import com.codedao.menuonline.Interface.RecyclerviewBlockItemClick;
import com.codedao.menuonline.Model.Block;
import com.codedao.menuonline.Model.DailyData;
import com.codedao.menuonline.Model.Meal;
import com.codedao.menuonline.R;
import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;

import static com.codedao.menuonline.DbCommon.COST;
import static com.codedao.menuonline.DbCommon.CUSTOMER;
import static com.codedao.menuonline.DbCommon.DAILY_DATA;
import static com.codedao.menuonline.DbCommon.MEAL;
import static com.codedao.menuonline.DbCommon.REVENUE;


public class MainScreenHost extends BaseActivity implements RecyclerviewBlockItemClick {

    private final String TAG = "NamHV4";
    private ArrayList<Block> mListBlocks = new ArrayList<>();
    private RecyclerView mRcvBlock;
    private RecyclerviewBlockAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FirebaseFirestore mDatabase = FirebaseFirestore.getInstance();
    static ArrayList<DailyData> mListDailyDatas;
    static ArrayList<Meal> mListMeals;
    private NavigationTabStrip mTabStrip;
    private ViewPager mViewpager;
    private ProgressBar mProgressBar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getTransformEffect();
        getSupportActionBar().setElevation(0f);
        Log.d(TAG, "onCreate: ");
        initView();
        mProgressBar.setVisibility(View.VISIBLE);
        retriveDataFromCloud();
//        initRcv();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.d(TAG, "onAttachFragment: fragment" + fragment);
        super.onAttachFragment(fragment);
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
                    if(mProgressBar.getVisibility()==View.VISIBLE){
                        mProgressBar.setVisibility(View.GONE);
                    }
                    for (DocumentSnapshot document : task.getResult()) {
                        mListDailyDatas.add(new DailyData(document.getId(), Float.parseFloat(document.get(CUSTOMER).toString()), Float.parseFloat(document.get(REVENUE).toString())));
                    }
                    initTabStrip();
                    initViewpager();
                    mTabStrip.setViewPager(mViewpager);
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

                    }
                } else {
                    Log.e(DAILY_DATA, getString(R.string.get_meal_error), task.getException());
                }
            }
        });

    }

//    private void initRcv() {
//        for (int i = 0; i < 15; i++) {
//            Random r = new Random();
//            mListBlocks.add(new Block(r.nextInt(1000), "content " + i));
//        }
//        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        } else {
//            mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//
//        }
//        mRcvBlock.setLayoutManager(mLayoutManager);
//        mAdapter = new RecyclerviewBlockAdapter(mListBlocks, this, this);
//        mRcvBlock.setAdapter(mAdapter);
//    }


    private void initView() {
        mRcvBlock = findViewById(R.id.rcvBlock);
        mTabStrip = findViewById(R.id.tabStrip);
        mViewpager = findViewById(R.id.viewpager);
        mProgressBar=findViewById(R.id.progressbar);
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
