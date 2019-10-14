package net.lmlab.m_tsunami_android.ui.alerts

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.lmlab.m_tsunami_android.R
import net.lmlab.m_tsunami_android.entity.EntryEntity

class AlertsListAdapter(context: Context, entries: List<EntryEntity>) : ArrayAdapter<EntryEntity>(context, 0, entries) {
    private val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view = convertView
        var holder: AlertsViewHolder

        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_alert_row, parent, false)
            holder = AlertsViewHolder(
                view.findViewById(R.id.icon_view),
                view.findViewById(R.id.alert_text)
            )
            view.tag = holder
        } else {
            holder = view.tag as AlertsViewHolder
        }

        val entry = getItem(position) as EntryEntity
        if (entry.title.startsWith("噴火")) {
            holder.iconView?.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.ic_volcano))
        } else if (entry.title.startsWith("震源")) {
            holder.iconView?.setImageBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.ic_earthquake))
        }
        holder.alertTextView?.text = entry.content

        return view
    }
}
