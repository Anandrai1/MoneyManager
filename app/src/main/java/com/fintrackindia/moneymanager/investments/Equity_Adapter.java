package com.fintrackindia.moneymanager.investments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintrackindia.moneymanager.R;

import java.util.List;

public class Equity_Adapter extends RecyclerView.Adapter<Equity_Adapter.myViewHolder> {
    private Context context;
    private List<Equity_ListItem> equity_listItemList;


    public Equity_Adapter(Context context, List<Equity_ListItem> equity_listItemList) {
        this.context = context;
        this.equity_listItemList = equity_listItemList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_equity, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final Equity_ListItem equityListItem = equity_listItemList.get(position);
        holder.tv_dName.setText(equityListItem.getdName());
        holder.tv_dMinAmount.setText(equityListItem.getdMinAmount());

        holder.iv_logo.setImageDrawable(equityListItem.getdImage());

       /* if (position % 2 == 0) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.hdfc_life));
        } else {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.sbi_life));
        }*/

    }

    @Override
    public int getItemCount() {
        return equity_listItemList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        /*Min Amount*/
        TextView tv_dName, tv_dMinAmount;
        ImageView iv_logo;

        public myViewHolder(View itemView) {
            super(itemView);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tv_dName = (TextView) itemView.findViewById(R.id.tv_dName);
            tv_dMinAmount = (TextView) itemView.findViewById(R.id.tv_dMinAmount);
        }
    }

}
