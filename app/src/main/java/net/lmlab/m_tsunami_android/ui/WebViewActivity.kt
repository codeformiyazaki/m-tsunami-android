package net.lmlab.m_tsunami_android.ui

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import net.lmlab.m_tsunami_android.R

class WebViewActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val url = intent.getStringExtra("url")
        val view = findViewById(R.id.webView) as WebView
        view.settings.javaScriptEnabled = true
        view.loadUrl(url)
    }
}