package tech.jaya.ridely.service

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import tech.jaya.ridely.client.GoogleMapsClient
import tech.jaya.ridely.dto.RequestRideEstimate
import tech.jaya.ridely.dto.RouteInfo
import tech.jaya.ridely.usecase.EstimateRideUseCase


@ActiveProfiles("test")
@SpringBootTest
class RideServiceTest {
    @Test
    fun shouldEstimateRideCorrectly() {
        val googleMapsClient = mockk<GoogleMapsClient>()
        val estimateRideUseCase = mockk<EstimateRideUseCase>()

        val request = RequestRideEstimate(
            pickUp = "Pituba Ville", dropOff = "Salvador Shopping",
        )
        every {
            googleMapsClient.getRouteInfo(any(), any())
        } returns RouteInfo(
            estimatedTimeMinute = 10,
            distanceKm = 10
        )
        val estimatedRide = estimateRideUseCase.extractRideInfo(request)

        assertEquals(10, estimatedRide.estimatedTimeMinute)
        assertEquals(10, estimatedRide.distanceKm)
        assertEquals(16.01, estimatedRide.price)

    }
}
