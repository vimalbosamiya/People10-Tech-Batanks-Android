package com.batanks.nextplan.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import java.util.*

public class LocaleHelper {

    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

    fun onAttach(context: Context):Context {
        val lang = getPersistedData(context, Locale.getDefault().getLanguage())
        return setLocale(context, lang)
    }
    public fun onAttach(context:Context, defaultLanguage:String):Context {
        val lang = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context:Context):String {
        return getPersistedData(context, Locale.getDefault().getLanguage())
    }

    fun setLocale(context:Context, language:String):Context {
        persist(context, language)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            return updateResources(context, language)
        }
        return updateResourcesLegacy(context, language)
    }

    private fun getPersistedData(context:Context, defaultLanguage:String):String {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)!!
    }

    private fun persist(context:Context, language:String) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putString(SELECTED_LANGUAGE, language)
        editor.apply()
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context:Context, language:String):Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context.getResources().getConfiguration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context:Context, language:String):Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.getResources()
        val configuration = resources.getConfiguration()
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
        {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.getDisplayMetrics())
        return context
    }
}