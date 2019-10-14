package net.lmlab.m_tsunami_android.ui.alerts.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.lmlab.m_tsunami_android.client.AlertDetailFeedClient
import net.lmlab.m_tsunami_android.entity.HeadlineEntity
import net.lmlab.m_tsunami_android.entity.ReportEntity
import net.lmlab.m_tsunami_android.ui.alerts.AlertsViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class AlertDetailViewModel: ViewModel() {

    val headline: MutableLiveData<HeadlineEntity> = MutableLiveData()

    fun fetchAlertsDetail(uuid: String) {
        val id = uuid.replace("^urn:uuid:".toRegex(), "")

        val retrofit = Retrofit.Builder()
            .baseUrl(AlertsViewModel.BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val client = retrofit.create(AlertDetailFeedClient::class.java)
        val call: Call<ReportEntity> = client.getReport(id)
        call.enqueue(object : Callback<ReportEntity> {
            override fun onResponse(call: Call<ReportEntity>?, response: Response<ReportEntity>?) {
                val result = response?.body()
                headline.postValue(result?.head?.headline)
            }

            override fun onFailure(call: Call<ReportEntity>?, t: Throwable?) {
                Log.d("m_tsunami_android", t.toString())
                headline.postValue(null)
            }
        })
    }
}