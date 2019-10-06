package net.lmlab.m_tsunami_android.ui.alerts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.lmlab.m_tsunami_android.R
import java.util.*

class AlertsFragment : Fragment() {

    companion object {
        val BASE_URL = "https://www.data.jma.go.jp/"
    }

    private lateinit var alertsViewModel: AlertsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertsViewModel =
            ViewModelProviders.of(this).get(AlertsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alerts, container, false)

        val listView = root.findViewById(R.id.list_alerts) as ListView
        alertsViewModel.titles.observe(this, Observer {
            Log.d("hoge", it.toString())

            val adapter = ArrayAdapter<String>(requireContext(),
                R.layout.list_alert_row,
                R.id.text_alert,
                it)

            listView.adapter = adapter

            listView.setOnItemClickListener { parente, view, position, id ->
                val timezone = adapter.getItem(position)
                Toast.makeText(requireContext(), timezone, Toast.LENGTH_SHORT).show()
            }
        })

        alertsViewModel.fetchAlertsFeed()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}