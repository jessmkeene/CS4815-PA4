package com.example.pa4.models

import kotlinx.coroutines.flow.Flow


/**
 * Interface defining the contract for hardware-level location and geofencing.
 *
 * @version 1.0
 */
interface LocationRepository {
    val locationUpdates: Flow<Coordinate>
    /**
     * A special function that can be paused and resumed without
     * blocking the underlying thread when retrieving a location.
     *
     * @return A [Coordinate] containing latitude and longitude if
     * there is one, null otherwise.
     */
    suspend fun getLastKnownOrCurrentLocation(): Coordinate?

    /**
     * A special function that can be paused and resumed without
     * blocking the underlying thread when retrieving a city name
     * from our coordinates.
     *
     * @param coordinate Contains latitude and longitude
     *
     * @return Name of the city if found, null otherwise.
     */
    suspend fun reverseGeocodeCity(coordinate: Coordinate): String?

    /**
     * A special function that can be paused and resumed without
     * blocking the underlying thread when adding a circular geofence
     * to the system.
     *
     * @param id Unique identifier for the geofence
     * @param center The center point of the geofence
     * @param radiusMeters The distance from the center in meters
     *
     * @return [Result] indicating success or failure of the registration.
     */
    suspend fun addGeofence(id: String, center: Coordinate, radiusMeters: Float): Result<Unit>

    /**
     * A special function that can be paused and resumed without
     * blocking the underlying thread when removing a circular geofence
     * to the system.
     *
     * @param id Unique identifier for the geofence
     *
     * @return [Result] indicating success or failure.
     */
    suspend fun removeGeofence(id: String): Result<Unit>
}