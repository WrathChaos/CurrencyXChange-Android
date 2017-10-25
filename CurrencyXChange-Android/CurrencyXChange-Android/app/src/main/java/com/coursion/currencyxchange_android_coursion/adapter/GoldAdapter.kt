package com.coursion.currencyxchange_android_coursion.adapter

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.coursion.currencyxchange_android_coursion.R
import com.coursion.currencyxchange_android_coursion.model.Gold
import com.coursion.currencyxchange_android_coursion.pref.PrefManager

import java.util.ArrayList
import java.util.Random

/**
 * Created by Boom on 9/24/2017.
 */

class GoldAdapter(private val activity: Activity, private val context: Context, private val list: ArrayList<Gold>) : RecyclerView.Adapter<GoldAdapter.GoldHolder>() {
    private val inflater: LayoutInflater
    // Preferences
    private val prefManager: PrefManager
    private val colorArray: ArrayList<String>?

    init {
        inflater = LayoutInflater.from(context)
        prefManager = PrefManager(activity)
        colorArray = prefManager.getColorArray("gold_color")
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): GoldAdapter.GoldHolder {
        val root = inflater.inflate(R.layout.currency_template, viewGroup, false)
        val holder = GoldHolder(root)
        val current = list[i]
        holder.currency_name.text = current.full_name
        holder.currency_value.setText(String.format("%.4f", current.buying))
        return holder
    }

    override fun onBindViewHolder(holder: GoldHolder, position: Int) {
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

    inner class GoldHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var currency_container: RelativeLayout
        var currency_image: ImageView
        var currency_name: TextView
        var currency_value: TextView

        init {
            currency_container = itemView.findViewById<View>(R.id.currency_container) as RelativeLayout
            currency_image = itemView.findViewById<View>(R.id.currency_image) as ImageView
            currency_name = itemView.findViewById<View>(R.id.currency_name) as TextView
            currency_value = itemView.findViewById<View>(R.id.currency_value) as TextView
            currency_container.setOnClickListener(this)
        }

        override fun onClick(view: View) {
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
