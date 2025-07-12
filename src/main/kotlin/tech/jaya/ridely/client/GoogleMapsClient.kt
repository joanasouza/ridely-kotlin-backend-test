package tech.jaya.ridely.client

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import tech.jaya.ridely.dto.GoogleMapsResponse
import tech.jaya.ridely.dto.RouteInfo
import tech.jaya.ridely.exception.GoogleMapsException

@Component
class GoogleMapsClient(
    @Value("\${google.maps.api.key}")
    private val apiKey: String,
    private val restTemplate: RestTemplate
){
    fun getRouteInfo(pickUp: String, dropOff: String): RouteInfo {
        try {
            val url = "https://maps.googleapis.com/maps/api/directions/json?origin=$pickUp&destination=$dropOff&key=$apiKey"
            val response = restTemplate.getForObject(url, GoogleMapsResponse::class.java)

            if (response == null) {
                throw GoogleMapsException("Failed to connect to Google Maps API")
            }

            if (response.status != "OK") {
                throw GoogleMapsException("Google Maps API returned error status: ${response.status}")
            }

            val route = response.routes.firstOrNull()
                ?: throw RuntimeException("No route found for the given locations: $pickUp to $dropOff")
            val leg = route.legs.firstOrNull()
                ?: throw RuntimeException("No leg found in the route")
            return RouteInfo(
                distanceKm = leg.distance.value,
                estimatedTimeMinute = leg.duration.value/60
            )
        }catch (e: Exception) {
           throw GoogleMapsException("Connection error to Google Maps API", e)
        }
    }
}