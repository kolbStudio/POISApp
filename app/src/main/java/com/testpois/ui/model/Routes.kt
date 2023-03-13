package com.testpois.ui.model

sealed class Routes(val route: String) {
    object PoiScreen : Routes("poi_screen")
    object PoiDetailScreen : Routes("poi_detail_screen/{title}/{geocoordinates}/{image}") {
        fun createRoute(title: String, geocoordinates: String, image: String) =
            "poi_detail_screen/$title/$geocoordinates/$image"
    }
}

