package com.hao.show.moudle.view.adapter;

public class BottomDate {

    Object checkbitmap;
    Object uncheckbitmap;
    int uncheckColor;
    int checkColor;
    String title;
    int tipNum;
    boolean isCheck;

    public BottomDate(Object checkbitmap, int checkColor, String title, int tipNum, boolean isCheck) {
        this.checkbitmap = checkbitmap;
        this.checkColor = checkColor;
        this.title = title;
        this.tipNum = tipNum;
        this.isCheck = isCheck;
    }

    public BottomDate(Object checkbitmap, Object uncheckbitmap, int uncheckColor, int checkColor, String title, int tipNum,boolean isCheck) {
        this.checkbitmap = checkbitmap;
        this.uncheckbitmap = uncheckbitmap;
        this.uncheckColor = uncheckColor;
        this.checkColor = checkColor;
        this.title = title;
        this.tipNum = tipNum;
        this.isCheck = isCheck;
    }
}