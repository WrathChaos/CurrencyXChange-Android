package com.coursion.currencyxchange_android.controller;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.coursion.currencyxchange_android.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.blurry.Blurry;

public class SelectedCurrencyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_currency);
        ButterKnife.bind(this);

        Blurry.with(SelectedCurrencyActivity.this)
                .radius(25)
                .sampling(2)
                .async()
                .onto((ViewGroup) findViewById(R.id.content));
    }
}
