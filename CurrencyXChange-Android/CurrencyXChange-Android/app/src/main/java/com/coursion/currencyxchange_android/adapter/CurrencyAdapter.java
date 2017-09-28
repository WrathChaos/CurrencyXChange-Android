package com.coursion.currencyxchange_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coursion.currencyxchange_android.R;
import com.coursion.currencyxchange_android.controller.SelectedCurrencyActivity;
import com.coursion.currencyxchange_android.model.Currency;
import com.coursion.currencyxchange_android.pref.PrefManager;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder> {

    private ArrayList<Currency> list;
    private LayoutInflater inflater;
    private Activity activity;
    private Context context;
    // Preferences
    private PrefManager prefManager;
    private ArrayList<String> colorArray;

    public CurrencyAdapter(Activity activity, Context context, ArrayList<Currency> list) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
        prefManager = new PrefManager(activity);
        colorArray = prefManager.getColorArray("currency_color");
    }

    @Override
    public CurrencyHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View root = inflater.inflate(R.layout.currency_template, viewGroup, false);
        CurrencyHolder holder = new CurrencyHolder(root);
        final Currency current = list.get(i);
        holder.currency_name.setText(current.getFull_name());
        holder.currency_value.setText(String.format("%.4f", current.getBuying()));
        return holder;
    }

    @Override
    public void onBindViewHolder(final CurrencyHolder holder, final int position) {
        final Currency current = list.get(position);
        if (position >= colorArray.size()) {
            Random rnd = new Random();
            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
            holder.currency_container.setBackgroundColor(color);
        } else {
            holder.currency_container.setBackgroundColor(Color.parseColor(colorArray.get(position)));
        }
        holder.currency_name.setText(current.getFull_name());
        holder.currency_value.setText(String.format("%.4f", current.getBuying()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CurrencyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout currency_container;
        ImageView currency_image;
        TextView currency_name;
        TextView currency_value;

        CurrencyHolder(View itemView) {
            super(itemView);
            currency_container = itemView.findViewById(R.id.currency_container);
            currency_image = itemView.findViewById(R.id.currency_image);
            currency_name = itemView.findViewById(R.id.currency_name);
            currency_value = itemView.findViewById(R.id.currency_value);
            currency_container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            for (Currency currency : list){
                if (currency.getFull_name().equalsIgnoreCase(currency_name.getText().toString())){
                    Log.d("MyApp", "Selected Currency Name : " + currency.getFull_name());
                    // Saved selected currency
                    PrefManager prefManager = new PrefManager(activity);
                    prefManager.saveSelectedCurrency(currency);
                    activity.startActivity(new Intent(activity, SelectedCurrencyActivity.class));
                }
            }
        }
    }
}
