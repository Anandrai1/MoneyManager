package com.fintrackindia.moneymanager.readsms;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fintrackindia.moneymanager.MassageItemList;
import com.fintrackindia.moneymanager.R;
import com.fintrackindia.moneymanager.database.RealmManager;
import com.fintrackindia.moneymanager.interfaces.IExpensesType;
import com.fintrackindia.moneymanager.spends.Expense;
import com.fintrackindia.moneymanager.spends.categories.Category;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;

public class ReadMassageAdapter extends RecyclerView.Adapter<ReadMassageAdapter.MyViewHolder> {

    ArrayList<String> arrayList_mass;
    Context context;
    String msssagetype;
    String msssagetype2 = "";
    String accounttype;
    int layoutType;
    Category currentCategory;
    int mExpenseType = IExpensesType.MODE_EXPENSES;
    private Date selectedDate;
    private List<MassageItemList> massageItemLists;

    public ReadMassageAdapter(Context context, ArrayList<String> arrayList_mass) {
        this.context = context;
        // this.arrayList_mass = arrayList_mass;
    }

    public ReadMassageAdapter(Context context, List<MassageItemList> massageItemLists, int layoutType) {
        this.context = context;
        this.massageItemLists = massageItemLists;
        this.layoutType = layoutType;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_read_massage, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        /* this.colorExpense = EdgeExpensesApp.getContext().getResources().getColor(R.color.red_400);
                this.colorIncome = EdgeExpensesApp.getContext().getResources().getColor(R.color.SeaGreen);*/

        if (layoutType == 0) {
            accounttype = "Your Ac. ";
            msssagetype2 = "";
            holder.mainlayout.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT; // LayoutParams: android.view.ViewGroup.LayoutParams
            holder.mainlayout.requestLayout();//It is necesary to refresh the screen

            holder.card_layout.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT; // LayoutParams: android.view.ViewGroup.LayoutParams
            holder.card_layout.requestLayout();//It is necesary to refresh the screen

            holder.iv_view.setVisibility(View.GONE);
        } else if (layoutType == 1) {
            accounttype = "Ac. Ending with ";
            // msssagetype2 = "Has ";
            holder.mainlayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT; // LayoutParams: android.view.ViewGroup.LayoutParams
            holder.mainlayout.requestLayout();//It is necesary to refresh the screen

            holder.card_layout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT; // LayoutParams: android.view.ViewGroup.LayoutParams
            holder.card_layout.requestLayout();//It is necesary to refresh the screen
            //  holder.iv_view.setVisibility(View.VISIBLE);
        }

        final MassageItemList massageItemList = massageItemLists.get(position);
        if (massageItemList.getBody().contains("is credited by") || massageItemList.getBody().contains("is credited for")) {
            holder.tv_spendsAmount.setTextColor(context.getResources().getColor(R.color.SeaGreen));
            holder.tv_rupee.setTextColor(context.getResources().getColor(R.color.SeaGreen));
            // msssagetype = msssagetype2 + "is credited for  ";
            // holder.tv_spendsAmount.setText(massageItemList.getAmount_value());
        } else if (massageItemList.getBody().contains("been debited for") || massageItemList.getBody().contains("is debited") || massageItemList.getBody().contains("successful payment") || massageItemList.getBody().contains("was withdrawn") || massageItemList.getBody().contains("txn of") || massageItemList.getBody().contains("for a purchase") || massageItemList.getBody().contains("Third party transfer to")) {
            holder.tv_spendsAmount.setTextColor(context.getResources().getColor(R.color.red_400));
            holder.tv_rupee.setTextColor(context.getResources().getColor(R.color.red_400));
            // holder.tv_spendsAmount.setText(massageItemList.getAmount_value());
            /*if (massageItemList.getBody().contains("withdrawn")) {
                msssagetype = msssagetype2 + "withdrawn with  ";
            } else {
                msssagetype = msssagetype2 + "been debited for  ";
            }*/

        } else /*if (massageItemList.getBody().contains("Clear Bal") || massageItemList.getBody().contains("Avail Bal") || massageItemList.getBody().contains("Avl Bal") || massageItemList.getBody().contains("Available Balance") || massageItemList.getBody().contains("available balance")) */ {
            holder.tv_spendsAmount.setTextColor(context.getResources().getColor(R.color.yellow_900));
            holder.tv_rupee.setTextColor(context.getResources().getColor(R.color.yellow_900));
            //msssagetype = msssagetype2 + "Clear Bal  ";
            /*holder.tv_availBal.setText(massageItemList.getAmount_value());
            holder.tv_spendsAmount.setText("0");*/
        }

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Append String: ");
        String str = "JCG";
        stringBuffer.append(str);
        stringBuffer.append("n");

        if (massageItemList.getBody().contains("ATM")) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.ic_atm));
            msssagetype = msssagetype2 + "ATM Withdrawn ";
        } else if (massageItemList.getBody().contains("Card purchase") || massageItemList.getBody().contains("Swipe") || massageItemList.getBody().contains("swipe")) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.ic_payment_terminal));
            msssagetype = msssagetype2 + "Card purchase ";
        } else if (massageItemList.getBody().contains("IMPS") || massageItemList.getBody().contains("NEFT") || massageItemList.getBody().contains("Third party transfer to")) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.ic_student));
            msssagetype = msssagetype2 + "Net Banking ";
        } else if (massageItemList.getBody().contains("is debited") || massageItemList.getBody().contains("been debited")) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.ic_student));
            msssagetype = msssagetype2 + "Debited with ";
        } else if (massageItemList.getBody().contains("is credited")) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.ic_student));
            msssagetype = msssagetype2 + "Credited with ";
        } else {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.ic_banks));
            msssagetype = msssagetype2 + "Clear Balance are";
        }
        double Amount = Double.parseDouble(massageItemList.getAmount_value());
        //float Amount = Float.parseFloat(massageItemList.getAmount_value());

        if (layoutType == 0) {
            holder.tv_spendsAmount.setText(msssagetype + "₹ " + massageItemList.getAmount_value());
            holder.tv_rupee.setVisibility(View.GONE);
        } else {
            //String amount = massageItemList.getAmount_value().replaceAll("₹ ", "");
            holder.tv_spendsAmount.setText(msssagetype + massageItemList.getAmount_value());
            holder.tv_rupee.setVisibility(View.VISIBLE);
            holder.tv_rupee.setText(massageItemList.getAmount_value());
            holder.tv_spendsAmount.setText(msssagetype);
        }

        holder.tv_smsbody.setText(massageItemList.getBody());

        // holder.title.setText(arrayList_mass.get(position));
        /*if (!massageItemList.getBank_Name().equalsIgnoreCase("No Bank Name find") || massageItemList.getBank_Name().equalsIgnoreCase("")) {
            holder.tv_bankname.setText(massageItemList.getBank_Name());
        } else if (!massageItemList.getCard_Name().equalsIgnoreCase("No Card Name find")) {
            holder.tv_bankname.setText(massageItemList.getCard_Name());
        } else {
            holder.tv_bankname.setText(massageItemList.getA_C_no());
        }*/

        if (!massageItemList.getA_C_no().equalsIgnoreCase("No Account find")) {
            holder.tv_bankname.setText(accounttype + massageItemList.getA_C_no());
        } else if (!massageItemList.getCard_Name().equalsIgnoreCase("No Card Name find")) {
            holder.tv_bankname.setText("Your " + massageItemList.getCard_Name() + " Card");
        } else {
            holder.tv_bankname.setText("Your " + massageItemList.getBank_Name());
        }

        holder.card_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (layoutType == 1) {
                    // holder.tv_smsbody.setVisibility(View.VISIBLE);
                    LayoutInflater factory = LayoutInflater.from(context);
                    final View deleteDialogView = factory.inflate(R.layout.layout_dialog_massage_view, null);
                    final AlertDialog deleteDialog = new AlertDialog.Builder(context).create();
                    deleteDialog.setView(deleteDialogView);
                    TextView tv_addtype = (TextView) deleteDialogView.findViewById(R.id.tv_addtype);
                    final Switch sw_addtype = (Switch) deleteDialogView.findViewById(R.id.sw_addtype);
                    if (massageItemList.getBody().contains("is credited by") || massageItemList.getBody().contains("is credited for")) {
                        tv_addtype.setText("Is Add Income");
                        tv_addtype.setVisibility(View.VISIBLE);
                        sw_addtype.setVisibility(View.VISIBLE);
                        mExpenseType = IExpensesType.MODE_INCOME;
                    } else if (massageItemList.getBody().contains("been debited for") || massageItemList.getBody().contains("is debited") || massageItemList.getBody().contains("successful payment") || massageItemList.getBody().contains("was withdrawn") || massageItemList.getBody().contains("txn of") || massageItemList.getBody().contains("for a purchase") || massageItemList.getBody().contains("Third party transfer to")) {
                        tv_addtype.setText("Is Add Expense");
                        tv_addtype.setVisibility(View.VISIBLE);
                        sw_addtype.setVisibility(View.VISIBLE);
                        mExpenseType = IExpensesType.MODE_EXPENSES;
                    } else {
                        tv_addtype.setVisibility(View.GONE);
                        sw_addtype.setVisibility(View.GONE);
                    }

                    long milliSeconds = Long.parseLong(massageItemList.getDate());
                    selectedDate = new Date(milliSeconds);

                    RealmObject realmObject= RealmManager.getInstance().findBySMSTime(Expense.class,selectedDate);

                    sw_addtype.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (sw_addtype.isChecked())
                                RealmManager.getInstance().save(new Expense("", selectedDate, mExpenseType, currentCategory, Float.parseFloat(massageItemList.getAmount_value())), Expense.class);
                                // RealmManager.getInstance().save(new Expense("", "", 1, "", Float.parseFloat("1")), Expense.class);
                                //Toast.makeText(context, "isChecked", Toast.LENGTH_SHORT).show();
                                //statusSwitch1 = simpleSwitch1.getTextOn().toString();
                            else
                                Toast.makeText(context, "is not Checked", Toast.LENGTH_SHORT).show();
                            //statusSwitch1 = simpleSwitch1.getTextOff().toString();
                        }
                    });

                    TextView txt_dia = (TextView) deleteDialogView.findViewById(R.id.txt_dia);
                    DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(milliSeconds);
                    String finalDateString = formatter.format(calendar.getTime());
                    txt_dia.setText("[" + massageItemList.getAddress() + "] " + finalDateString + "\n" + massageItemList.getBody());
                    deleteDialogView.findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //your business logic
                            deleteDialog.dismiss();
                        }
                    });
                    deleteDialogView.findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteDialog.dismiss();
                        }
                    });
                    deleteDialog.show();
                } else {
                    holder.tv_smsbody.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        //return arrayList_mass.size();
        return massageItemLists.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_smsbody, tv_spendsAmount, tv_rupee;
        public TextView tv_availBal, tv_bankname;
        public ConstraintLayout mainlayout;
        public CardView card_layout;
        public ImageView iv_logo;
        public ImageView iv_view;

        public MyViewHolder(View view) {
            super(view);
            tv_smsbody = (TextView) view.findViewById(R.id.tv_smsbody);
            tv_spendsAmount = (TextView) view.findViewById(R.id.tv_spendsAmount);
            tv_rupee = (TextView) view.findViewById(R.id.tv_rupee);
            tv_availBal = (TextView) view.findViewById(R.id.tv_availBal);
            tv_bankname = (TextView) view.findViewById(R.id.tv_bankname);
            mainlayout = (ConstraintLayout) view.findViewById(R.id.mainlayout);
            card_layout = (CardView) view.findViewById(R.id.card_layout);
            iv_logo = (ImageView) view.findViewById(R.id.iv_logo);
            iv_view = (ImageView) view.findViewById(R.id.iv_view);
        }
    }

}