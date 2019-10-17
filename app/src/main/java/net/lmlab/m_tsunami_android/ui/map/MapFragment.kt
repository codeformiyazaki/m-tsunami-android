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
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.*
import net.lmlab.m_tsunami_android.R

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var viewModel: MapViewModel

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
        viewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

        MapsInitializer.initialize(this.activity)
        mapView = rootView.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this@MapFragment)

        viewModel.location.observe(this, Observer {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude
                if (latitude != 0.0 && longitude != 0.0) {
                    val latLng = LatLng(latitude, longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
                }
            }
        })

        viewModel.buildings.observe(this, Observer {
            it.forEach{
                val latLng = LatLng(it.lat, it.lng)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16.0f))
                val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_building)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title("Marker in Sydney")
                        .snippet("Australian cities")
                        .icon(icon)
                )
            }
        })

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

            viewModel.getLastLocation()
            viewModel.loadBuildings()
        }
    }
}