package com.codedao.menuonline.Model;

/**
 * Created by utnam on 2/5/2018.
 */

public class Order {
    private int mStt;
    private String mTable;
    private String mDescription;

    public Order(int mStt, String mTable, String mDescription) {
        this.mStt = mStt;
        this.mTable = mTable;
        this.mDescription = mDescription;
    }

    public int getmStt() {
        return mStt;
    }

    public void setmStt(int mStt) {
        this.mStt = mStt;
    }

    public String getmTable() {
        return mTable;
    }

    public void setmTable(String mTable) {
        this.mTable = mTable;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
