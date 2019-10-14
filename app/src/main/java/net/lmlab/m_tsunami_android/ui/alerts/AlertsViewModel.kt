package net.lmlab.m_tsunami_android.ui.alerts

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.lmlab.m_tsunami_android.client.AlertsFeedClient
import net.lmlab.m_tsunami_android.entity.EntryEntity
import net.lmlab.m_tsunami_android.entity.FeedEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class AlertsViewModel : ViewModel() {

    companion object {
        val BASE_URL = "https://www.data.jma.go.jp/"
    }

    val entities: MutableLiveData<List<EntryEntity>> = MutableLiveData()

    fun fetchAlertsFeed() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(SimpleXmlConverterFactory.create())
            .build()

        val client = retrofit.create(AlertsFeedClient::class.java)
        val call: Call<FeedEntity> = client.getEntry()
        call.enqueue(object : Callback<FeedEntity> {
            override fun onResponse(call: Call<FeedEntity>?, response: Response<FeedEntity>?) {
                val result = response?.body()
                val filteredEntries = result?.entities?.filter { it.title.startsWith("噴火") || it.title.startsWith("震源") }
                entities.postValue(filteredEntries)
            }

            override fun onFailure(call: Call<FeedEntity>?, t: Throwable?) {
                Log.d("m_tsunami_android", t.toString())
            }
        })
    }
}