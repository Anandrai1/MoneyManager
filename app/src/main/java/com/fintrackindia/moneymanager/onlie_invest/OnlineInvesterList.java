package com.fintrackindia.moneymanager.onlie_invest;

import android.graphics.drawable.Drawable;

public class OnlineInvesterList {
    private Drawable dImage;
    private String name;
    private String image;
    private String webURL;
    private String emailAdd;
    private String careNumber;

    public OnlineInvesterList(Drawable dImage, String name, String webURL, String emailAdd, String careNumber) {
        this.dImage = dImage;
        this.name = name;
        this.webURL = webURL;
        this.emailAdd = emailAdd;
        this.careNumber = careNumber;
    }

    public Drawable getdImage() {
        return dImage;
    }

    public void setdImage(Drawable dImage) {
        this.dImage = dImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    public String getEmailAdd() {
        return emailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        this.emailAdd = emailAdd;
    }

    public String getCareNumber() {
        return careNumber;
    }

    public void setCareNumber(String careNumber) {
        this.careNumber = careNumber;
    }
}
