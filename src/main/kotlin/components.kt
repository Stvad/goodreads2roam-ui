import com.ccfraser.muirwik.components.mCssBaseline
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import react.RProps
import react.dom.div
import react.dom.h1
import react.dom.h3
import react.functionalComponent
import react.useEffect
import react.useState

val App = functionalComponent<RProps> {
    mCssBaseline()

    val (currentVideo, setCurrentVideo) = useState<Video?>(null)
    val (unwatchedVideos, setUnwatchedVideos) = useState(emptyList<Video>())
    val (watchedVideos, setWatchedVideos) = useState(emptyList<Video>())

    useEffect(emptyList()) {
        GlobalScope.launch {
            setUnwatchedVideos(fetchVideos())
        }
    }

    h1 {
        +"KotlinConf Explorer"
    }
    div {

        h3 {
            +"Videos to watch"
        }
        videoList {
            videos = unwatchedVideos
            selectedVideo = currentVideo
            onSelectVideo = setCurrentVideo
        }

        h3 {
            +"Videos watched"
        }
        videoList {
            videos = watchedVideos
            selectedVideo = currentVideo
            onSelectVideo = setCurrentVideo
        }

        currentVideo?.let {
            videoPlayer {
                video = it
                unwatchedVideo = it in unwatchedVideos
                onWatchedButtonPressed = { video ->
                    if (video in unwatchedVideos) {
                        setUnwatchedVideos(unwatchedVideos - video)
                        setWatchedVideos(watchedVideos + video)
                    } else {
                        setUnwatchedVideos(unwatchedVideos + video)
                        setWatchedVideos(watchedVideos - video)
                    }
                }
            }
        }
    }
}

suspend fun fetchVideo(id: Int): Video =
    window.fetch("https://my-json-server.typicode.com/kotlin-hands-on/kotlinconf-json/videos/$id")
        .await()
        .json()
        .await()
        .let {
            Json.decodeFromDynamic(it)
        }

suspend fun fetchVideos() = coroutineScope {
    (1..25).map { id ->
        async {
            fetchVideo(id)
        }
    }.awaitAll()
}

