package tech.jaya.ridely.dto

class RideEstimateResponseDTO(
    val distanceKm: Int,
    val estimatedTimeMinute: Int,
    val price: Double
)

data class RouteInfo(
    val estimatedTimeMinute: Int,
    val distanceKm: Int
)