package later.com.linkinbio.model

data class LinkinbioPost(
    val imageUrl: String?,
    val linkUrl: String?,
    var isViewed: Boolean = false
)