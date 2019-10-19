package net.lmlab.m_tsunami_android.entity

data class Route(val startLat: Double?,
                 val startLng: Double?,
                 val endLat: Double?,
                 val endLng: Double?,
                 val overviewPolyline: String = "")