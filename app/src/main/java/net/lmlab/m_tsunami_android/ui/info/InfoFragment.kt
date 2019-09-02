package net.lmlab.m_tsunami_android.ui.info

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import net.lmlab.m_tsunami_android.R
import java.util.regex.Pattern

class InfoFragment : Fragment() {

    private lateinit var infoViewModel: InfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        infoViewModel =
            ViewModelProviders.of(this).get(InfoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        infoViewModel.text.observe(this, Observer {

            textView.text = it
            val githubUrl = "https://github.com/codeformiyazaki/m-tsunami-android"
            val pattern = Pattern.compile("GitHub")
            val filter = Linkify.TransformFilter { _, _ -> githubUrl }
            Linkify.addLinks(textView, pattern, githubUrl, null, filter)
        })
        return root
    }
}