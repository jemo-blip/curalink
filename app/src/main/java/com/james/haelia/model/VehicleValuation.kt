package com.james.haelia.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


  @Suppress("DEPRECATED_ANNOTATION")
@Parcelize
data class VehicleValuation(
      val id: String = "",
    val userId: String,
    val make: String,
    val model: String,
    val year: String,
    val bodyType: String,
    val fuelType: String,
    val numberOfPassengers: String,
    val condition: String,
    val color: String,
    val estimatedValue: String,
    val valuationDate: String,
    val confidenceLevel: String,

) : Parcelable
