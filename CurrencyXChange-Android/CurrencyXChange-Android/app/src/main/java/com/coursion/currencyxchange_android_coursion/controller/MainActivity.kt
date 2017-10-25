package com.coursion.currencyxchange_android_coursion.controller

import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.coursion.currencyxchange_android_coursion.R
import com.coursion.currencyxchange_android_coursion.adapter.CurrencyAdapter
import com.coursion.currencyxchange_android_coursion.adapter.GoldAdapter
import com.coursion.currencyxchange_android_coursion.background.CurrencyTask
import com.coursion.currencyxchange_android_coursion.background.GoldTask
import com.coursion.currencyxchange_android_coursion.event.CurrencyEvent
import com.coursion.currencyxchange_android_coursion.event.GoldEvent
import com.coursion.currencyxchange_android_coursion.model.Currency
import com.coursion.currencyxchange_android_coursion.model.Gold
import com.coursion.currencyxchange_android_coursion.pref.PrefManager
import com.coursion.currencyxchange_android_coursion.util.NetworkCheck
import org.greenrobot.eventbus.EventBus
import java.util.ArrayList
import cn.pedant.SweetAlert.SweetAlertDialog
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_currency.*
import kotlinx.android.synthetic.main.fragment_gold.*
import org.greenrobot.eventbus.Subscribe

class MainActivity : AppCompatActivity() {

    lateinit private var mSectionsPagerAdapter: SectionsPagerAdapter
    lateinit private var mViewPager: ViewPager
    // Preferences
    lateinit private var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefManager.clearPreferences()

        // Color Array Initialization
        colorArrayInit()
        // Toolbar Initialization
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onContentChanged() {
        super.onContentChanged()
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        prefManager = PrefManager(this@MainActivity)

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container)
        mViewPager.adapter = mSectionsPagerAdapter

        // This is IMPORTANT for TABS
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
    }

    private fun colorArrayInit() {
        try {
            val colorArray = prefManager.getColorArray("currency_color")
            val goldColorArray = prefManager.getColorArray("gold_color")
            if (colorArray!!.size <= 0) {
                // Generating and saving color array for currency first 10 data
                prefManager.saveColorArray("currency_color")
            }
            if (goldColorArray!!.size <= 0) {
                // Generating and saving color array for currency first 10 data
                prefManager.saveColorArray("gold_color")
            }
        } catch (e: Exception) {
            Log.d("MyApp", "colorArrayInit: Exception : " + e.toString())
            e.printStackTrace()
            // Generating and saving color array for currency first 10 data
            prefManager.saveColorArray("currency_color")
            // Generating and saving color array for currency first 10 data
            prefManager.saveColorArray("gold_color")
        }

    }

    class PlaceholderFragment : Fragment() {
        // Loading Outlets
        lateinit private var loading_view_currency: View
        lateinit private var nc_view_currency: View
        lateinit private var loading_view_gold: View
        lateinit private var nc_view_gold: View

        // Currency Variables
        lateinit private var currencies: ArrayList<Currency>
        lateinit private var currencyAdapter: CurrencyAdapter
        // Gold Variables
        lateinit private var goldies: ArrayList<Gold>
        lateinit private var goldAdapter: GoldAdapter

        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                                  savedInstanceState: Bundle?): View? {
            val rootView = inflater!!.inflate(R.layout.fragment_currency, container, false)
            val rootView2 = inflater.inflate(R.layout.fragment_gold, container, false)

            loading_view_currency = rootView.findViewById(R.id.loading_view_currency);
            nc_view_currency = rootView.findViewById(R.id.nc_view_currency);
            loading_view_gold = rootView2.findViewById(R.id.loading_view_gold);
            nc_view_gold = rootView2.findViewById(R.id.nc_view_gold);


            when (arguments.getInt(ARG_SECTION_NUMBER)) {
                1 -> {
                    if (!networkCheck()) {
                        SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.no_internet))
                                .setContentText(getString(R.string.try_again))
                                .setConfirmText(getString(R.string.try_again))
                                .setConfirmClickListener { sDialog ->
                                    sDialog.dismissWithAnimation()
                                    activity.recreate()
                                    activity.finish()
                                }
                                .show()
                    } else {
                        try {
                            loading_view_currency.visibility = View.VISIBLE
                            nc_view_currency!!.visibility = View.GONE
                            // Get all currency data from API
                            getCurrencyData()
                        } catch (e: Exception) {
                            loading_view_currency.visibility = View.GONE
                            Log.e("MyApp", "getCurrencyData Error : " + e.toString())
                            e.printStackTrace()
                            nc_view_currency!!.visibility = View.VISIBLE
                        }

                    }
                    return rootView
                }
                2 -> {
                    if (!networkCheck()) {
                        SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText(getString(R.string.no_internet))
                                .setContentText(getString(R.string.try_again))
                                .setConfirmText(getString(R.string.try_again))
                                .setConfirmClickListener { sDialog ->
                                    sDialog.dismissWithAnimation()
                                    activity.recreate()
                                    activity.finish()
                                }
                                .show()
                    } else {
                        try {
                            loading_view_gold.visibility = View.VISIBLE
                            nc_view_gold.visibility = View.GONE
                            // Get all gold data from API
                            getGoldData()
                        } catch (e: Exception) {
                            loading_view_gold.visibility = View.GONE
                            Log.e("MyApp", "getGoldData Error : " + e.toString())
                            e.printStackTrace()
                            nc_view_gold.visibility = View.VISIBLE
                        }

                    }
                    return rootView2
                }
            }
            return rootView
        }

        override fun onResume() {
            super.onResume()
            if (!networkCheck()) {
                SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText(getString(R.string.no_internet))
                        .setContentText(getString(R.string.try_again))
                        .setConfirmText(getString(R.string.try_again))
                        .setConfirmClickListener { sDialog ->
                            sDialog.dismissWithAnimation()
                            activity.recreate()
                        }
                        .show()
            }
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
            }
        }


        @Subscribe
        fun listenCurrencyEvent(currencyEvent: CurrencyEvent) {
            loading_view_currency.visibility = View.GONE
            currencies = ArrayList(currencyEvent.currencyList!!)
            Log.d("MyApp", "listenCurrencyEvent Currency List size : " + currencies.size)
            recyclerViewCurrency.layoutManager = LinearLayoutManager(context)
            currencyAdapter = CurrencyAdapter(activity, context, currencies)
            // Creating recyclerview animation
            val animationAdapter = AlphaInAnimationAdapter(currencyAdapter)
            animationAdapter.setFirstOnly(false)
            recyclerViewCurrency!!.adapter = ScaleInAnimationAdapter(animationAdapter)
        }

        @Subscribe
        fun listenGoldEvent(goldEvent: GoldEvent) {
            loading_view_gold.visibility = View.GONE
            goldies = ArrayList(goldEvent.goldList!!)
            Log.d("MyApp", "listenGoldEvent Gold List Size : " + goldies.size)
            recyclerViewGold.layoutManager = LinearLayoutManager(context)
            goldAdapter = GoldAdapter(activity, context, goldies)
            // Creating recyclerview animation
            val animationAdapter = AlphaInAnimationAdapter(goldAdapter)
            animationAdapter.setFirstOnly(false)
            recyclerViewGold.adapter = ScaleInAnimationAdapter(animationAdapter)
        }

        private fun getCurrencyData() {
            val currencyTask = CurrencyTask()
            currencyTask.getCurrencyData()
        }

        private fun getGoldData() {
            val goldTask = GoldTask()
            goldTask.getGoldData()
        }

        /**
         * Internet connection control function
         */
        private fun networkCheck(): Boolean {
            val networkCheck = NetworkCheck(context)
            return networkCheck.isNetworkConnected
        }

        companion object {
            // Fragment based variables
            private val ARG_SECTION_NUMBER = "section_number"

            /**
             * Returns a new instance of this fragment for the given section
             * number.
             */
            fun newInstance(sectionNumber: Int): PlaceholderFragment {
                val fragment = PlaceholderFragment()
                val args = Bundle()
                args.putInt(ARG_SECTION_NUMBER, sectionNumber)
                fragment.arguments = args
                return fragment
            }
        }
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
     inner class SectionsPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1)
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                0 -> return getString(R.string.currency)
                1 -> return getString(R.string.gold)
            }
            return null
        }
    }
}
