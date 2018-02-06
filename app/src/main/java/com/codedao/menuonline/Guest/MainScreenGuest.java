package com.codedao.menuonline.Guest;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import com.codedao.menuonline.Adapter.RecyclerviewTableAdapter;
import com.codedao.menuonline.Interface.RecyclerviewTableItemClick;
import com.codedao.menuonline.Model.Table;
import com.codedao.menuonline.R;
import com.joaquimley.faboptions.FabOptions;
import com.shashank.sony.fancygifdialoglib.FancyGifDialog;
import com.shashank.sony.fancygifdialoglib.FancyGifDialogListener;

import java.util.ArrayList;
import java.util.Random;

public class MainScreenGuest extends AppCompatActivity implements RecyclerviewTableItemClick {
    private RecyclerView mRcvTables;
    private ArrayList<Table> mListTables;
    private RecyclerviewTableAdapter mAdapter;
    private FabOptions mFabOpt;

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mRcvTables.setLayoutManager(new GridLayoutManager(this, 6));
            mAdapter.notifyDataSetChanged();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRcvTables.setLayoutManager(new GridLayoutManager(this, 3));
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen_guest2);
        getSupportActionBar().hide();
        initView();
        dummyData();
        initFab();
        mFabOpt.animate();
    }


    private void initFab() {

        mRcvTables.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    if (mFabOpt.isShown()) {
                        mFabOpt.animate().translationY(mFabOpt.getHeight() + 16).setInterpolator(new AccelerateInterpolator(2)).start();
                    }
                } else {
//                        mFabOpt.setTranslationY(mFabOpt.getHeight() + 16);
                    mFabOpt.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

                }
            }
        });
        mFabOpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.qr:
                        startActivity(new Intent(MainScreenGuest.this, QRCode.class));
                        break;
                    case R.id.mess:
                        Toast.makeText(MainScreenGuest.this, "mess", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    private void dummyData() {
        mListTables = new ArrayList<>();
        if (getApplication().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRcvTables.setLayoutManager(new GridLayoutManager(this, 3));
        } else {
            mRcvTables.setLayoutManager(new GridLayoutManager(this, 6));
        }

        for (int i = 0; i < 15; i++) {
            int a = randomMaxChair();
            mListTables.add(new Table(i + 1, randomEmptyChair(a), a));
        }
        mAdapter = new RecyclerviewTableAdapter(mListTables, this, this);
        mRcvTables.setDrawingCacheEnabled(true);
        mRcvTables.setHasFixedSize(true);
        mRcvTables.setItemViewCacheSize(15);
        mRcvTables.setAdapter(mAdapter);
    }

    private void initView() {
        mFabOpt = findViewById(R.id.fab_options);
        mRcvTables = findViewById(R.id.rcvTable);
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
        int emptyChair = mListTables.get(position).getEmtyChair();
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
