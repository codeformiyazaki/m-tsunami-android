package net.lmlab.m_tsunami_android.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.gms.location.LocationServices.getFusedLocationProviderClient
import com.google.android.gms.maps.model.*
import net.lmlab.m_tsunami_android.R

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            this.googleMap = googleMap
            this.googleMap.setOnMarkerClickListener { marker : Marker ->
                Log.d("m_tsunami_android", "setOnMarkerClickListener")
                return@setOnMarkerClickListener true
            }
            checkPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        MapsInitializer.initialize(this.activity)
        mapView = rootView.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MapFragment)

        return rootView
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    // todo: なぜか呼ばれない
    // 許可を求めるダイアログで何らかのボタンが押されたときに発生するイベント
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.d("m_tsunami_android", "onRequestPermissionsResult")
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10) //REQUEST_LOCATION
        } else {
            // 許可済でないとクラッシュする
            googleMap.isMyLocationEnabled = true
            googleMap.uiSettings.isMapToolbarEnabled = true

            getLastLocation()
        }
    }

    fun getLastLocation() {
        val locationClient = getFusedLocationProviderClient(requireActivity())
        locationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val latitude = location.latitude
                val longitude = location.longitude
                if (latitude != 0.0 && longitude != 0.0) {
                    Log.d("m_tsunami_android", latitude.toString() + " " + longitude.toString())
                    val myLatLng = LatLng(latitude, longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 16.0f))
                    val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_building)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(myLatLng)           // 地図上のマーカーの位置
                            .title("Marker in Sydney")    // マーカーをタップ時に表示するテキスト文字列
                            .snippet("Australian cities") // タイトルの下に表示される追加のテキスト
                            .icon(icon)
                    )
                }
            }
        }.addOnFailureListener { e ->
            e.printStackTrace()
        }
    }
}