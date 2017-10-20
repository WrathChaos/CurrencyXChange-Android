package com.coursion.currencyxchange_android.adapter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.coursion.currencyxchange_android.R
import com.coursion.currencyxchange_android.controller.SelectedCurrencyActivity
import com.coursion.currencyxchange_android.model.Currency
import com.coursion.currencyxchange_android.pref.PrefManager

import java.util.ArrayList
import java.util.Random

/**
 * Created by Kuray(FreakyCoder) on 22/09/2017.
 */

class CurrencyAdapter(private val activity: Activity, private val context: Context, private val list: ArrayList<Currency>) : RecyclerView.Adapter<CurrencyAdapter.CurrencyHolder>() {
    private val inflater: LayoutInflater
    // Preferences
    private val prefManager: PrefManager
    private val colorArray: ArrayList<String>?

    init {
        inflater = LayoutInflater.from(context)
        prefManager = PrefManager(activity)
        colorArray = prefManager.getColorArray("currency_color")
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CurrencyHolder {
        val root = inflater.inflate(R.layout.currency_template, viewGroup, false)
        val holder = CurrencyHolder(root)
        val current = list[i]
        holder.currency_name.text = current.full_name
        holder.currency_value.setText(String.format("%.4f", current.buying))
        return holder
    }

    override fun onBindViewHolder(holder: CurrencyHolder, position: Int) {
        val current = list[position]
        if (position >= colorArray!!.size) {
            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
            holder.currency_container.setBackgroundColor(color)
        } else {
            holder.currency_container.setBackgroundColor(Color.parseColor(colorArray[position]))
        }
        holder.currency_name.text = current.full_name
        holder.currency_value.setText(String.format("%.4f", current.buying))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class CurrencyHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var currency_container: RelativeLayout
        var currency_image: ImageView
        var currency_name: TextView
        var currency_value: TextView

        init {
            currency_container = itemView.findViewById(R.id.currency_container)
            currency_image = itemView.findViewById(R.id.currency_image)
            currency_name = itemView.findViewById(R.id.currency_name)
            currency_value = itemView.findViewById(R.id.currency_value)
            currency_container.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            for (currency in list) {
                if (currency.full_name.equals(currency_name.text.toString(), ignoreCase = true)) {
                    Log.d("MyApp", "Selected Currency Name : " + currency.full_name)
                    // Saved selected currency
                    val prefManager = PrefManager(activity)
                    prefManager.saveSelectedCurrency(currency)
                    activity.startActivity(Intent(activity, SelectedCurrencyActivity::class.java))
                }
            }
        }
    }
}
