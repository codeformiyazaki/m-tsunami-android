package net.lmlab.m_tsunami_android.client

import net.lmlab.m_tsunami_android.entity.Directions
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleDirectionClient {
    @GET("directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") key: String
    ): Call<Directions>
}
