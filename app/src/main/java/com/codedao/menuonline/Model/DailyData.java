package com.codedao.menuonline.Model;

/**
 * Created by Administrator on 06/02/2018.
 */

public class DailyData {
    String day;
    float customer;
    float revenue;

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
