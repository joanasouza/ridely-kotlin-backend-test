package tech.jaya.ridely.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.jaya.ridely.dto.AcceptResponse
import tech.jaya.ridely.dto.ActionRideRequest
import tech.jaya.ridely.dto.CancelResponse
import tech.jaya.ridely.dto.FinishResponse
import tech.jaya.ridely.dto.FinishRideRequest
import tech.jaya.ridely.dto.RefuseResponse
import tech.jaya.ridely.dto.RequestDriver
import tech.jaya.ridely.dto.RequestDriverResponse
import tech.jaya.ridely.dto.RequestRideEstimate
import tech.jaya.ridely.dto.RideEstimateResponseDTO
import tech.jaya.ridely.exception.DriverUnavailable
import tech.jaya.ridely.repository.DriverRepo
import tech.jaya.ridely.repository.RideRepo
import tech.jaya.ridely.service.RideService
import tech.jaya.ridely.usecase.RideUseCase

@RestController
@RequestMapping("/rides")
@Tag(name = "Rides", description = "Endpoints for managing rides")
class RideController(
    private val rideRepo: RideRepo,
    private val driverRepo: DriverRepo,
    private val rideService: RideService,
    private val rideUseCase: RideUseCase

) {

    @PostMapping("/estimate")
    fun estimate(@Valid @RequestBody req: RequestRideEstimate): RideEstimateResponseDTO {
        return rideService.estimate(req)
    }

    @PostMapping("/request-driver")
    fun requestDriver(@RequestBody req: RequestDriver): RequestDriverResponse {
        val driver = driverRepo.findAvailableDriver().orElseThrow {
            throw DriverUnavailable("We do not have drivers available")
        }
        val ride = req.toRide(driver)
        ride.request(driver)
        return RequestDriverResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/refuse-ride")
    fun refuseRide(@RequestBody req: ActionRideRequest): RefuseResponse {
        val ride = rideUseCase.findRideById(req.id)
        ride.refuse()
        return RefuseResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/cancel-ride")
    fun deleteRide(@RequestBody req: ActionRideRequest): CancelResponse {
        val ride = rideUseCase.findRideById(req.id)
        ride.cancel()
        return CancelResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/finish-ride")
    fun finishRide(@RequestBody req: FinishRideRequest): FinishResponse {
        val (id, price) = req
        val ride = rideUseCase.findRideById(id)
        ride.complete(price)
        return FinishResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/accept-ride")
    fun acceptRide(@RequestBody req: ActionRideRequest): AcceptResponse {
        val ride = rideUseCase.findRideById(req.id)
        ride.accept()
        return AcceptResponse.fromRide(rideRepo.save(ride))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return rideRepo.deleteById(id).let {
            ResponseEntity.noContent().build()
        }
    }
}