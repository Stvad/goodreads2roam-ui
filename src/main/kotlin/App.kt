import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.mPaper
import com.ccfraser.muirwik.components.mTypography
import kotlinx.browser.window
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.css.Display
import kotlinx.css.Position
import kotlinx.css.TextAlign
import kotlinx.css.display
import kotlinx.css.height
import kotlinx.css.left
import kotlinx.css.pct
import kotlinx.css.position
import kotlinx.css.properties.Transforms
import kotlinx.css.properties.translate
import kotlinx.css.textAlign
import kotlinx.css.top
import kotlinx.css.transform
import kotlinx.css.vh
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onDragEnterFunction
import kotlinx.html.js.onDragLeaveFunction
import kotlinx.html.js.onDragOverFunction
import kotlinx.html.js.onDropFunction
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.w3c.dom.DragEvent
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileList
import org.w3c.files.FileReader
import org.w3c.files.get
import react.RProps
import react.dom.pre
import react.functionalComponent
import react.useState
import styled.css
import styled.styledDiv
import styled.styledInput
import styled.styledLabel

val App = functionalComponent<RProps> {
    val (text, setText) = useState("")


    fun onFilesSelected(files: FileList?) {
        console.log("files", files)
        files?.get(0)?.let {
            FileReader().apply {
                onloadend = {
                    setText(toJs(booksToRoam(parseBooks(result.unsafeCast<String>()))))
                }
                readAsText(it)
            }
        }
    }

    // mGridContainer {
    //    mGridItem {
    styledDiv {
        css {
            height = 100.vh
            // display = Display.flex
            // flexDirection = FlexDirection.column
            // width
        }

        //todo on enter indicator

        attrs.onDragOverFunction = {
            console.log("over", it)
            it.preventDefault()
        }


        attrs.onDragEnterFunction = {
            console.log("enter", it)
        }

        attrs.onDragLeaveFunction = {
            console.log("leave", it)
        }


        attrs.onDropFunction = {
            val e = it.unsafeCast<DragEvent>()
            e.stopPropagation()
            e.preventDefault()

            onFilesSelected(e.dataTransfer?.files)
        }

        val hiddenInput = "hiddenInput"
        // inp.props?.

        mPaper {
            css {
                // alignItems = Align.center
                // flexGrow = 1.0
            }

            if (text.isBlank()) {
                styledLabel {
                    css {
                        position = Position.fixed;
                        top = 50.pct;
                        left = 50.pct;
                        /* bring your own prefixes */
                        transform = Transforms().apply { translate((-50).pct, (-50).pct) }
                    }

                    attrs.htmlFor = hiddenInput

                    mTypography(variant = MTypographyVariant.h1) {
                        css {
                            textAlign = TextAlign.center
                        }
                        +"Gimme your files"
                    }

                    styledInput(type = InputType.file) {
                        css {
                            display = Display.none
                        }

                        attrs.id = hiddenInput

                        attrs.onChangeFunction = { e ->
                            val files = e.target.unsafeCast<HTMLInputElement>().files
                            onFilesSelected(files)
                        }
                    }
                }
            }

            pre {
                +text
            }
        }
    }

    // }
    // }

}

/**
 * initial state - big drag & drop panel with prompt to add book (clickin/input should work too)
 * with placeholder on the side for shelves
 * when drag - transform the thing & populate shelves
 * need to figure out how to exclude/include well
 *
 * dark-ish theme
 * https://roam-tools.ryanguill.com/
 * markdown syntax highlighting
 *
 * support buttons
 * this is also available as CLI at ...

 */

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

