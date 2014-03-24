package ru.cybern.kinoserver.mobileapi.actors.helpers;


import java.io.Serializable;

public class Page implements Serializable {
    private final int pageNum;

    public Page(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    @Override
    public String toString() {
        return Integer.toString(pageNum);
    }

}
