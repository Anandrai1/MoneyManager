package com.fintrackindia.moneymanager;

public class MassageItemList {
    /*sms.add("Massage _id=" + _id + "\nMassage Address=" + address + "\nMassage Date=" + date /*+ "\nMassage Body=" + body + "\nA/C no=" + A_C_no
    + "\nAmount Value: " + amount_value + "\nMerchant Name: " + Merchant_Name + "\nCard Name: " + Card_Name + "\nBank Name: " + Bank_Name);*/

    private String _id;
    private String address;
    private String date;
    private String body;
    private String A_C_no;
    private String amount_value;
    private String Merchant_Name;
    private String Card_Name;
    private String Bank_Name;

    public MassageItemList() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getA_C_no() {
        return A_C_no;
    }

    public void setA_C_no(String a_C_no) {
        A_C_no = a_C_no;
    }

    public String getAmount_value() {
        return amount_value;
    }

    public void setAmount_value(String amount_value) {
        this.amount_value = amount_value;
    }

    public String getMerchant_Name() {
        return Merchant_Name;
    }

    public void setMerchant_Name(String merchant_Name) {
        Merchant_Name = merchant_Name;
    }

    public String getCard_Name() {
        return Card_Name;
    }

    public void setCard_Name(String card_Name) {
        Card_Name = card_Name;
    }

    public String getBank_Name() {
        return Bank_Name;
    }

    public void setBank_Name(String bank_Name) {
        Bank_Name = bank_Name;
    }
}
