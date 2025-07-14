package tech.jaya.ridely.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.kotlin.whenever
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import tech.jaya.ridely.service.RideService
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import tech.jaya.ridely.client.GoogleMapsClient
import tech.jaya.ridely.dto.RequestRideEstimate
import tech.jaya.ridely.dto.RideEstimateResponseDTO
import tech.jaya.ridely.repository.DriverRepo
import tech.jaya.ridely.repository.RideRepo

@WebMvcTest(RideController::class)
class RideControllerTest {
    @MockBean
    private lateinit var rideService: RideService

    @MockBean
    private lateinit var rideRepo: RideRepo

    @MockBean
    private lateinit var driverRepo: DriverRepo

    @MockBean
    private lateinit var mockGoogleMapsClient: GoogleMapsClient

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `should return 400 when pickup is empty`() {
        val request = """
            {
                "pickUp": "",
                "dropOff": "Salvador Shopping"
            }
        """
        mockMvc.post("/rides/estimate") {
            contentType = MediaType.APPLICATION_JSON
            content = request
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.errors[0]").value("Pickup or dropOff cannot be blank") }
        }

    @Test
    fun `should return route info when valid locations are provided`() {
        // Arrange
        val request = RequestRideEstimate(
            pickUp = "Salvador Shopping",
            dropOff = "Cores de Piata"
        )
        val expectedResponse = RideEstimateResponseDTO(
            distanceKm = 13,
            estimatedTimeMinute = 16,
             10.0
        )

        whenever(rideService.estimateRide(request)).thenReturn(expectedResponse)

        mockMvc.post("/rides/estimate") {
            contentType = MediaType.APPLICATION_JSON
            content = ObjectMapper().writeValueAsString(request)
        }.andExpect {
//            status{ isOk() }
            jsonPath("$.distanceKm").value(expectedResponse.distanceKm)
            jsonPath("$.estimatedTimeMinute").value(expectedResponse.estimatedTimeMinute)
            jsonPath("$.price").value(expectedResponse.price)
        }
//        verify { rideService.estimateRide(request) }
    }
    }