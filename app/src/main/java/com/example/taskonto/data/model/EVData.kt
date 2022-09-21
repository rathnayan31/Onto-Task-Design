package com.example.taskonto.data.model
import com.google.gson.annotations.SerializedName

data class EVData(
    @SerializedName("username") val userName : String? = null,
    @SerializedName("bookings") val bookingDetails : List<Bookings>? = null,
)

data class Bookings (
    @SerializedName("car") var car : Car? = Car(),
    @SerializedName("subscription_miles_left") var subscriptionMilesLeft : String?= null
)

data class Car (
    @SerializedName("make") var make: String?= null,
    @SerializedName("model") var model: String?= null,
    @SerializedName("last_energy_level") var lastEnergyLevel: String? = null,
)