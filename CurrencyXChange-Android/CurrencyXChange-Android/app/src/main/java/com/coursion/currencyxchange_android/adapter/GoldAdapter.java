package com.coursion.currencyxchange_android.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.coursion.currencyxchange_android.R;
import com.coursion.currencyxchange_android.model.Gold;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Boom on 9/24/2017.
 */

public class GoldAdapter extends RecyclerView.Adapter<GoldAdapter.GoldHolder>{

    private ArrayList<Gold> list;
    private LayoutInflater inflater;
    private Activity activity;
    private Context context;

    public GoldAdapter(Activity activity, Context context, ArrayList<Gold> list) {
        this.context = context;
        this.list = list;
        this.activity = activity;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public GoldAdapter.GoldHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View root = inflater.inflate(R.layout.currency_template, viewGroup, false);
        GoldHolder holder = new GoldHolder(root);
        final Gold current = list.get(i);
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        holder.currency_container.setBackgroundColor(color);
//        holder.currency_image.setBackground();
        holder.currency_name.setText(current.getFull_name());
        holder.currency_value.setText(String.format("%.4f", current.getBuying()));
        return holder;
    }

    @Override
    public void onBindViewHolder(final GoldHolder holder, final int position) {
        final Gold current = list.get(position);
//        holder.currency_image.setBackground();
        holder.currency_name.setText(current.getFull_name());
        holder.currency_value.setText(String.format("%.4f", current.getBuying()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class GoldHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RelativeLayout currency_container;
        ImageView currency_image;
        TextView currency_name;
        TextView currency_value;
        GoldHolder(View itemView) {
            super(itemView);
            currency_container = (RelativeLayout) itemView.findViewById(R.id.currency_container);
            currency_image = (ImageView) itemView.findViewById(R.id.currency_image);
            currency_name = (TextView) itemView.findViewById(R.id.currency_name);
            currency_value = (TextView) itemView.findViewById(R.id.currency_value);
            currency_container.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
           /* for (int i = 0; i < list.size(); i++) {
                if (String.valueOf(list.get(i).getId()).equalsIgnoreCase(incident_id.getText().toString())) {
                    Log.d("MyApp", "List id : " + list.get(i).getId() + "\nIncident Id : " + incident_id.getText());
                    prefManagerIncident.saveIncident("clicked_incident", list.get(i));
                    prefUtil.saveTappedMarker(list.get(i).getSectionId(), list.get(i).getLatitude(), list.get(i).getLongitude(), "incident");
                }
            }
            activity.startActivity(new Intent(activity, SelectedCurrencyActivity.class));*/
        }
    }
}
