package net.lmlab.m_tsunami_android.ui.alerts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.lmlab.m_tsunami_android.R
import net.lmlab.m_tsunami_android.ui.alerts.detail.AlertDetailActivity

class AlertsFragment : Fragment() {

    private lateinit var alertsViewModel: AlertsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        alertsViewModel = ViewModelProviders.of(this).get(AlertsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_alerts, container, false)

        val listView = root.findViewById(R.id.list_alerts) as ListView
        alertsViewModel.entities.observe(this, Observer {
            val adapter = AlertsListAdapter(requireContext(), it)
            listView.adapter = adapter
            listView.setOnItemClickListener { _, _, position, _ ->
                val entry = adapter.getItem(position)
                val intent = Intent(activity, AlertDetailActivity::class.java)
                intent.putExtra("uuid", entry.id)
                activity?.startActivity(intent)
            }
        })

        alertsViewModel.fetchAlertsFeed()

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}