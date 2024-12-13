package com.example.sehatyou

import android.app.Application
import com.example.sehatyou.utils.SuggestionFetcher

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        SuggestionFetcher.initialize(this)
    }
}