package tech.jaya.ridely.service

import org.springframework.stereotype.Service
import tech.jaya.ridely.dto.RequestRideEstimate
import tech.jaya.ridely.dto.RideEstimateResponseDTO
import tech.jaya.ridely.usecase.EstimateRideUseCase

@Service
class RideService() {
    private lateinit var estimateRideUseCase: EstimateRideUseCase

     fun calculatePrice(distance: Int, estimatedTime: Int): Double {
        val pricePerKm = 1
        val pricePerMinute = 0.5
        val appTax = 1.01
        val total = (distance * pricePerKm) + (estimatedTime * pricePerMinute)

        return total + appTax
    }

    fun estimate(req: RequestRideEstimate): RideEstimateResponseDTO {
        val responseRide = estimateRideUseCase.extractRideInfo(req)
        return responseRide
    }


}