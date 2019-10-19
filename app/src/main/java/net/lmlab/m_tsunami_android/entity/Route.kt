package net.lmlab.m_tsunami_android.entity

data class Route(val startName: String = "",
                 val endName: String = "",
                 val startLat: Double?,
                 val startLng: Double?,
                 val endLat: Double?,
                 val endLng: Double?,
                 val overviewPolyline: String = "")