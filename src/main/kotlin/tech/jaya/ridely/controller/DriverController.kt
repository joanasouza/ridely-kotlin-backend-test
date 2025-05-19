package tech.jaya.ridely.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.jaya.ridely.repository.DriverRepo
import tech.jaya.ridely.repository.RideRepo

@RestController
@RequestMapping("/drivers")
class DriverController(
    private val driverRepo: DriverRepo,
    private val rideRepo: RideRepo
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<DriverResponse> {
        return driverRepo.findById(id).orElseThrow {
            DriverNotFound("Drive not found $id")
        }.let {
            ResponseEntity.ok(it.toResponse())
        }
    }

    @GetMapping("/{id}/get-rides")
    fun getRide(@PathVariable id: Long): AcceptResponse {
        val ride = rideRepo.findLastRideByDriveId(id).orElseThrow {
            throw RideNotFoundException("You don't have any Ride")
        }
        return AcceptResponse.fromRide(ride)
    }

    @PostMapping
    fun save(@RequestBody driverRequest: DriverCreation): ResponseEntity<DriverResponse> {
        return driverRepo.save(driverRequest.toDriver()).let {
            ResponseEntity.ok(it.toResponse())
        }
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
        return driverRepo.deleteById(id).let {
            ResponseEntity.noContent().build()
        }
    }
}
