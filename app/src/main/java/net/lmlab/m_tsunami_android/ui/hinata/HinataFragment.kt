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

        val url = "https://kenzkenz.xsrv.jp/aaa/#13/131.44455/31.92127%3FS%3D1%26L%3D%5B%5B%7B%22id%22%3A%22shinsuishin%22%2C%22o%22%3A1%7D%2C%7B%22id%22%3A1%2C%22o%22%3A1%7D%5D%2C%5B%7B%22id%22%3A2%2C%22o%22%3A1%2C%22c%22%3A%22%22%7D%5D%2C%5B%7B%22id%22%3A4%2C%22o%22%3A1%2C%22c%22%3A%22%22%7D%5D%2C%5B%7B%22id%22%3A5%2C%22o%22%3A1%2C%22c%22%3A%22%22%7D%5D%5D"
        val view = rootView.findViewById(net.lmlab.m_tsunami_android.R.id.webView) as WebView
        view.settings.javaScriptEnabled = true
        view.loadUrl(url)

        return rootView
    }
}