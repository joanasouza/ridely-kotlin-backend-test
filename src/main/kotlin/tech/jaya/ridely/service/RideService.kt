package tech.jaya.ridely.service

import org.springframework.stereotype.Service
import tech.jaya.ridely.client.GoogleMapsClient
import tech.jaya.ridely.dto.RequestRideEstimate
import tech.jaya.ridely.dto.RideEstimateDTO

@Service
class RideService( private val googleMapsClient: GoogleMapsClient) {
    private fun calculatePrice(distance: Double, estimatedTime: Int): Double {
        val pricePerKm = 1.0
        val pricePerMinute = 0.5
        val appTax = 1.01
        val total = (distance * pricePerKm) + (estimatedTime * pricePerMinute)

        return total + appTax
    }

    fun estimateRide(request: RequestRideEstimate): RideEstimateDTO {
        val routeInfo = googleMapsClient.getRouteInfo(request.pickUp, request.dropOff)
        val estimatedTime = routeInfo.estimatedTimeMinute
        val distance = routeInfo.distanceKm
        val price = calculatePrice(distance, estimatedTime)

        return RideEstimateDTO(
            estimatedTime = estimatedTime,
            distanceKm = distance,
            price = price
        )
    }
}