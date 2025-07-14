package tech.jaya.ridely.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tech.jaya.ridely.dto.AcceptResponse
import tech.jaya.ridely.dto.DriverCreation
import tech.jaya.ridely.dto.DriverResponse
import tech.jaya.ridely.dto.toResponse
import tech.jaya.ridely.usecase.DriverUseCase

@RestController
@RequestMapping("/drivers")
class DriverController(
    private val driverUseCase: DriverUseCase
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<DriverResponse> {
        val driver = driverUseCase.findDriverById(id)
        return ResponseEntity.ok(driver.toResponse())
        }


    @GetMapping("/{id}/get-rides")
    fun getRide(@PathVariable id: Long): AcceptResponse {
        val ride = driverUseCase.findLastRideByDriverId(id)
        return AcceptResponse.fromRide(ride)
    }

    @PostMapping
    fun save(@RequestBody driverRequest: DriverCreation): ResponseEntity<DriverResponse> {
        val driver = driverUseCase.save(driverRequest.toDriver())
            return ResponseEntity.ok(driver.toResponse())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Unit> {
    driverUseCase.deleteById(id)
        return ResponseEntity.noContent().build()
    }
}
