package net.lmlab.m_tsunami_android.client

import net.lmlab.m_tsunami_android.entity.ReportEntity
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface AlertDetailFeedClient {
    @GET("developer/xml/data/{id}.xml")
    fun getReport(@Path("id") id: String): Call<ReportEntity>
}