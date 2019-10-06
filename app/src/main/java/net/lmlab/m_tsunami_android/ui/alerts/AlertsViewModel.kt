package net.lmlab.m_tsunami_android.ui.alerts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.lmlab.m_tsunami_android.client.AlertsFeedClient
import net.lmlab.m_tsunami_android.entity.FeedEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class AlertsViewModel : ViewModel() {

    val titles: MutableLiveData<List<String>> = MutableLiveData()

    fun fetchAlertsFeed() {
        val retrofit = Retrofit.Builder()
            .baseUrl(AlertsFragment.BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val client = retrofit.create(AlertsFeedClient::class.java)
        val call: Call<FeedEntity> = client.getEntry()
        call.enqueue(object : Callback<FeedEntity> {
            override fun onResponse(call: Call<FeedEntity>?, response: Response<FeedEntity>?) {
                val result = response?.body()
                // todo: Log は消そうぜ
                result?.entities?.forEach {
                    Log.d("hoge", it.content)
                }
                val titleList = result?.entities?.map { it.content } as List<String>
                titles.postValue(titleList)
            }

            override fun onFailure(call: Call<FeedEntity>?, t: Throwable?) {
                Log.d("hoge", "aaa")
            }
        })
    }
}