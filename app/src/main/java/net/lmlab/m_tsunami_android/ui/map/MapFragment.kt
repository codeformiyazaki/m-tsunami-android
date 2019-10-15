package net.lmlab.m_tsunami_android.ui.map

import android.Manifest
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.PermissionChecker.checkPermission
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onMapReady(googleMap: GoogleMap?) {
        if (googleMap != null) {
            this.googleMap = googleMap
            doCheckPermission()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(net.lmlab.m_tsunami_android.R.layout.fragment_map, container, false)

        MapsInitializer.initialize(this.activity)
        mapView = rootView.findViewById(net.lmlab.m_tsunami_android.R.id.mapView)
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

    private fun doCheckPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 10) //REQUEST_LOCATION
        } else {
            Log.e("DB", "PERMISSION GRANTED")
            // この５行を実行するとクラッシュ
//            googleMap.isMyLocationEnabled = true
//            googleMap.uiSettings.isMapToolbarEnabled = true
//            val osakaStation = googleMap.getMyLocation()
//            val myLatLng = LatLng(osakaStation.getLatitude(), osakaStation.getLongitude())
//            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 16.0f))

            val sydney = LatLng(-34.0, 151.0)
            googleMap.addMarker(
                MarkerOptions()
                    .position(sydney)             // 地図上のマーカーの位置
                    .title("Marker in Sydney")    // マーカーをタップ時に表示するテキスト文字列
                    .snippet("Australian cities") // タイトルの下に表示される追加のテキスト
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)) // アイコン
            )
        }
    }

    //許可を求めるダイアログで何らかのボタンが押されたときに発生するイベント
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("DB", "onRequestPermissionsResult")
    }
}