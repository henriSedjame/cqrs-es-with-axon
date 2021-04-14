package io.fleet.parkingservice.data.entities.repositories

import io.fleet.parkingservice.data.entities.CarStatus
import io.fleet.parkingservice.data.entities.CarView
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface CarViewRepository : CoroutineCrudRepository<CarView, UUID> {

    @Query("INSERT INTO cars (id, immatriculation, marque, status) VALUES (:id, :immatriculation, :marque, :status)")
    suspend fun insert(
        @Param("id") id: UUID,
        @Param("immatriculation") immatriculation: String,
        @Param("marque") marque: String,
        @Param("status") status: CarStatus
    ): CarView

}
