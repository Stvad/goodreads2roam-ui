import com.ccfraser.muirwik.components.gridlist.mGridList
import com.ccfraser.muirwik.components.gridlist.mGridListTile
import com.ccfraser.muirwik.components.gridlist.mGridListTileBar
import kotlinx.css.pct
import kotlinx.css.width
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.dom.img
import react.dom.p
import react.functionalComponent
import styled.css

external interface VideoListProps : RProps {
    var videos: List<Video>
    var selectedVideo: Video?
    var onSelectVideo: (Video) -> Unit
}

val RBuilder.videoList
    get() = extension(VideoList)

val VideoList = functionalComponent<VideoListProps> { props ->
    props.videos.forEach { video ->
        p {
            key = video.id.toString()
            attrs {
                onClickFunction = {
                    props.onSelectVideo(video)
                }
            }

            if (video == props.selectedVideo) +"▶ "

            +"${video.speaker}: ${video.title}"
        }
    }

    mGridList(cellHeight = 180) {
        // css(gridList)
        css {
            width = 45.pct
        }
        // mGridListTile("Subheader", 2) {
        //     // css { put("height", (6.spacingUnits).toString() + " !important") }
        //     mListSubheader("Video", component = "div") {
        //         css { height = LinearDimension.auto }
        //     }
        // }
        props.videos.forEach {
            mGridListTile(key = it.youtubeId) {
                img(src = it.previewUrl, alt = it.title) {}
                mGridListTileBar(it.title, "by ${it.speaker}")
            }
        }
    }

}

fun RBuilder.dumbVideoList(videos: List<Video>) {
    videos.forEach {
        p {
            +"${it.speaker}: ${it.title}"
        }
    }
}
