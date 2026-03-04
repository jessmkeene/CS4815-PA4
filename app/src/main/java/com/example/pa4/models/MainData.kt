package wpics.weather.models

import com.google.gson.annotations.SerializedName

/**
 * Data class representing core atmospheric measurements.
 *
 * @property temp Current temperature in the requested unit system. Defaults to null if missing from JSON.
 * @property humidity Humidity percentage. Defaults to null if missing from JSON.
 * @property pressure Current pressure. Defaults to null if missing from JSON.
 *
 * @version 1.0
 */
data class MainData(
    val temp: Double? = null,
    val humidity: Int? = null,
    val pressure: Int? = null,
    @SerializedName("temp_min") val tempMin: Double?, //needed serialized name to get values and render correctly
    @SerializedName("temp_max") val tempMax: Double?
)