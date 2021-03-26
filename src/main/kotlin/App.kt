import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.mPaper
import com.ccfraser.muirwik.components.mTypography
import kotlinx.browser.window
import kotlinx.coroutines.async
import kotlinx.coroutines.await
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.css.Display
import kotlinx.css.Overflow
import kotlinx.css.TextAlign
import kotlinx.css.WhiteSpace
import kotlinx.css.display
import kotlinx.css.flexGrow
import kotlinx.css.height
import kotlinx.css.maxHeight
import kotlinx.css.overflowX
import kotlinx.css.textAlign
import kotlinx.css.vh
import kotlinx.css.whiteSpace
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onDragEnterFunction
import kotlinx.html.js.onDragLeaveFunction
import kotlinx.html.js.onDragOverFunction
import kotlinx.html.js.onDropFunction
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromDynamic
import org.w3c.dom.DragEvent
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileList
import org.w3c.files.FileReader
import org.w3c.files.get
import react.RBuilder
import react.RProps
import react.functionalComponent
import react.useMemo
import react.useState
import styled.css
import styled.styledDiv
import styled.styledInput
import styled.styledLabel
import styled.styledPre

val RBuilder.uploadPanel
    get() = extension(UploadPanel)

external interface UploadPanelProps : RProps {
    var shelvesToShow: List<String>?
    var setAllShelves: (List<String>) -> Unit
}

val UploadPanel = functionalComponent<UploadPanelProps> { props ->
    val (text, setText) = useState("")
    val (books, setBooks) = useState<dynamic>(null)
    val (booksToShow, setBooksToShow) = useState<dynamic>(null)

    // val (shelvesToShow, setShelves) = useState(listOf("read", "to-read", "non-fiction"))

    fun onFilesSelected(files: FileList?) {
        console.log("files", files)
        files?.get(0)?.let {
            FileReader().apply {
                readAsText(it)
                onloadend = {
                    val parsedBooks = parseBooks(result.unsafeCast<String>())
                    println(parsedBooks)
                    println(cljMap(::shelves, parsedBooks))
                    val allShelves =
                        toJs(cljMap(::shelves, parsedBooks)).unsafeCast<Array<Array<String>>>().flatten().toHashSet()
                    println(allShelves)
                    props.setAllShelves(allShelves.toList().sorted())

                    setBooks(parsedBooks)

                    // println(books[0])
                    // println(toJs(books))
                    // println(shelves(toJs(books)[0]))
                }
            }
        }
    }
    //
    useMemo({
        if (books != null) {
            setBooksToShow(
                if (props.shelvesToShow?.isEmpty() == false) {
                    filterByShelves(books, props.shelvesToShow!!.toTypedArray())
                } else books
            )
        }
        console.log("memo?")
    }, arrayOf(books, props.shelvesToShow))
    //
    if (booksToShow != null) {
        setText(toJs(booksToRoam(booksToShow)))
    }

    styledDiv {
        css {
            height = 100.vh
            flexGrow = 3.0
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

        // inp.props?.

        mPaper {
            css {
                // alignItems = Align.center
                // flexGrow = 1.0
            }

            if (text.isBlank()) {
                styledLabel {
                    css {
                        // position = Position.fixed;
                        // top = 50.pct;
                        // left = 50.pct;
                        /* bring your own prefixes */
                        // transform = Transforms().apply { translate((-50).pct, (-50).pct) }
                    }

                    val hiddenInput = "hiddenInput"
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

            styledPre {
                css {
                    whiteSpace = WhiteSpace.preWrap
                    overflowX = Overflow.scroll
                }
                +text
            }
        }
    }

}

val App = functionalComponent<RProps> {
    val (shelves, setShelves) = useState(listOf("read", "to-read", "non-fiction"))
    val (shelvesToShow, setShelvesToShow) = useState(listOf<String>())

    styledDiv {
        css {
            display = Display.flex
            maxHeight = 98.vh
        }

        uploadPanel {
            this.shelvesToShow = shelvesToShow
            setAllShelves = setShelves
        }

        filteringSidebar {
            this.shelves = shelves
            this.setShelvesToShow = setShelvesToShow
        }
    }
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
 *
 * export:
 * copy to clipboard button (doing that automagically can also be an option, but I lean against it)
 * download button

 */

@ExperimentalSerializationApi
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

