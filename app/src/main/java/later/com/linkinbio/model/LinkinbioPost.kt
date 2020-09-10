package later.com.linkinbio.model

data class LinkinbioPost(
    val image_url: String?,
    val link_url: String?,
    var isViewed: Boolean = false
)