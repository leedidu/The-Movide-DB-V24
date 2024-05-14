package com.ltu.m7019e.moviedb.v24.utils

import android.content.Context

object PreferencesManager {
    private const val PREFS_NAME = "MovieTabPrefs"
    private const val SELECTED_TAB_INDEX_KEY = "SelectedTabIndex"

    fun saveSelectedTabIndex(context: Context, tabIndex: Int) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(SELECTED_TAB_INDEX_KEY, tabIndex).apply()
    }

    fun getSelectedTabIndex(context: Context): Int {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(SELECTED_TAB_INDEX_KEY, 0) // Default to 0
    }
}
