package com.example.easywallet.model;

/**
 * Created by TO_MANG on 10/12/2560.
 */

public class PayItem {

    public final int id;
    public final String title;
    public final String number;
    public final String picture;

    public PayItem(int id, String title, String number, String picture) {
        this.id = id;
        this.title = title;
        this.number = number;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return title;
    }
}
