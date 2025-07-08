package tech.jaya.ridely.client

import org.springframework.stereotype.Component
import tech.jaya.ridely.dto.RouteInfo

@Component
class GoogleMapsClient {
    fun getRouteInfo(pickUp: String, dropOff: String): RouteInfo {
        return RouteInfo(
            estimatedTimeMinute = 15, // Simulated value
            distanceKm = 10.0 // Simulated value
        )

    }
}