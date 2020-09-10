package later.com.linkinbio.model

import com.google.gson.annotations.SerializedName

data class LinkinbioPost(
    @SerializedName("image_url")
    val imageUrl: String?,

    @SerializedName("link_url")
    val linkUrl: String?,
    var isViewed: Boolean = false
)