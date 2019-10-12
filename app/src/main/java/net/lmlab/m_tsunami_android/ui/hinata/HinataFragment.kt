package net.lmlab.m_tsunami_android.ui.hinata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.webkit.WebView

class HinataFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(net.lmlab.m_tsunami_android.R.layout.fragment_hinata, container, false)

        val url = "https://www.data.jma.go.jp/developer/xml/data/1cf283d4-ffbc-31ea-a94e-432ba5cbf57e.xml"
        val view = rootView.findViewById(net.lmlab.m_tsunami_android.R.id.webView) as WebView
        view.settings.javaScriptEnabled = true
        view.loadUrl(url)

        return rootView
    }
}