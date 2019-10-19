package net.lmlab.m_tsunami_android.ui.map

import android.app.Application
import android.location.Location
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import net.lmlab.m_tsunami_android.Constants
import net.lmlab.m_tsunami_android.R
import net.lmlab.m_tsunami_android.client.GoogleDirectionClient
import net.lmlab.m_tsunami_android.entity.*
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapViewModel(app: Application): AndroidViewModel(app) {

    companion object {
        val BASE_URL = "https://maps.googleapis.com/maps/api/"
    }

    val location: MutableLiveData<Location> = MutableLiveData()
    val buildings: MutableLiveData<List<Building>> = MutableLiveData()
    val toilets: MutableLiveData<List<Toilet>> = MutableLiveData()
    val webcams: MutableLiveData<List<Webcam>> = MutableLiveData()
    val route: MutableLiveData<Route> = MutableLiveData()

    fun getLastLocation() {
        val context = getApplication<Application>().applicationContext
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        locationClient.lastLocation.addOnSuccessListener { location ->
            this.location.postValue(location)
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }

    fun loadBuildings() {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.assets.open("csv/buildings_locations.csv")
        val rows: List<List<String>> = csvReader().readAll(inputStream)
        val buildingList = rows.map { Building(it[0], it[1], it[2].toDouble(), it[3], it[4], it[5].toDouble(), it[6].toDouble()) }
        buildings.postValue(buildingList)
    }

    fun loadToilets() {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.assets.open("csv/toilets_locations.csv")
        val rows: List<List<String>> = csvReader().readAll(inputStream)
        val toiletList = rows.map { Toilet(it[0], it[1].toInt(), it[2].toDouble(), it[3].toDouble()) }
        toilets.postValue(toiletList)
    }

    fun loadWebcams() {
        val context = getApplication<Application>().applicationContext
        val inputStream = context.assets.open("csv/webcams_locations.csv")
        val rows: List<List<String>> = csvReader().readAll(inputStream)
        val webcamList = rows.map { Webcam(it[0], it[1], it[2].toDouble(), it[3].toDouble()) }
        webcams.postValue(webcamList)
    }

    fun getDirections(origin: String, destination: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient().newBuilder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val client = retrofit.create(GoogleDirectionClient::class.java)
        val call: Call<Directions> =
            client.getDirections(origin, destination, Constants.GOOGLE_API_KEY)
        call.enqueue(object : Callback<Directions> {
            override fun onResponse(call: Call<Directions>, response: Response<Directions>) {
                val directions = response.body()!!
                if (directions.status.equals("OK")) {
                    val legs = directions.routes[0].legs[0]
                    route.postValue(Route(
                        legs.startLocation.lat,
                        legs.startLocation.lng,
                        legs.endLocation.lat,
                        legs.endLocation.lng,
                        directions.routes[0].overviewPolyline.points
                    ))
                } else {
                    val context = getApplication<Application>().applicationContext
                    Toast.makeText(context, R.string.route_failed, Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Directions>, t: Throwable) {
                val context = getApplication<Application>().applicationContext
                Toast.makeText(context, R.string.route_failed, Toast.LENGTH_LONG).show()
            }
        })
    }

    fun autoZoomLevel(positionList: List<LatLng>): CameraUpdate {
        if (positionList.size == 1) {
            val latitude = positionList[0].latitude
            val longitude = positionList[0].longitude
            val latLng = LatLng(latitude, longitude)

            return CameraUpdateFactory.newLatLngZoom(latLng, 13f)
        } else {
            val builder = LatLngBounds.Builder()
            for (position in positionList) {
                builder.include(position)
            }
            val bounds = builder.build()

            val padding = 200 // offset from edges of the map in pixels

            return CameraUpdateFactory.newLatLngBounds(bounds, padding)
        }
    }
}