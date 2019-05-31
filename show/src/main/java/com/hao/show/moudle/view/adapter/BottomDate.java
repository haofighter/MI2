package com.hao.show.moudle.view.adapter;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BottomDate {

    Object checkIcon;
    Object defIcon;
    int uncheckColor;
    int checkColor;
    String title;
    int tipNum;

    List<View> views = new ArrayList<>();

    public Object getCheckIcon() {
        return checkIcon;
    }

    public List<View> getViews() {
        return views;
    }

    public void setViews(List<View> views) {
        this.views = views;
    }

    public BottomDate setCheckIcon(Object checkIcon) {

        this.checkIcon = checkIcon;
        return this;
    }

    public Object getDefIcon() {
        return defIcon;
    }

    public BottomDate setDefIcon(Object defIcon) {
        this.defIcon = defIcon;
        return this;
    }

    public int getUncheckColor() {
        return uncheckColor;
    }

    public BottomDate setUncheckColor(int uncheckColor) {
        this.uncheckColor = uncheckColor;
        return this;
    }

    public int getCheckColor() {
        return checkColor;
    }

    public BottomDate setCheckColor(int checkColor) {
        this.checkColor = checkColor;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BottomDate setTitle(String title) {
        this.title = title;
        return this;
    }

    public int getTipNum() {
        return tipNum;
    }

    public BottomDate setTipNum(int tipNum) {
        this.tipNum = tipNum;
        return this;
    }
}