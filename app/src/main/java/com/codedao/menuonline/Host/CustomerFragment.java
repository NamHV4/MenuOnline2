package com.codedao.menuonline.Host;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedao.menuonline.Model.DailyData;
import com.codedao.menuonline.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomerFragment extends Fragment {
    BarChart mBarChart;

    public CustomerFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Context context) {
        CustomerFragment customerFragment = new CustomerFragment();
        return customerFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_customer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        mBarChart=view.findViewById(R.id.barchart);
        initBarChart(MainScreenHost.mListDailyDatas);
    }
    private void initBarChart(final ArrayList<DailyData> mListDailyDatas) {

       ArrayList<BarEntry> mListBars = new ArrayList<>();

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

}
