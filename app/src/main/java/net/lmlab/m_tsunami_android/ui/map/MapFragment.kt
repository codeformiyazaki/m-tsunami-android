package net.lmlab.m_tsunami_android.ui.map

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.google.android.gms.maps.model.*
import com.google.maps.android.PolyUtil
import net.lmlab.m_tsunami_android.R
import net.lmlab.m_tsunami_android.entity.Route
import net.lmlab.m_tsunami_android.ui.WebViewActivity

class MapFragment : Fragment(), OnMapReadyCallback {

    companion object {
        val REQUEST_LOCATION = 10
    }

    private lateinit var viewModel: MapViewModel

    private var routePostions = ArrayList<LatLng>()
    private lateinit var routePolylines: Polyline

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            this.googleMap = googleMap
            this.googleMap.setOnMarkerClickListener { marker : Marker ->
                if (marker.snippet.startsWith("https")) {
                    val intent = Intent(activity, WebViewActivity::class.java)
                    intent.putExtra("url", marker.snippet)
                    activity?.startActivity(intent)
                    return@setOnMarkerClickListener false
                }
                clearMarkersAndRoute()
                val origin = googleMap.myLocation.latitude.toString() + "," + googleMap.myLocation.longitude.toString()
                val destination = marker.position.latitude.toString() + "," + marker.position.longitude.toString()
                viewModel.getDirections(origin, destination)
                return@setOnMarkerClickListener false
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

        observe()

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

    // 許可を求めるダイアログで何らかのボタンが押されたときに発生するイベント
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != REQUEST_LOCATION) { return }

        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(context, R.string.location_permission_denied, Toast.LENGTH_LONG).show()
            return
        }

        loadData()
    }

    private fun observe() {
        viewModel.location.observe(this, Observer {
            if (it != null) {
                val latitude = it.latitude
                val longitude = it.longitude
                if (latitude != 0.0 && longitude != 0.0) {
                    val latLng = LatLng(latitude, longitude)
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f))
                }
            }
        })

        viewModel.buildings.observe(this, Observer {
            it.forEach{
                val latLng = LatLng(it.lat, it.lng)
                val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_building)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(it.name)
                        .snippet("標高" + it.altitude.toString() + "m " + it.structure + " " + it.floor + "階")
                        .icon(icon)
                )
            }
        })

        viewModel.toilets.observe(this, Observer {
            it.forEach{
                val latLng = LatLng(it.lat, it.lng)
                val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_toilet)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(it.name)
                        .snippet("トイレ" + it.count.toString() + "個")
                        .icon(icon)
                )
            }
        })

        viewModel.webcams.observe(this, Observer {
            it.forEach{
                val latLng = LatLng(it.lat, it.lng)
                val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_webcam)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(it.name)
                        .snippet(it.url)
                        .icon(icon)
                )
            }
        })

        viewModel.shelters.observe(this, Observer {
            it.forEach{
                val latLng = LatLng(it.lat, it.lng)
                val icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_shelter)
                googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(it.name)
                        .snippet(it.address)
                        .icon(icon)
                )
            }
        })

        viewModel.route.observe(this, Observer {
            setMarkersAndRoute(it)
        })
    }

    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        } else {
            loadData()
        }
    }

    private fun loadData() {
        // 許可済でないとクラッシュする
        googleMap.isMyLocationEnabled = true

        viewModel.getLastLocation()
        viewModel.loadBuildings()
        viewModel.loadToilets()
        viewModel.loadWebcams()
        viewModel.loadShelters()
    }

    private fun setMarkersAndRoute(route: Route) {
        val startLatLng = LatLng(route.startLat!!, route.startLng!!)
        val endLatLng = LatLng(route.endLat!!, route.endLng!!)
        routePostions.add(startLatLng)
        routePostions.add(endLatLng)

        val polylineOptions = PolylineOptions().color(Color.BLUE)
        val pointsList = PolyUtil.decode(route.overviewPolyline)
        for (point in pointsList) {
            polylineOptions.add(point)
        }

        routePolylines = googleMap.addPolyline(polylineOptions)

        googleMap.animateCamera(viewModel.autoZoomLevel(routePostions))
    }

    private fun clearMarkersAndRoute() {
        routePostions.clear()
        if (::routePolylines.isInitialized) {
            routePolylines.remove()
        }
    }
}