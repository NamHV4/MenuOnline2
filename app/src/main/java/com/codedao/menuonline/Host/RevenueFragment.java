package com.codedao.menuonline.Host;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codedao.menuonline.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RevenueFragment extends Fragment {


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

}
