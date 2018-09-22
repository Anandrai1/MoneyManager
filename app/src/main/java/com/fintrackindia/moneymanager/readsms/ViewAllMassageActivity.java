package com.fintrackindia.moneymanager.readsms;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.fintrackindia.moneymanager.MassageItemList;
import com.fintrackindia.moneymanager.R;
import com.fintrackindia.moneymanager.custom.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewAllMassageActivity extends AppCompatActivity {
    ReadMassageAdapter readMassageAdapter;
    String amount_value, A_C_no, Merchant_Name, Card_Name, Bank_Name;
    private RecyclerView rv_tran;
    private List<MassageItemList> massageItemLists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_massage);

        rv_tran = (RecyclerView) findViewById(R.id.rv_tran);
        /* List<String> apps = getSMS(); *//* false = no system packages *//*

        // Log.e("Transaction All Massage", apps.toString());
        *//*System.out.print("Get All Massage" + apps.toString());*//*
        final int max = apps.size();
        for (int i = 0; i < max; i++) {
            //Log.e("Get All Massage", apps.get(i));
            //System.out.print("Transaction Massage" + apps.get(i) + "\n");
            arrayList_mass.add(apps.get(i));
        }*/

        //rv_tran.setLayoutManager(new GridLayoutManager(this, 2));
        //rv_tran.setLayoutManager(new LinearLayoutManager(this));

        //rv_tran.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rv_tran.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        readMassageAdapter = new ReadMassageAdapter(ViewAllMassageActivity.this, massageItemLists, 1);
        rv_tran.setAdapter(readMassageAdapter);
        rv_tran.setHasFixedSize(true);
        rv_tran.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rv_tran, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
               /* currentCategory = categoriesList[position];
                selectedCategory = currentCategory.getName();
                //Toast.makeText(getApplicationContext(), currentCategory.getName(), Toast.LENGTH_SHORT).show();
                tv_category.setText(currentCategory.getName());
                rl_touch.setVisibility(View.GONE);*/
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        readMassageAdapter.notifyDataSetChanged();

        getAllSMS();
    }

    public void getAllSMS() {

        //Cursor cur = getContentResolver().query(uriSMSURI, projection, "datetime(date/1000, 'unixepoch') between date('now', '-1 day') and date('now')", null, null)
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        //Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
        Cursor cur = getContentResolver().query(uriSMSURI, new String[]{"_id", "address", "date", "body"}, null, null, null);

        if (!massageItemLists.isEmpty()) {
            massageItemLists.clear();
        }
        while (cur != null && cur.moveToNext()) {
            String _id = cur.getString(cur.getColumnIndex("_id"));
            String address = cur.getString(cur.getColumnIndex("address"));
            String date = cur.getString(cur.getColumnIndex("date"));
            String body = cur.getString(cur.getColumnIndexOrThrow("body"));

            //  Log.e("Get All Massage", "Number: " + address + " .Message: " + body);

           /* if (body.contains("debited") ||
                    body.contains("purchasing") || body.contains("purchase") || body.contains("dr")) {
                smsDto.setTransactionType("0");
            } else if (body.contains("credited") || body.contains("cr")) {
                smsDto.setTransactionType("1");
            }*/

            if (body.contains("been debited for") || body.contains("been Debited for") || body.contains("is debited") /* || body.contains("purchasing")*/ /*|| body.contains("for a purchase")*/ || body.contains("Debited Card Purchase") || body.contains("Third party transfer to")
                    /*|| body.contains("DR")*/ || body.contains("is credited by") || body.contains("is credited for") || body.contains("was withdrawn") || body.contains("txn of")/*|| body.contains("CR")*/
                    || body.contains("Clear Bal") || body.contains("Avbl Bal") || body.contains("Avail Bal") || body.contains("Avl Bal") || body.contains("Available Balance") || body.contains("available balance") || body.contains("successful payment")) {

                //sms.add("Number: " + address + " .Message: " + body);

                /**
                 * For finding out amount from bank transaction message.

                 (?i)(?:(?:RS|INR|MRP)\.?\s?)(\d+(:?\,\d+)?(\,\d+)?(\.\d{1,2})?)
                 * */
                Pattern regEx = Pattern.compile("(?i)(?:(?:RS|INR|MRP|Cr)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
                // Find instance of pattern matches
                Matcher m = regEx.matcher(body);
                if (m.find()) {
                    try {
                        // Log.e("amount_value= ", "" + m.group(0));
                        //amount_value = m.group(0);
                        String amount = (m.group(0).replaceAll("INR", ""));
                        amount = amount.replaceAll("Cr", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");
                        amount = amount.replaceAll("Rs.", "");
                        amount_value = amount;
                        //  if (!Character.isDigit(amount_value.charAt(0)))
                        // Log.e("amount_value222= ", "" + amount_value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Log.e("amount_value= ", "No Amount find");
                    amount_value = "No Amount find";
                }

                /**
                 * For finding A/C no

                 [0-9]*[Xx\*]*[0-9]*[Xx\*]+[0-9]{3,}
                 * */
                regEx = Pattern.compile("[0-9]*[Xx\\*]*[0-9]*[Xx\\*]+[0-9]{3,}");
                // Find instance of pattern matches
                m = regEx.matcher(body);
                if (m.find()) {
                    try {
                        Log.e("A/C no= ", "" + m.group(0));
                        A_C_no = m.group(0);
                        A_C_no = A_C_no.substring(A_C_no.length() - 3);
                        A_C_no = "**" + A_C_no;
                        A_C_no = A_C_no.replaceAll("x", "*");
                        A_C_no = A_C_no.replaceAll("X", "*");
                        /*String amount = (m.group(0).replaceAll("INR", ""));
                        amount = amount.replaceAll("Rs", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");*/

                        // Log.e("matchedValue= ", "" + m.group(0));
                   /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                        resSms.add(smsDto);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("A/C no= ", "No Account find");
                    A_C_no = "No Account find";
                }

                /**
                 * For finding out merchant name from bank transaction message.

                 (?i)(?:\sat\s|in\*)([A-Za-z0-9]*\s?-?\s?[A-Za-z0-9]*\s?-?\.?)
                 * */
                regEx = Pattern.compile("(?i)(?:\\sat\\s|in\\*)([A-Za-z0-9]*\\s?-?\\s?[A-Za-z0-9]*\\s?-?\\.?)");
                // Find instance of pattern matches
                m = regEx.matcher(body);
                if (m.find()) {
                    try {
                        Log.e("Merchant Name= ", "" + m.group(0));
                        //Merchant_Name = m.group(0);
                        Merchant_Name = (m.group(0).replaceAll("at", ""));
                       /* amount = amount.replaceAll("Rs", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");*/

                        // Log.e("matchedValue= ", "" + m.group(0));
                       /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                            resSms.add(smsDto);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Merchant Name= ", "No Merchant Name find");
                    Merchant_Name = "No Merchant Name find";
                }

                /**
                 * For finding out card name(debit/credit card) from bank transaction message.

                 (?i)(?:\smade on|ur|made a\s|in\*)([A-Za-z]*\s?-?\s[A-Za-z]*\s?-?\s[A-Za-z]*\s?-?)
                 * */
                regEx = Pattern.compile("(?i)(?:\\smade on|ur|made a\\s|in\\*)([A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?\\s[A-Za-z]*\\s?-?)");
                // Find instance of pattern matches
                m = regEx.matcher(body);
                if (m.find()) {
                    try {
                        Log.e("Card Name= ", "" + m.group(0));
                        Card_Name = (m.group(0).replaceAll("ur", ""));
                        /*String amount = (m.group(0).replaceAll("INR", ""));
                        amount = amount.replaceAll("Rs", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");*/

                        // Log.e("matchedValue= ", "" + m.group(0));
                   /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                        resSms.add(smsDto);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Card Name= ", "No Card Name find");
                    Card_Name = "No Card Name find";
                }

                /**
                 * For finding Bank name from bank transaction message.

                 ((?<=[)[\u4e00-\u9fa5]+(? =])
                 * */
                regEx = Pattern.compile("(?<=[)[\u4e00" +
                        "\u9fa5]+(? =] )");
                // Find instance of pattern matches
                m = regEx.matcher(body);
                if (m.find()) {
                    try {
                        Log.e("Bank Name= ", "" + m.group(0));
                        Bank_Name = m.group(0);
                        /*String amount = (m.group(0).replaceAll("INR", ""));
                        amount = amount.replaceAll("Rs", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");*/

                        // Log.e("matchedValue= ", "" + m.group(0));
                   /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                        resSms.add(smsDto);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("Bank Name= ", "No Bank Name find");
                    Bank_Name = "No Bank Name find";
                }
                // sms.add("Massage _id=" + _id + "\nMassage Address=" + address + "\nMassage Date=" + date /*+ "\nMassage Body=" + body*/ + "\nA/C no=" + A_C_no + "\nAmount Value: " + amount_value + "\nMerchant Name: " + Merchant_Name + "\nCard Name: " + Card_Name + "\nBank Name: " + Bank_Name);
                // sms.add("\nAmount Value: " + amount_value);

                MassageItemList massageItemList = new MassageItemList();
                massageItemList.set_id(_id);
                massageItemList.setAddress(address);
                massageItemList.setDate(date);
                massageItemList.setBody(body);
                massageItemList.setA_C_no(A_C_no);
                massageItemList.setAmount_value(amount_value);
                massageItemList.setMerchant_Name(Merchant_Name);
                massageItemList.setCard_Name(Card_Name);
                massageItemList.setBank_Name(Bank_Name);
                massageItemLists.add(massageItemList);
            }
        }

        if (cur != null) {
            cur.close();
        }
        readMassageAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
