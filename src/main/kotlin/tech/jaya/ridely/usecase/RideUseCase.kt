package tech.jaya.ridely.usecase

import org.springframework.stereotype.Component
import tech.jaya.ridely.exception.RideNotFoundException
import tech.jaya.ridely.model.Ride
import tech.jaya.ridely.repository.RideRepo

@Component
class RideUseCase {
    private lateinit var  rideRepo: RideRepo

    fun findRideById(id: Long) = rideRepo.findById(id).orElseThrow {
        RideNotFoundException("No ride found with id $id") }

    fun save(ride: Ride) = rideRepo.save(ride)

    fun deleteById(id: Long) {
        if (!rideRepo.existsById(id)) {
            throw RideNotFoundException("Ride with id $id not found")
        }
        rideRepo.deleteById(id)
    }


}