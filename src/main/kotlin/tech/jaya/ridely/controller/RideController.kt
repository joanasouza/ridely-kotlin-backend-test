package tech.jaya.ridely.controller

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
import tech.jaya.ridely.dto.RideEstimateDTO
import tech.jaya.ridely.exception.DriverUnavailable
import tech.jaya.ridely.exception.RideNotFoundException
import tech.jaya.ridely.repository.DriverRepo
import tech.jaya.ridely.repository.RideRepo
import tech.jaya.ridely.service.RideService

@RestController
@RequestMapping("/rides")
class RideController(
    private val rideRepo: RideRepo,
    private val driverRepo: DriverRepo,
    private val rideService: RideService
) {

    @PostMapping("/estimate")
    fun estimateRide(@RequestBody req: RequestRideEstimate): RideEstimateDTO {
        return rideService.estimateRide(req)
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
        val id = req.id
        val ride = rideRepo.findById(id).orElseThrow { RideNotFoundException("No ride found with id $id") }
        ride.refuse()
        return RefuseResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/cancel-ride")
    fun deleteRide(@RequestBody req: ActionRideRequest): CancelResponse {
        val id = req.id
        val ride = rideRepo.findById(id).orElseThrow { RideNotFoundException("No ride found with id $id") }
        ride.cancel()
        return CancelResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/finish-ride")
    fun finishRide(@RequestBody req: FinishRideRequest): FinishResponse {
        val (id, price) = req
        val ride = rideRepo.findById(id).orElseThrow { RideNotFoundException("No ride found with id $id") }
        ride.complete(price)
        return FinishResponse.fromRide(rideRepo.save(ride))
    }

    @PostMapping("/accept-ride")
    fun acceptRide(@RequestBody req: ActionRideRequest): AcceptResponse {
        val id = req.id
        val ride = rideRepo.findById(id).orElseThrow { RideNotFoundException("No ride found with id $id") }
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