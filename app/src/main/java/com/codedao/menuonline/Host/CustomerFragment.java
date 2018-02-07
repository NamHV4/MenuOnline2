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
public class CustomerFragment extends Fragment {


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

}
