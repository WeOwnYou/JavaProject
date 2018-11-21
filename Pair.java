package com.company;

public class Pair<F,S> {

    private F first;
    private S second;

    public Pair(F first, S second){
        super();
        this.first = first;
        this.second = second;
    }

    public S getSecond() {
        return second;
    }

    public F getFirst() {
        return first;
    }
}
