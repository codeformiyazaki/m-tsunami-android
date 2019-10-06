package net.lmlab.m_tsunami_android.client

import net.lmlab.m_tsunami_android.entity.FeedEntity
import retrofit2.Call
import retrofit2.http.GET

interface AlertsFeedClient {
    @GET("developer/xml/feed/eqvol.xml")
    fun getEntry(): Call<FeedEntity>
}

