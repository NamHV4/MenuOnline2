package com.codedao.menuonline.Host;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedao.menuonline.BaseFragment;
import com.codedao.menuonline.Model.DailyData;
import com.codedao.menuonline.Model.Meal;
import com.codedao.menuonline.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MealFragment extends BaseFragment {

    PieChart mPieChart;

    public MealFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(Context context) {
        return new MealFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPieChart = view.findViewById(R.id.piechart);
        initPieChart(MainScreenHost.mListMeals);
    }

    private void initPieChart(ArrayList<Meal> mListMeals) {
        ArrayList<PieEntry> mListEntries = new ArrayList<>();
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

}
