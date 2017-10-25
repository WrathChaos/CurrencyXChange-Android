package com.coursion.currencyxchange_android_coursion.controller

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.InputMethodManager
import com.coursion.currencyxchange_android_coursion.R
import com.coursion.currencyxchange_android_coursion.model.Currency
import com.coursion.currencyxchange_android_coursion.pref.PrefManager
import com.tapadoo.alerter.Alerter
import kotlinx.android.synthetic.main.activity_selected_currency.*


class SelectedCurrencyActivity : AppCompatActivity() {
    // Preferences
    lateinit private var prefManager: PrefManager
    private var currency: Currency? = null
    private var flag: Boolean = false // False = TL, True = Selected Currency

    private var textWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {

        }

        override fun afterTextChanged(editable: Editable?) {
            if (editable != null && !editable.toString().equals("", ignoreCase = true)) {
                if (left_currency_et.text.hashCode() == editable.hashCode()) {
                    Log.d("MyApp", "LEFT TEXTWATCHER")
                    val userValue = editable.toString().replace(',', '.')
                    val value = java.lang.Double.parseDouble(userValue)
                    right_currency_et.removeTextChangedListener(this)
                    right_currency_et.setText(calculate(value))
                    right_currency_et.addTextChangedListener(this)
                } else if (right_currency_et.text.hashCode() == editable.hashCode()) {
                    Log.d("MyApp", "RIGHT TEXTWATCHER")
                    val userValue = editable.toString().replace(',', '.')
                    val value = java.lang.Double.parseDouble(userValue)
                    left_currency_et.removeTextChangedListener(this)
                    left_currency_et.setText(calculateFromTL(value))
                    left_currency_et.addTextChangedListener(this)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selected_currency)
        // Setup
        setup()
        // Keyboard Auto Pop up
        autoPopupKeyboard()
        // EditText Listener
        listeners()
    }

    override fun onContentChanged() {
        super.onContentChanged()
        prefManager = PrefManager(this@SelectedCurrencyActivity)
    }

    private fun setup() {
        currency = prefManager.selectedCurrency
        title_right_side.text = currency!!.code + " ➙ TL"
        currency_title_left.text = currency!!.code
        currency_title_right.text = "TL"
        right_currency_et.setText(String.format("%.4f", currency!!.buying))
        copyBtn.setTitle("Copy TL")
        left_currency_et.setSelection(left_currency_et.text.length)
        left_currency_et.addTextChangedListener(textWatcher)
        right_currency_et.addTextChangedListener(textWatcher)
    }

    private fun autoPopupKeyboard() {
        (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                .showSoftInput(left_currency_et, InputMethodManager.SHOW_FORCED)
    }

    private fun listeners() {
        /**
         * Focus on Change Listeners
         */
        left_currency_et.setOnFocusChangeListener { v, hasFocus ->
            left_currency_et.removeTextChangedListener(textWatcher)
            copyBtn.setTitle("Copy " + currency!!.code)
            left_currency_et.addTextChangedListener(textWatcher)
            flag = true
        }

        right_currency_et.setOnFocusChangeListener{ v, hasFocus ->
            right_currency_et.removeTextChangedListener(textWatcher)
            copyBtn.setTitle("Copy TL")
            right_currency_et.addTextChangedListener(textWatcher)
            flag = false
        }


        /**
         * Long Click Listeners for clearing the edittext
         */
        left_currency_et.setOnLongClickListener {
            left_currency_et.setText("")
            true
        }
        right_currency_et.setOnLongClickListener {
            right_currency_et.setText("")
            true
        }

        /**
         * Copy Buttons Click Listener
         */
        copyBtn.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            if (!flag) {
                val clip = ClipData.newPlainText("", left_currency_et.text.toString())
                clipboard.primaryClip = clip
                Alerter.create(this@SelectedCurrencyActivity)
                        .setTitle("Copied!")
                        .setIcon(R.drawable.ic_copy)
                        .setDuration(750)
                        .setBackgroundColorRes(R.color.green_500)
                        .setText("Currency value is " + left_currency_et.text.toString() + " copied!")
                        .show()
            } else {
                val clip = ClipData.newPlainText("", right_currency_et.text.toString())
                clipboard.primaryClip = clip
                Alerter.create(this@SelectedCurrencyActivity)
                        .setTitle("Copied!")
                        .setIcon(R.drawable.ic_copy)
                        .setBackgroundColorRes(R.color.green_500)
                        .setDuration(750)
                        .setText("Currency value is " + right_currency_et.text.toString() + " copied!")
                        .show()
            }
        }
    }

    private fun calculate(userValue: Double?): String {
        return String.format("%.4f", currency!!.buying!! * userValue!!)
    }

    private fun calculateFromTL(userValue: Double?): String {
        return String.format("%.4f", userValue!! / currency!!.buying!!)
    }
}
