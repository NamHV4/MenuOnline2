package com.codedao.menuonline.Model;

/**
 * Created by Administrator on 01/02/2018.
 */

public class Block {
    int counter;
    String content;

    public Block(int counter, String content) {
        this.counter = counter;
        this.content = content;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Block() {
    }
}
