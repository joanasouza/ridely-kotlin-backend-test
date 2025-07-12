package tech.jaya.ridely.usecase
import org.springframework.stereotype.Component
import tech.jaya.ridely.dto.RequestRideEstimate
import tech.jaya.ridely.dto.RideEstimateResponseDTO

import tech.jaya.ridely.client.GoogleMapsClient
import tech.jaya.ridely.repository.RideRepo
import tech.jaya.ridely.service.RideService

@Component
class EstimateRideUseCase() {
    private lateinit var  rideRepo: RideRepo
    private lateinit var googleMapsClient: GoogleMapsClient
    val rideService = RideService()

    fun extractRideInfo(request: RequestRideEstimate): RideEstimateResponseDTO {
        val routeInfo = googleMapsClient.getRouteInfo(request.pickUp, request.dropOff)
        val estimatedTime = routeInfo.estimatedTimeMinute
        val distance = routeInfo.distanceKm
        val price = rideService.calculatePrice(distance, estimatedTime)

        return RideEstimateResponseDTO(
            estimatedTimeMinute = estimatedTime,
            distanceKm = distance,
            price = price
        )
    }

}