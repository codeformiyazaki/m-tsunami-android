package net.lmlab.m_tsunami_android.ui.alerts.detail

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import net.lmlab.m_tsunami_android.R

class AlertDetailActivity : FragmentActivity() {

    private lateinit var viewModel: AlertDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alert_detail)

        viewModel = ViewModelProviders.of(this).get(AlertDetailViewModel::class.java)

        val textView: TextView = findViewById(R.id.text_notifications)
        textView.setMovementMethod(ScrollingMovementMethod())

        viewModel.headline.observe(this, Observer {
            if (it == null) {
                textView.text = "詳細情報はありません"
                return@Observer
            }
            var text = it.text + "\n\n"
            it.informations?.forEach {
                text += "【" + it.type + "】\n\n"
                it.items?.forEach {
                    text += "■" + it.kind.name + "\n"
                    it.areas?.areas?.forEach {
                        text += it.name + "\n"
                    }
                    text += "\n"
                }
                text += "\n"
            }
            textView.text = text
        })

        viewModel.fetchAlertsDetail(intent.getStringExtra("uuid"))
    }
}
