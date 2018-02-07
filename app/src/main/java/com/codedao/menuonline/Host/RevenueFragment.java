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
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RevenueFragment extends Fragment implements MainScreenHost.IDataTransferInterface {
    LineChart mLineChart;

    public RevenueFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Context context) {
        RevenueFragment revenueFragment = new RevenueFragment();
        return revenueFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_revenue, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mLineChart=view.findViewById(R.id.linechart);


    }

    @Override
    public void onTransfer(ArrayList<DailyData> listDailyData) {
        initLineChart(listDailyData);
    }

    private void initLineChart(final ArrayList<DailyData> listDailyDatas) {

            ArrayList<Entry> listEntries = new ArrayList<>();
            for (int i = 0; i < listDailyDatas.size(); i++) {
                listEntries.add(new Entry((float) i, listDailyDatas.get(i).getRevenue()));
            }
            LineDataSet lineDataSet = new LineDataSet(listEntries, "Daily revenue");
            LineData lineData = new LineData(lineDataSet);

            XAxis xAxis = mLineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTextSize(5f);
            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    return listDailyDatas.get((int) value).getDay();
                }
            });
            mLineChart.setData(lineData);
            mLineChart.animateY(5000);
            mLineChart.getDescription().setEnabled(false);
            mLineChart.getDescription().setEnabled(false);
            mLineChart.invalidate();

    }
}
