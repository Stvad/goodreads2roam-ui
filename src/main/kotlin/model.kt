import kotlinx.serialization.Serializable

@Serializable
data class Video(val id: Int, val title: String, val speaker: String, val videoUrl: String) {
    // https://www.youtube.com/watch?v=juFkdMv4B9s
    // https://youtu.be/PsaFVLr8t4E
    // yes I need to properly parse url :p
    val youtubeId = videoUrl.split("/").last().split("=").last()

    //http://i3.ytimg.com/vi/erLk59H86ww/hqdefault.jpg
    val previewUrl = "http://i3.ytimg.com/vi/$youtubeId/hqdefault.jpg"

    val displayName = "$speaker: $title"
}

val unwatchedVideosInit = listOf(
    Video(1, "Building and breaking things", "John Doe", "https://youtu.be/PsaFVLr8t4E"),
    Video(2, "The development process", "Jane Smith", "https://youtu.be/PsaFVLr8t4E"),
    Video(3, "The Web 7.0", "Matt Miller", "https://youtu.be/PsaFVLr8t4E")
)

val watchedVideosInit = listOf(
    Video(4, "Mouseless development", "Tom Jerry", "https://youtu.be/PsaFVLr8t4E")
)

