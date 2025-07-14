package tech.jaya.ridely.usecase

import org.springframework.stereotype.Component
import tech.jaya.ridely.exception.DriverNotFound
import tech.jaya.ridely.exception.DriverUnavailable
import tech.jaya.ridely.exception.RideNotFoundException
import tech.jaya.ridely.model.Driver
import tech.jaya.ridely.repository.DriverRepo
import tech.jaya.ridely.repository.RideRepo

@Component
class DriverUseCase {
    private lateinit var driverRepo: DriverRepo
    private lateinit var rideRepo: RideRepo

    fun findDriverById(id: Long) = driverRepo.findById(id).orElseThrow {
        DriverNotFound("Drive not found $id")
    }

    fun save(driver: Driver) = driverRepo.save(driver)

    fun findLastRideByDriverId(driverId: Long) = rideRepo.findLastRideByDriveId(driverId)
        .orElseThrow {  throw RideNotFoundException("You don't have any Ride") }

    fun findAvailableDriver()= driverRepo.findAvailableDriver()
        .orElseThrow {  throw DriverUnavailable("We do not have drivers available") }

    fun deleteById(id: Long) {
        if (!driverRepo.existsById(id)) {
            throw DriverNotFound("Driver with id $id not found")
        }
        driverRepo.deleteById(id)
    }

}