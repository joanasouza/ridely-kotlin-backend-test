package tech.jaya.ridely.usecase

import org.springframework.stereotype.Component
import tech.jaya.ridely.exception.RideNotFoundException
import tech.jaya.ridely.repository.RideRepo

@Component
class RideUseCase {
    private lateinit var  rideRepo: RideRepo

    fun findRideById(id: Long) = rideRepo.findById(id).orElseThrow {
        RideNotFoundException("No ride found with id $id") }

}