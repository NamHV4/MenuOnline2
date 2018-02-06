package com.codedao.menuonline.Model;

/**
 * Created by Administrator on 06/02/2018.
 */

public class Meal {
    String name;
    float cost;

    public Meal() {
    }

    public Meal(String name, float cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }
}
