package com.example.run.location

import com.example.core.domain.location.LocationWithAltitude
import android.location.Location

fun Location.toLocationWithAltitude(): LocationWithAltitude {
    return LocationWithAltitude(
        location = com.example.core.domain.location.Location(
            lat = latitude,
            long = longitude
        ),
        altitude = altitude
    )
}