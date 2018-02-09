package com.codedao.menuonline.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 06/02/2018.
 */

public class DailyData implements Parcelable {
    String day;
    float customer;
    float revenue;

    protected DailyData(Parcel in) {
        day = in.readString();
        customer = in.readFloat();
        revenue = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeFloat(customer);
        dest.writeFloat(revenue);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DailyData> CREATOR = new Creator<DailyData>() {
        @Override
        public DailyData createFromParcel(Parcel in) {
            return new DailyData(in);
        }

        @Override
        public DailyData[] newArray(int size) {
            return new DailyData[size];
        }
    };

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
        this.revenue = revenue;
    }

    public String getDay() {
        StringBuffer s=new StringBuffer(day);
        s.insert(2,"/").insert(5,"/");

        return s.toString();
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getCustomer() {
        return customer;
    }

    public void setCustomer(float customer) {
        this.customer = customer;
    }

    public DailyData(String day, float customer,float revenue) {
        this.day = day;
        this.customer = customer;
        this.revenue=revenue;
    }

    public DailyData() {
    }
}
