package com.fintrackindia.moneymanager.utils;

import android.preference.PreferenceManager;
import android.widget.EditText;

import com.fintrackindia.moneymanager.MyApplication;
import com.fintrackindia.moneymanager.R;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String formatDateToString(Date date, String pattern) {
        DateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    public static boolean isEmptyField(EditText et) {
        return (et.getText() == null || et.getText().toString().isEmpty());
    }

  /*  public static List<Integer> getListColors() {
        ArrayList<Integer> colors = new ArrayList<>();
       *//* for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);*//*
     *//* for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);*//*
     *//* for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);*//*
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);
        colors.add(ColorTemplate.getHoloBlue());
        return colors;
    }*/

    public static String getFormattedCurrency(float number) {
        String countryCode = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getString(MyApplication.getContext().getString(R.string.pref_country_key), MyApplication.getContext().getString(R.string.default_country));
        Locale locale = new Locale("EN", countryCode);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedNumber = numberFormat.format(number);
        String symbol = numberFormat.getCurrency().getSymbol(locale);
        return formattedNumber.replace(symbol, symbol + " ");
    }

    public static String getCurrentDateFormat() {
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext()).getString(MyApplication.getContext().getString(R.string.date_format_key), MyApplication.getContext().getString(R.string.default_date_format));
    }

}
