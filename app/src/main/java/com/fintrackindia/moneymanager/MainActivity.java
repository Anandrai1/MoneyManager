package com.fintrackindia.moneymanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fintrackindia.moneymanager.calculator.InvCalculatorActivity;
import com.fintrackindia.moneymanager.custom.RecyclerTouchListener;
import com.fintrackindia.moneymanager.database.RealmManager;
import com.fintrackindia.moneymanager.insurance.InsurancesActivity;
import com.fintrackindia.moneymanager.interfaces.IExpensesType;
import com.fintrackindia.moneymanager.investments.InvestmentsActivity;
import com.fintrackindia.moneymanager.onlie_invest.OnlieInvestActivity;
import com.fintrackindia.moneymanager.planning.PlanningActivity;
import com.fintrackindia.moneymanager.readsms.ReadMassageAdapter;
import com.fintrackindia.moneymanager.readsms.ViewAllMassageActivity;
import com.fintrackindia.moneymanager.spends.AddExpenseActivity;
import com.fintrackindia.moneymanager.spends.SpendsActivity;
import com.fintrackindia.moneymanager.spends.categories.Category;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.READ_SMS;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int PERMISSION_REQUEST_CODE = 200;
    final String FirstRun = "FirstRun";
    int expenses_Mode = IExpensesType.MODE_EXPENSES;
    int income_Mode = IExpensesType.MODE_INCOME;
    CardView card_spends, card_planning, card_investments, card_insurances, card_calculator, card_online_invest;
    ArrayList<String> arrayList_mass = new ArrayList<>();
    ReadMassageAdapter readMassageAdapter;
    String amount_value, A_C_no, Merchant_Name, Card_Name, Bank_Name;
    String accountAddress = "";
    // All Shared Preferences Keys
    SharedPreferences mPrefs;
    Boolean isFirstRun;
    TextView tv_viewall;
    String Clear_Bal;
    private RecyclerView rv_tran;
    private List<MassageItemList> massageItemLists = new ArrayList<>();
    private ArrayList<String> smslist = new ArrayList<>();

    public static int getCurrentDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                startActivity(new Intent(MainActivity.this, AddExpenseActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });

        mPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        // second argument is the default to use if the preference can't be found
        isFirstRun = mPrefs.getBoolean(FirstRun, false);

        if (!isFirstRun) {
            // here you can launch another activity if you like
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putBoolean(FirstRun, true);
            editor.apply(); // Very important to save the preference
            onSetECategory();
            onSetICategory();
        }

        card_spends = (CardView) findViewById(R.id.card_spends);
        card_spends.setOnClickListener(this);
        card_planning = (CardView) findViewById(R.id.card_planning);
        card_planning.setOnClickListener(this);
        card_investments = (CardView) findViewById(R.id.card_investments);
        card_investments.setOnClickListener(this);
        card_insurances = (CardView) findViewById(R.id.card_insurances);
        card_insurances.setOnClickListener(this);
        card_calculator = (CardView) findViewById(R.id.card_calculator);
        card_calculator.setOnClickListener(this);
        card_online_invest = (CardView) findViewById(R.id.card_online_invest);
        card_online_invest.setOnClickListener(this);

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

        rv_tran.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        readMassageAdapter = new ReadMassageAdapter(MainActivity.this, massageItemLists, 0);
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

        if (!checkPermission()) {
            requestPermission();
        } else {
            getAllSMS();
        }

        tv_viewall = (TextView) findViewById(R.id.tv_viewall);
        tv_viewall.setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.card_spends:
                Intent IntentSpendsActivity = new Intent(this, SpendsActivity.class);
                IntentSpendsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentSpendsActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.card_planning:
                Intent IntentPlanningActivity = new Intent(this, PlanningActivity.class);
                IntentPlanningActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentPlanningActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.card_investments:
                Intent IntentInvestmentsActivity = new Intent(this, InvestmentsActivity.class);
                IntentInvestmentsActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentInvestmentsActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.card_insurances:
                Intent IntentInsurancesActivity = new Intent(this, InsurancesActivity.class);
                IntentInsurancesActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentInsurancesActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.card_calculator:
                Intent IntentInvCalculatorActivity = new Intent(this, InvCalculatorActivity.class);
                IntentInvCalculatorActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentInvCalculatorActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.card_online_invest:
                Intent IntentOnlieInvestActivity = new Intent(this, OnlieInvestActivity.class);
                IntentOnlieInvestActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentOnlieInvestActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
            case R.id.tv_viewall:
                Intent IntentViewAllMassageActivity = new Intent(this, ViewAllMassageActivity.class);
                IntentViewAllMassageActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(IntentViewAllMassageActivity);
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
                break;
        }
    }

    public List<String> getSMS() {

        List<String> sms = new ArrayList<String>();
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        //Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
        Cursor cur = getContentResolver().query(uriSMSURI, new String[]{"_id", "address", "date", "body"}, null, null, null);

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

            if (body.contains("been debited for") || body.contains("is debited for") /* || body.contains("purchasing")*/ || body.contains("Debited Card Purchase")
                    /*|| body.contains("DR")*/ || body.contains("is credited by") /*|| body.contains("CR")*/
                    || body.contains("Clear Bal") || body.contains("Avail Bal") || body.contains("Avl Bal") || body.contains("Available Balance") || body.contains("available balance") || body.contains("successful payment")) {

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
                        Log.e("amount_value= ", "" + m.group(0));
                        amount_value = m.group(0);
                        String amount = (m.group(0).replaceAll("INR", ""));
                        amount = amount.replaceAll("Rs", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");

                        //Log.e("matchedValue= ", "" + amount);
                   /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                        resSms.add(smsDto);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e("amount_value= ", "No Amount find");
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
                sms.add("\nAmount Value: " + amount_value);
            }
        }

        if (cur != null) {
            cur.close();
        }
        return sms;
    }
   /* public static int getDateDayOfMonth (Date date) {
        Calendar calendar = Calendar.getInstance ();
        Calendar.setTime (date);
        return calendar.get (Calendar.DAY_OF_MONTH);
    }*/

    public void getAllSMS() {

        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        //Cursor cur = getContentResolver().query(uriSMSURI, null, null, null, null);
        Cursor cur = getContentResolver().query(uriSMSURI, new String[]{"_id", "address", "date", "body"}, null, null, null);

        if (!massageItemLists.isEmpty()) {
            massageItemLists.clear();
        }
        while (cur != null && cur.moveToNext()) {
            String _id = cur.getString(cur.getColumnIndex("_id"));
            String address = cur.getString(cur.getColumnIndex("address"));
            String datee = cur.getString(cur.getColumnIndex("date"));
            String body = cur.getString(cur.getColumnIndexOrThrow("body"));

            //    Long millisSecond = convertToMillisSecond (date);
           /* Long millisSecond =  cur.getLong(cur.getColumnIndex("date"));;
            Long currencyMillisSecond = System.currentTimeMillis ();

            if (currencyMillisSecond> millisSecond) {
                Long diff = currencyMillisSecond - millisSecond;
                Long day = 86400000L;

                if (diff <day && getCurrentDayOfMonth () == getDateDayOfMonth (date)) {
                    Result = convertMillisSecondsToHourString (millisSecond);

                } else if (diff <(day * 2) && getCurrentDayOfMonth () -1 == getDateDayOfMonth (date)) {
                    Result = yesterday;
                } else  {
                    Result = convertMillisSecondsToDateString (millisSecond);
                }
            }*/


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
                Clear_Bal = body;

                Clear_Bal = Clear_Bal.substring(Clear_Bal.indexOf("bal") + 3, Clear_Bal.length());
                Clear_Bal = Clear_Bal.substring(Clear_Bal.indexOf("Bal") + 3, Clear_Bal.length());
                Clear_Bal = Clear_Bal.substring(Clear_Bal.indexOf("Balance") + 1, Clear_Bal.length());
                Clear_Bal = Clear_Bal.substring(Clear_Bal.indexOf("balance") + 1, Clear_Bal.length());

                Pattern regExd = Pattern.compile("(?i)(?:(?:RS|INR|MRP|Cr)\\.?\\s?)(\\d+(:?\\,\\d+)?(\\,\\d+)?(\\.\\d{1,2})?)");
                // Find instance of pattern matches
                Matcher md = regExd.matcher(Clear_Bal);
                if (md.find()) {
                    try {
                        // Log.e("amount_value= ", "" + m.group(0));
                        Clear_Bal = md.group(0);
                        String amount = (md.group(0).replaceAll("INR", ""));
                        amount = amount.replaceAll("Rs.", "");
                        amount = amount.replaceAll("Cr", "");
                        amount = amount.replaceAll("INR", "");
                        amount = amount.replaceAll(" ", "");
                        amount = amount.replaceAll(",", "");
                        amount_value = amount;
                        //Log.e("matchedValue= ", "" + amount);
                   /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                        resSms.add(smsDto);*/
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    // Log.e("amount_value= ", "No Amount find");
                    Clear_Bal = "No  Clear_Bal find";
                }

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
                        String strMain = "Alpha, Beta, Delta, Gamma, Sigma";
                        String[] arrSplit = amount_value.split(".");
                        for (int i = 0; i < arrSplit.length; i++) {
                            Log.e("matchedValue=", "" + arrSplit[i]);
                        }
                        //Log.e("matchedValue= ", "" + amount);
                       /* if (!Character.isDigit(smsDto.getSenderid().charAt(0)))
                            resSms.add(smsDto);*/
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
                        //  Log.e("A/C no= ", "" + m.group(0));
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
                    // Log.e("A/C no= ", "No Account find");
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
                        //Log.e("Merchant Name= ", "" + m.group(0));
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
                    //Log.e("Merchant Name= ", "No Merchant Name find");
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
                        //Log.e("Card Name= ", "" + m.group(0));
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
                    // Log.e("Card Name= ", "No Card Name find");
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
                        //Log.e("Bank Name= ", "" + m.group(0));
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
                    // Log.e("Bank Name= ", "No Bank Name find");
                    Bank_Name = "No Bank Name find";
                }
                // sms.add("Massage _id=" + _id + "\nMassage Address=" + address + "\nMassage Date=" + date /*+ "\nMassage Body=" + body*/ + "\nA/C no=" + A_C_no + "\nAmount Value: " + amount_value + "\nMerchant Name: " + Merchant_Name + "\nCard Name: " + Card_Name + "\nBank Name: " + Bank_Name);
                // sms.add("\nAmount Value: " + amount_value);
                accountAddress = A_C_no;
                if (accountAddress.equalsIgnoreCase("No Account find")) {
                    accountAddress = Card_Name;
                }

              /*  if (accountAddress.equalsIgnoreCase("No Card Name find")) {
                    accountAddress = Bank_Name;
                }*/

                /*if (accountAddress.equalsIgnoreCase("No Card Name find")) {
                    accountAddress = Card_Name;
                }*/

                if (!smslist.contains(accountAddress)) {
                    smslist.add(accountAddress);
                    MassageItemList massageItemList = new MassageItemList();
                    massageItemList.set_id(_id);
                    massageItemList.setAddress(address);
                    massageItemList.setDate(datee);
                    massageItemList.setBody(body);
                    massageItemList.setA_C_no(A_C_no);
                    massageItemList.setAmount_value(amount_value);
                    massageItemList.setMerchant_Name(Merchant_Name);
                    massageItemList.setCard_Name(Card_Name);
                    massageItemList.setBank_Name(Bank_Name);
                    massageItemLists.add(massageItemList);
                }

                //  Log.e("Clear_Bal Massage", "Clear Bal: " + Bank_Name);
            }
        }

        if (cur != null) {
            cur.close();
        }
        readMassageAdapter.notifyDataSetChanged();
    }

    public void onSetECategory() {
        //String ArrayList
        ArrayList<String> cList = new ArrayList<String>();
        cList.add("\uD83C\uDF54 Food and Groceries");
        cList.add("\uD83D\uDCFD Entertainment");
        cList.add("\uD83D\uDC8A Health & Fitness");
        cList.add("\uD83C\uDFEB Education");
        cList.add("\uD83D\uDECD Shopping");
        cList.add("\uD83D\uDC68\u200D\uD83D\uDD27 Personal Care");
        cList.add("\uD83D\uDC9D Gifts & Donations");
        cList.add("\uD83D\uDCDF Bills & Utilities");
        cList.add("\uD83D\uDE95 Auto & Transport");
        for (int i = 0; i < cList.size(); i++) {
            String category_name = cList.get(i);
            Category category = new Category(category_name, expenses_Mode);
            RealmManager.getInstance().save(category, Category.class);
        }

    }

    public void onSetICategory() {
        //String ArrayList
        ArrayList<String> cList = new ArrayList<String>();
        cList.add("\uD83D\uDCB0 Salary");
        cList.add("\uD83D\uDCB2 Paycheck");
        cList.add("\uD83E\uDD11 Bonus");
        cList.add("\uD83D\uDCB5 Interest Income");
        cList.add("\uD83D\uDCBC Reimbursement");
        cList.add("\uD83C\uDFE0 Rental Income");
        for (int i = 0; i < cList.size(); i++) {
            String category_name = cList.get(i);
            Category category = new Category(category_name, income_Mode);
            RealmManager.getInstance().save(category, Category.class);
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{READ_SMS, CALL_PHONE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {

                   /* boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean cameraAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;*/
                    boolean readsms = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean call_phone = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    //if (locationAccepted && cameraAccepted)
                    if (!readsms && call_phone) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_SMS) && shouldShowRequestPermissionRationale(CALL_PHONE)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_SMS, CALL_PHONE},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    } else {
                        getAllSMS();
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(MainActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* if (!checkPermission()) {
            requestPermission();
        } else {
            getAllSMS();
        }*/
        readMassageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        /*if (!checkPermission()) {
            requestPermission();
        } else {
            getAllSMS();
        }*/
    }

}
