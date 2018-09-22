package com.fintrackindia.moneymanager.investments;

import android.graphics.drawable.Drawable;

public class Equity_ListItem {
    private Drawable dImage;
    private String dCompany_ID;
    private String dName;
    private String dMinAmount;
    private String dRate1;
    private String dRate3;
    private String dRate5;
    private String dSrC;

    public Equity_ListItem(Drawable dImage, String dName, String dMinAmount) {
        this.dImage = dImage;
        this.dName = dName;
        this.dMinAmount = dMinAmount;
    }

    public Equity_ListItem() {
    }

    public String getdCompany_ID() {
        return dCompany_ID;
    }

    public void setdCompany_ID(String dCompany_ID) {
        this.dCompany_ID = dCompany_ID;
    }

    public Drawable getdImage() {
        return dImage;
    }

    public void setdImage(Drawable dImage) {
        this.dImage = dImage;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdMinAmount() {
        return dMinAmount;
    }

    public void setdMinAmount(String dMinAmount) {
        this.dMinAmount = dMinAmount;
    }

    public String getdRate1() {
        return dRate1;
    }

    public void setdRate1(String dRate1) {
        this.dRate1 = dRate1;
    }

    public String getdRate3() {
        return dRate3;
    }

    public void setdRate3(String dRate3) {
        this.dRate3 = dRate3;
    }

    public String getdRate5() {
        return dRate5;
    }

    public void setdRate5(String dRate5) {
        this.dRate5 = dRate5;
    }

    public String getdSrC() {
        return dSrC;
    }

    public void setdSrC(String dSrC) {
        this.dSrC = dSrC;
    }
}
