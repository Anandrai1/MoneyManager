package com.fintrackindia.moneymanager.onlie_invest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintrackindia.moneymanager.R;

import java.util.List;

public class OnlieInvestAdapter extends RecyclerView.Adapter<OnlieInvestAdapter.myViewHolder> {
    private Context context;
    private List<OnlineInvesterList> onlineInvesterListList;


    public OnlieInvestAdapter(Context context, List<OnlineInvesterList> onlineInvesterListList) {
        this.context = context;
        this.onlineInvesterListList = onlineInvesterListList;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_onlineinvestment, parent, false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        final OnlineInvesterList onlineInvesterList = onlineInvesterListList.get(position);
        holder.tv_dName.setText(onlineInvesterList.getName());

        holder.iv_logo.setImageDrawable(onlineInvesterList.getdImage());

       /* if (position % 2 == 0) {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.hdfc_life));
        } else {
            holder.iv_logo.setImageDrawable(context.getDrawable(R.drawable.sbi_life));
        }*/

        holder.tv_call.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + onlineInvesterList.getCareNumber()));
                context.startActivity(callIntent);
            }
        });

        holder.tv_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", onlineInvesterList.getEmailAdd(), null));
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                context.startActivity(Intent.createChooser(intent, "Choose an Email client :"));
            }
        });

        holder.tv_invest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(onlineInvesterList.getWebURL()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return onlineInvesterListList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        /*Min Amount*/
        TextView tv_dName, tv_call, tv_email, tv_invest;
        ImageView iv_logo;

        public myViewHolder(View itemView) {
            super(itemView);
            iv_logo = (ImageView) itemView.findViewById(R.id.iv_logo);
            tv_dName = (TextView) itemView.findViewById(R.id.tv_dName);
            tv_call = (TextView) itemView.findViewById(R.id.tv_call);
            tv_email = (TextView) itemView.findViewById(R.id.tv_email);
            tv_invest = (TextView) itemView.findViewById(R.id.tv_invest);
        }
    }
}
