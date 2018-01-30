package com.codedao.menuonline.Model;

/**
 * Created by Administrator on 30/01/2018.
 */

public class Table {
    int index,emtyChair,maxChair;

    public Table() {
    }

    public Table(int index, int emtyChair, int maxChair) {
        this.index = index;
        this.emtyChair = emtyChair;
        this.maxChair = maxChair;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getEmtyChair() {
        return emtyChair;
    }

    public void setEmtyChair(int emtyChair) {
        this.emtyChair = emtyChair;
    }

    public int getMaxChair() {
        return maxChair;
    }

    public void setMaxChair(int maxChair) {
        this.maxChair = maxChair;
    }
}
