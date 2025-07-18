package tech.jaya.ridely.exception

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.RestTemplate
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@Component
@ControllerAdvice
class RidelyExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [RideNotFoundException::class])
    fun handleContentNotFound(
        ex: RideNotFoundException, request: WebRequest
    ): ResponseEntity<Any> {
        return handleExceptionInternal(
            ex,
            ex.message,
            HttpHeaders(),
            HttpStatus.NOT_FOUND,
            request
        )!!
    }

    @ExceptionHandler(DriverUnavailable::class)
    fun handleDriverUnavailable(
        ex: DriverUnavailable, request: WebRequest
    ): ResponseEntity<Any> {
        val bodyOfResponse = "Driver unavailable: ${ex.message}"

        return handleExceptionInternal(
            ex,
            bodyOfResponse,
            HttpHeaders(),
            HttpStatus.SERVICE_UNAVAILABLE,
            request
        )!!
    }

    @ExceptionHandler(RideInvalidState::class)
    fun handleRideInvalidState(
        ex: RideInvalidState, request: WebRequest
    ): ResponseEntity<Any> {
        val bodyOfResponse = "Ride invalid state: ${ex.message}"

        return handleExceptionInternal(
            ex,
            bodyOfResponse,
            HttpHeaders(),
            HttpStatus.BAD_REQUEST,
            request
        )!!
    }

    override fun  handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.map {
            it.defaultMessage ?: "Pickup or drop-off cannot be null or empty"  }
        val body = mapOf("errors" to errors)
            return ResponseEntity(body, HttpStatus.BAD_REQUEST)
        }

   @ExceptionHandler(value = [GoogleMapsException::class])
    fun handleGoogleMapsException(
        ex: GoogleMapsException, request: WebRequest
    ): ResponseEntity<Any>? {
        val bodyOfResponse = "Failed to connect to Google Maps API: ${ex.message}"

        return handleExceptionInternal(
            ex,
            bodyOfResponse,
            HttpHeaders(),
            HttpStatus.BAD_GATEWAY,
            request
        )
    }
}