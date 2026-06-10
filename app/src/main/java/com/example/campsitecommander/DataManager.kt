package com.example.campsitecommander

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import androidx.core.content.edit

object DataManager {
    private const val PREFS_NAME = "journey_keeper_prefs"
    private const val KEY_ITEMS = "items_list"

    fun saveItem(context: Context, item: Item) {
        val items = getItems(context).toMutableList()
        items.add(item)
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = Gson().toJson(items)
        prefs.edit { putString(KEY_ITEMS, json) }
    }

    fun getItems(context: Context): List<Item> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_ITEMS, null) ?: return emptyList()
        val type = object : TypeToken<List<Item>>() {}.type
        return Gson().fromJson(json, type)
    }

    fun clearItems(context: Context) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit { remove(KEY_ITEMS) }
    }
}