/**
 *
 * Please note:
 * This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * Do not edit this file manually.
 *
 */

@file:Suppress(
    "ArrayInDataClass",
    "EnumEntryName",
    "RemoveRedundantQualifierName",
    "UnusedImport"
)

package generated.openapi.model


import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize
import java.io.Serializable

/**
 * 
 *
 * @param backdropPath 
 * @param firstAirDate 
 * @param genreIds 
 * @param id 
 * @param name 
 * @param originCountry 
 * @param originalLanguage 
 * @param originalName 
 * @param overview 
 * @param popularity 
 * @param posterPath 
 * @param voteAverage 
 * @param voteCount 
 */
@Parcelize


data class DiscoverTv200ResponseResultsInner (

    @Json(name = "backdrop_path")
    val backdropPath: kotlin.String? = null,

    @Json(name = "first_air_date")
    val firstAirDate: kotlin.String? = null,

    @Json(name = "genre_ids")
    val genreIds: kotlin.collections.List<kotlin.Int>? = null,

    @Json(name = "id")
    val id: kotlin.Int? = 0,

    @Json(name = "name")
    val name: kotlin.String? = null,

    @Json(name = "origin_country")
    val originCountry: kotlin.collections.List<kotlin.String>? = null,

    @Json(name = "original_language")
    val originalLanguage: kotlin.String? = null,

    @Json(name = "original_name")
    val originalName: kotlin.String? = null,

    @Json(name = "overview")
    val overview: kotlin.String? = null,

    @Json(name = "popularity")
    val popularity: java.math.BigDecimal? = java.math.BigDecimal("0"),

    @Json(name = "poster_path")
    val posterPath: kotlin.String? = null,

    @Json(name = "vote_average")
    val voteAverage: java.math.BigDecimal? = java.math.BigDecimal("0"),

    @Json(name = "vote_count")
    val voteCount: kotlin.Int? = 0

) : Serializable, Parcelable {
    companion object {
        private const val serialVersionUID: Long = 123
    }


}

