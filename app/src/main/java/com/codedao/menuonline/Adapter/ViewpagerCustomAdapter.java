package com.codedao.menuonline.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codedao.menuonline.Host.CustomerFragment;
import com.codedao.menuonline.Host.RevenueFragment;

/**
 * Created by Administrator on 07/02/2018.
 */

public class ViewpagerCustomAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public ViewpagerCustomAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment f = new Fragment();
        switch (position) {
            case 0:
                f = RevenueFragment.newInstance(mContext);
                break;
            case 1:
                f = CustomerFragment.newInstance(mContext);
                break;

        }
        return f;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
