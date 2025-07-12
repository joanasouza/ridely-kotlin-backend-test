package tech.jaya.ridely.dto

data class GoogleMapsResponse(
    val routes: List<Route>,
    val status: String
)

data class Route(val legs: List<Leg>)

data class Leg(val distance: Distance, val duration: Duration)

data class Distance(val text: String, val value: Int)

data class Duration(val text: String, val value: Int)