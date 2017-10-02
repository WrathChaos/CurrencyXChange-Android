package com.coursion.currencyxchange_android.controller;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.coursion.currencyxchange_android.R;
import com.coursion.currencyxchange_android.adapter.CurrencyAdapter;
import com.coursion.currencyxchange_android.adapter.GoldAdapter;
import com.coursion.currencyxchange_android.background.CurrencyTask;
import com.coursion.currencyxchange_android.background.GoldTask;
import com.coursion.currencyxchange_android.event.CurrencyEvent;
import com.coursion.currencyxchange_android.event.GoldEvent;
import com.coursion.currencyxchange_android.model.Currency;
import com.coursion.currencyxchange_android.model.Gold;
import com.coursion.currencyxchange_android.pref.PrefManager;
import com.coursion.currencyxchange_android.util.NetworkCheck;
import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import org.greenrobot.eventbus.Subscribe;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    // Preferences
    PrefManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Color Array Initialization
        colorArrayInit();
        // Toolbar Initialization
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        prefManager = new PrefManager(MainActivity.this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // This is IMPORTANT for TABS
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void colorArrayInit(){
        try {
            ArrayList<String> colorArray = prefManager.getColorArray("currency_color");
            ArrayList<String> goldColorArray = prefManager.getColorArray("gold_color");
            if (colorArray.size() <= 0){
                // Generating and saving color array for currency first 10 data
                prefManager.saveColorArray("currency_color");
            }
            if (goldColorArray.size() <= 0){
                // Generating and saving color array for currency first 10 data
                prefManager.saveColorArray("gold_color");
            }
        } catch (Exception e) {
            Log.d("MyApp", "colorArrayInit: Exception : " + e.toString());
            e.printStackTrace();
            // Generating and saving color array for currency first 10 data
            prefManager.saveColorArray("currency_color");
            // Generating and saving color array for currency first 10 data
            prefManager.saveColorArray("gold_color");
        }
    }

    public static class PlaceholderFragment extends Fragment {
        // Fragment based variables
        private static final String ARG_SECTION_NUMBER = "section_number";
        // Currency Outlets
        @BindView(R.id.recyclerViewCurrency) RecyclerView recyclerViewCurrency;
        @BindView(R.id.loading_view_currency) View lv_currency;
        @BindView(R.id.nc_view_currency) View nc_view_currency;
        // Currency Variables
        ArrayList<Currency> currencies;
        CurrencyAdapter currencyAdapter;
        // Gold Outlets
        RecyclerView recyclerViewGold;
        View lv_gold;
        View nc_view_gold;
        // Gold Variables
        ArrayList<Gold> goldies;
        GoldAdapter goldAdapter;

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_currency, container, false);
            View rootView2 = inflater.inflate(R.layout.fragment_gold, container, false);
            ButterKnife.bind(this, rootView);
            recyclerViewGold = rootView2.findViewById(R.id.recyclerViewGold);
            lv_gold = rootView2.findViewById(R.id.loading_view_gold);
            nc_view_gold = rootView2.findViewById(R.id.nc_view_gold);

            switch (getArguments().getInt(ARG_SECTION_NUMBER)){
                case 1:
                    if (!networkCheck()) {
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.no_internet))
                                .setContentText(getString(R.string.try_again))
                                .setConfirmText(getString(R.string.try_again))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        getActivity().recreate();
                                        getActivity().finish();
                                    }
                                })
                                .show();
                    } else {
                        try {
                            lv_currency.setVisibility(View.VISIBLE);
                            nc_view_currency.setVisibility(View.GONE);
                            // Get all currency data from API
                            getCurrencyData();
                        } catch (Exception e) {
                            lv_currency.setVisibility(View.GONE);
                            Log.e("MyApp", "getCurrencyData Error : " + e.toString());
                            e.printStackTrace();
                            nc_view_currency.setVisibility(View.VISIBLE);
                        }
                    }
                    return rootView;
                case 2:
                    if(!networkCheck()){
                        new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.no_internet))
                                .setContentText(getString(R.string.try_again))
                                .setConfirmText(getString(R.string.try_again))
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        getActivity().recreate();
                                        getActivity().finish();
                                    }
                                })
                                .show();
                    } else {
                        try {
                            lv_gold.setVisibility(View.VISIBLE);
                            nc_view_gold.setVisibility(View.GONE);
                            // Get all gold data from API
                            getGoldData();
                        } catch (Exception e) {
                            lv_gold.setVisibility(View.GONE);
                            Log.e("MyApp", "getGoldData Error : " + e.toString());
                            e.printStackTrace();
                            nc_view_gold.setVisibility(View.VISIBLE);
                        }
                    }
                    return rootView2;
            }
            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            if (!networkCheck()) {
                new SweetAlertDialog(getActivity(), SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getString(R.string.no_internet))
                        .setContentText(getString(R.string.try_again))
                        .setConfirmText(getString(R.string.try_again))
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                getActivity().recreate();
                            }
                        })
                        .show();
            }
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            if (EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this);
        }

        @Subscribe
        public void listenCurrencyEvent(CurrencyEvent currencyEvent){
            lv_currency.setVisibility(View.GONE);
            currencies = new ArrayList<>(currencyEvent.getCurrencyList());
            Log.d("MyApp", "listenCurrencyEvent Currency List size : " + currencies.size());
            recyclerViewCurrency.setLayoutManager(new LinearLayoutManager(getContext()));
            currencyAdapter = new CurrencyAdapter(getActivity(), getContext(), currencies);
            // Creating recyclerview animation
            AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(currencyAdapter);
            animationAdapter.setFirstOnly(false);
            recyclerViewCurrency.setAdapter(new ScaleInAnimationAdapter(animationAdapter));
        }

        @Subscribe
        public void listenGoldEvent(GoldEvent goldEvent){
            lv_gold.setVisibility(View.GONE);
            goldies = new ArrayList<>(goldEvent.getGoldList());
            Log.d("MyApp", "listenGoldEvent Gold List Size : " + goldies.size());
            recyclerViewGold.setLayoutManager(new LinearLayoutManager(getContext()));
            goldAdapter = new GoldAdapter(getActivity(), getContext(), goldies);
            // Creating recyclerview animation
            AlphaInAnimationAdapter animationAdapter = new AlphaInAnimationAdapter(goldAdapter);
            animationAdapter.setFirstOnly(false);
            recyclerViewGold.setAdapter(new ScaleInAnimationAdapter(animationAdapter));
        }

        private void getCurrencyData(){
            CurrencyTask currencyTask = new CurrencyTask();
            currencyTask.getCurrencyData();
        }

        private void getGoldData(){
            GoldTask goldTask = new GoldTask();
            goldTask.getGoldData();
        }
        /**
         * Internet connection control function
         */
        private boolean networkCheck() {
            NetworkCheck networkCheck = new NetworkCheck(getContext());
            return networkCheck.isNetworkConnected();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }
        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.currency);
                case 1:
                    return getString(R.string.gold);
            }
            return null;
        }
    }
}
