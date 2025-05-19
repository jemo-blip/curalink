package com.james.haelia.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleInfo(
    val id: String = "",
    val make: String,
    val model: String,
    val year: String,
    val condition: String,
   // val color: String,
    val numberOfPassengers: String,
    val fueltype: String,
    val bodytype: String,
    val color: String,
    val confidenceValue: String,
    val valuationDate: String,

    val estimatedValue: String
) : Parcelable
