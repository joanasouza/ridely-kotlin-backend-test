package tech.jaya.ridely.dto

class RideEstimateDTO(
    val distanceKm: Double,
    val estimatedTime: Int,
    val price: Double
)

data class RouteInfo(
    val estimatedTimeMinute: Int,
    val distanceKm: Double
)