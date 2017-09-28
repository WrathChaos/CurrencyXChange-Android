package com.coursion.currencyxchange_android.controller;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.coursion.currencyxchange_android.R;
import com.coursion.currencyxchange_android.model.Currency;
import com.coursion.currencyxchange_android.pref.PrefManager;
import com.tapadoo.alerter.Alerter;
import butterknife.BindView;
import butterknife.ButterKnife;
import ru.dimorinny.floatingtextbutton.FloatingTextButton;


public class SelectedCurrencyActivity extends AppCompatActivity {
    // Outlets
    @BindView(R.id.bg) ImageView bg;
    @BindView(R.id.content) RelativeLayout content;
    @BindView(R.id.title_right_side) TextView title_right_side;
    @BindView(R.id.currency_title_left) TextView currency_title_left;
    @BindView(R.id.currency_title_right) TextView currency_title_right;
    @BindView(R.id.left_currency_et) EditText left_currency_et;
    @BindView(R.id.right_currency_et) EditText right_currency_et;
    @BindView(R.id.copyBtn) FloatingTextButton copyBtn;
    // Preferences
    PrefManager prefManager;
    Currency currency;
    Boolean flag = false; // False = TL, True = Selected Currency

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_currency);
        ButterKnife.bind(this);
        // Setup
        setup();
        // Keyboard Auto Pop up
        autoPopupKeyboard();
        // EditText Listener
        listeners();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        prefManager  = new PrefManager(SelectedCurrencyActivity.this);
    }

    private void setup(){
        currency = prefManager.getSelectedCurrency();
        title_right_side.setText(currency.getCode() + " ➙ TL");
        currency_title_left.setText(currency.getCode());
        currency_title_right.setText("TL");
        right_currency_et.setText(String.valueOf(currency.getBuying()));
        copyBtn.setTitle("Copy TL");
        left_currency_et.addTextChangedListener(textWatcher);
        right_currency_et.addTextChangedListener(textWatcher);
    }

    private void autoPopupKeyboard(){
        ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE))
                .showSoftInput(left_currency_et, InputMethodManager.SHOW_FORCED);
    }

    private void listeners(){
        left_currency_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                left_currency_et.removeTextChangedListener(textWatcher);
                copyBtn.setTitle("Copy " + currency.getCode());
                left_currency_et.setText("");
                left_currency_et.addTextChangedListener(textWatcher);
                flag = true;
            }
        });
        right_currency_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                right_currency_et.removeTextChangedListener(textWatcher);
                copyBtn.setTitle("Copy TL");
                right_currency_et.setText("");
                right_currency_et.addTextChangedListener(textWatcher);
                flag = false;
            }
        });
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                if (!flag){
                    ClipData clip = ClipData.newPlainText("", right_currency_et.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Alerter.create(SelectedCurrencyActivity.this)
                            .setTitle("Copied!")
                            .setIcon(R.drawable.ic_copy)
                            .setBackgroundColorRes(R.color.green_500)
                            .setDuration(750)
                            .setText("Currency value is " + right_currency_et.getText().toString() + " copied!")
                            .show();
                } else {
                    ClipData clip = ClipData.newPlainText("", left_currency_et.getText().toString());
                    clipboard.setPrimaryClip(clip);
                    Alerter.create(SelectedCurrencyActivity.this)
                            .setTitle("Copied!")
                            .setIcon(R.drawable.ic_copy)
                            .setDuration(750)
                            .setBackgroundColorRes(R.color.green_500)
                            .setText("Currency value is " + left_currency_et.getText().toString() + " copied!")
                            .show();
                }
            }
        });
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable != null && !editable.toString().equalsIgnoreCase("")) {
                if (left_currency_et.getText().hashCode() == editable.hashCode()) {
                    Log.d("MyApp", "LEFT TEXTWATCHER");
                    String userValue = editable.toString().replace(',', '.');
                    Double value = Double.parseDouble(userValue);
                    right_currency_et.removeTextChangedListener(textWatcher);
                    right_currency_et.setText(calculate(value));
                    right_currency_et.addTextChangedListener(textWatcher);
                } else if (right_currency_et.getText().hashCode() == editable.hashCode()){
                    Log.d("MyApp", "RIGHT TEXTWATCHER");
                    String userValue = editable.toString().replace(',', '.');
                    Double value = Double.parseDouble(userValue);
                    left_currency_et.removeTextChangedListener(textWatcher);
                    left_currency_et.setText(calculateFromTL(value));
                    left_currency_et.addTextChangedListener(textWatcher);
                }
            }
        }
    };

    private String calculate(Double userValue){
        return String.format("%.4f", (currency.getBuying() * userValue));
    }

    private String calculateFromTL(Double userValue){
        return String.format("%.4f", (userValue / currency.getBuying()));
    }
}
