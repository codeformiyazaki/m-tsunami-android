package net.lmlab.m_tsunami_android.ui.info

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.lmlab.m_tsunami_android.BuildConfig

class InfoViewModel:ViewModel() {
    private val _linkText = MutableLiveData<String>().apply {
        value = "Find us on GitHub"
    }
    val linkText: LiveData<String> = _linkText

    private val _versionText = MutableLiveData<String>().apply {
        value = "version " + BuildConfig.VERSION_NAME
    }
    val versionText: LiveData<String> = _versionText
}