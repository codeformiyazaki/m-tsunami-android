package net.lmlab.m_tsunami_android.ui.map

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.google.android.gms.location.LocationServices
import net.lmlab.m_tsunami_android.entity.Building

class MapViewModel(app: Application): AndroidViewModel(app) {

    val location: MutableLiveData<Location> = MutableLiveData()
    val buildings: MutableLiveData<List<Building>> = MutableLiveData()

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
}