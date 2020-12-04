package com.batanks.nextplan.Settings

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.batanks.nextplan.R
import com.batanks.nextplan.Settings.viewmodel.CurrencyViewModel
import com.batanks.nextplan.Settings.viewmodel.LanguageViewModel
import com.batanks.nextplan.arch.BaseAppCompatActivity
import com.batanks.nextplan.arch.response.Status
import com.batanks.nextplan.arch.viewmodel.GenericViewModelFactory
import com.batanks.nextplan.common.getLoadingDialog
import com.batanks.nextplan.home.HomePlanPreview
/*import com.batanks.nextplan.home.adapter.HomePlanPreviewAdapter*/
import com.batanks.nextplan.network.RetrofitClient
import com.batanks.nextplan.splash.SplashActivity
import com.batanks.nextplan.swagger.api.CurrencyAPI
import com.batanks.nextplan.swagger.api.LanguageAPI
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_signin.*

class Settings : BaseAppCompatActivity() {

    var currencyList : ArrayList<String> = arrayListOf()
    var languageList : ArrayList<String> = arrayListOf()

    private val languageViewModel: LanguageViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(LanguageAPI::class.java)?.let {
                LanguageViewModel(it)
            }
        }).get(LanguageViewModel::class.java)
    }

    private val currencyViewModel: CurrencyViewModel by lazy {
        ViewModelProvider(this, GenericViewModelFactory {
            RetrofitClient.getRetrofitInstance(this)?.create(CurrencyAPI::class.java)?.let {
                CurrencyViewModel(it)
            }
        }).get(CurrencyViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadingDialog = this.getLoadingDialog(0, R.string.loading_please_wait, theme = R.style.AlertDialogCustom)

        languageViewModel.getLanguageList()
        currencyViewModel.getCurrencyList()

        languageViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()

                    languageViewModel.response = response.data as ArrayList<HashMap<String, String>>

                    for (i in languageViewModel.response) {
                        // println(i.entries.forEach())}

                        for (key in i.keys) {
                            println("Element at key $key : ${i[key]}")

                            i[key]?.let { languageList.add(it) }
                        }

                        println(languageList)

                        val adapter = ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,languageList)
                        input_settings_language.setAdapter(adapter)
                        input_settings_language.threshold = 1
                        //input_settings_language.setDropDownBackgroundResource((R.color.dropDownColor))

                        input_settings_language.onItemClickListener = AdapterView.OnItemClickListener{
                            parent,view,position,id->
                            val selectedItem = parent.getItemAtPosition(position).toString()

                            // Display the clicked item using toast
                            //Toast.makeText(this,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
                        }

                        input_settings_language.setOnDismissListener {
                            //Toast.makeText(activity,"Suggestion closed.",Toast.LENGTH_SHORT).show()

                            //natureOfTheCostTextField.hint = null
                        }

                        input_settings_language.onFocusChangeListener = View.OnFocusChangeListener{
                            view, b ->
                            if(b){
                                // Display the suggestion dropdown on focus
                                input_settings_language.showDropDown()
                                //input_settings_currency.setBackgroundColor(resources.getColor(R.color.dropDownColor))
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        currencyViewModel.responseLiveData.observe(this, Observer { response ->

            when (response.status) {
                Status.LOADING -> {
                    showLoader()
                }

                Status.SUCCESS -> {
                    hideLoader()

                    currencyViewModel.response = response.data as ArrayList<String>
                    currencyList = response.data

                    val adapter = ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,currencyList)
                    input_settings_currency.setAdapter(adapter)
                    input_settings_currency.threshold = 1
                    //input_settings_currency.setDropDownBackgroundResource((R.color.dropDownColor))

                    input_settings_currency.onItemClickListener = AdapterView.OnItemClickListener{
                        parent,view,position,id->
                        val selectedItem = parent.getItemAtPosition(position).toString()

                        // Display the clicked item using toast
                        //Toast.makeText(activity,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
                    }

                    input_settings_currency.setOnDismissListener {
                        //Toast.makeText(activity,"Suggestion closed.",Toast.LENGTH_SHORT).show()

                        //natureOfTheCostTextField.hint = null
                    }

                    input_settings_currency.onFocusChangeListener = View.OnFocusChangeListener{
                        view, b ->
                        if(b){
                            // Display the suggestion dropdown on focus
                            input_settings_currency.showDropDown()
                            //input_settings_currency.setBackgroundColor(resources.getColor(R.color.dropDownColor))
                        }
                    }

                    //println(currencyList)
                    //println(currencyViewModel.response)
                }

                Status.ERROR -> {
                    hideLoader()
                    showMessage(response.error?.message.toString())
                }
            }
        })

        println(currencyList)

        //val tempList: ArrayList<String> = arrayListOf("US", "ENG", "GUL")
        //val adapter = { ArrayAdapter<String>(it, android.R.layout.simple_list_item_1, nature_of_the_cost) }

        img_settings_close.setOnClickListener {

            intent = Intent(this, HomePlanPreview :: class.java)
            startActivity(intent)
            finish()
        }

        rl_settings_account.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Account :: class.java)
            startActivity(intent)
        })
        rl_settings_contacts.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Contact :: class.java)
            startActivity(intent)
        })
        rl_settings_followup.setOnClickListener(View.OnClickListener {
            intent = Intent(this, Followups :: class.java)
            startActivity(intent)
        })
        rl_settings_logout.setOnClickListener(View.OnClickListener {
            //RetrofitClient.cookieJar?.clear()
            getSharedPreferences(SplashActivity.PREF_NAME, Context.MODE_PRIVATE).edit().clear().apply()
            getSharedPreferences(RetrofitClient.USER_TOKEN_PREF, Context.MODE_PRIVATE).edit().clear().apply()
            intent = Intent(this, SplashActivity :: class.java)
            startActivity(intent)
            finishAffinity()
        })
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = getCurrentFocus()
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm?.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
