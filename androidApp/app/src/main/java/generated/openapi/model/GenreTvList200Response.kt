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
 * @param genres 
 */
@Parcelize


data class GenreTvList200Response (

    @Json(name = "genres")
    val genres: kotlin.collections.List<GenreTvList200ResponseGenresInner>? = null

) : Serializable, Parcelable {
    companion object {
        private const val serialVersionUID: Long = 123
    }


}

