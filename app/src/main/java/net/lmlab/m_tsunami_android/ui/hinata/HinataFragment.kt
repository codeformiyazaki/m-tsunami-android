package net.lmlab.m_tsunami_android.ui.hinata

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import net.lmlab.m_tsunami_android.Constants.HINATA_URL
import net.lmlab.m_tsunami_android.R

class HinataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_hinata, container, false)

        val progressBar = rootView.findViewById(R.id.progressBar) as ProgressBar

        val webView = rootView.findViewById(R.id.webView) as WebView
        webView.webViewClient = object: WebViewClient() {

            // ローディング開始時に呼ばれる
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
            }

            // ローディング終了時に呼ばれる
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
        }

        webView.settings.javaScriptEnabled = true
        webView.loadUrl(HINATA_URL)

        return rootView
    }
}