package com.arunabraham.solsticecontactsapp;

/**
 * Created by Arun on 12/23/2017.
 */

public class Tuple<X, Y> {
    private X x;
    private Y y;
    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public void setX(X x) {
        this.x = x;
    }

    public void setY(Y y) {
        this.y = y;
    }
}
