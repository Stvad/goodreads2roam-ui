import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.Display.flex
import kotlinx.css.Position
import kotlinx.css.backgroundColor
import kotlinx.css.display
import kotlinx.css.fontFamily
import kotlinx.css.marginBottom
import kotlinx.css.position
import kotlinx.css.px
import kotlinx.css.right
import kotlinx.css.top
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.functionalComponent
import styled.css
import styled.styledButton
import styled.styledDiv
import styled.styledH3

external interface VideoPlayerProps : RProps {
    var video: Video
    var onWatchedButtonPressed: (Video) -> Unit
    var unwatchedVideo: Boolean
}

val RBuilder.videoPlayer
    get() = extension(VideoPlayer)

val VideoPlayer = functionalComponent<VideoPlayerProps> { props ->
    styledDiv {
        css {
            position = Position.absolute
            top = 10.px
            right = 10.px
        }

        styledH3 {
            css {
                fontFamily = "sans-serif"
            }
            +"${props.video.speaker}: ${props.video.title}"
        }

        styledButton {
            css {
                display = Display.block
                backgroundColor = if (props.unwatchedVideo) Color.lightGreen else Color.red
            }
            attrs {
                onClickFunction = {
                    props.onWatchedButtonPressed(props.video)
                }
            }

            if (props.unwatchedVideo) {
                +"Mark as watched"
            } else {
                +"Mark as unwatched"
            }
        }

        styledDiv {
            css {
                display = flex
                marginBottom = 10.px
            }

            emailShareButton {
                attrs.url = props.video.videoUrl
                emailIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }

            twitterShareButton {
                attrs.url = props.video.videoUrl
                twitterIcon {
                    attrs.size = 32
                    attrs.round = true
                }
            }
        }

        reactPlayer {
            attrs.url = props.video.videoUrl
        }

    }
}
