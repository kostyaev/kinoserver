package ru.cybern.kinoserver.mobileapi.actors.helpers;


public class Page {
    private int pageNum;

    public Page(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
