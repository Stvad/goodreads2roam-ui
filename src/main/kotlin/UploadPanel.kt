import com.ccfraser.muirwik.components.MTypographyVariant
import com.ccfraser.muirwik.components.button.mIconButton
import com.ccfraser.muirwik.components.mPaper
import com.ccfraser.muirwik.components.mSvgIcon
import com.ccfraser.muirwik.components.mTypography
import kotlinx.browser.window
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.*
import org.w3c.dom.DragEvent
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.files.FileList
import org.w3c.files.FileReader
import org.w3c.files.get
import react.*
import styled.*

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

    fun onFilesSelected(files: FileList?) {
        console.log("files", files)
        files?.get(0)?.let {
            FileReader().apply {
                val onLoadEnd: (Event) -> Unit = {
                    val parsedBooks = parseBooks(result.unsafeCast<String>())
                    val allShelves =
                        toJs(cljMap(::shelves, parsedBooks)).unsafeCast<Array<Array<String>>>().flatten().toHashSet()
                    props.setAllShelves(allShelves.toList().filter { it.isNotBlank() }.sorted())

                    setBooks(parsedBooks)
                }
                readAsText(it)
                onloadend = onLoadEnd
            }
        }
    }
    useMemo({
        if (books != null) {
            setBooksToShow(
                if (props.shelvesToShow?.isEmpty() == false) {
                    filterByShelves(toClj(props.shelvesToShow!!.toTypedArray()), books)
                } else books
            )
        }
    }, arrayOf(books, props.shelvesToShow))

    useMemo({
        if (booksToShow != null) {
            val newText: String = toJs(booksToRoam(booksToShow))
            setText(if (newText.isNotBlank()) newText else "No books found that are on all the selected shelves")
        }
    }, arrayOf(booksToShow))

    fun RBuilder.copyIcon() {
        mIconButton {
            css {
                // otherwise the it's somehow not applied with proper priority :(
                // position = Position.absolute
                put("position", "fixed !important")

                top = 1.em
                right = 1.em
            }

            mSvgIcon("M318.54,57.282h-47.652V15c0-8.284-6.716-15-15-15H34.264c-8.284,0-15,6.716-15,15v265.522c0,8.284,6.716,15,15,15h47.651 v42.281c0,8.284,6.716,15,15,15H318.54c8.284,0,15-6.716,15-15V72.282C333.54,63.998,326.824,57.282,318.54,57.282z M49.264,265.522V30h191.623v27.282H96.916c-8.284,0-15,6.716-15,15v193.24H49.264z M303.54,322.804H111.916V87.282H303.54V322.804 z") {
                attrs.viewBox = "0 0 352.804 352.804"
            }

            attrs.onClick = {
                window.navigator.clipboard.writeText(text)
            }
        }
    }

    fun RBuilder.filePrompt() {
        styledLabel {

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

    styledDiv {
        css {
            height = 100.vh
            width = 80.pct

            padding(all = 0.5.em)

            transform = Transforms().apply {
                // The main point is to shift the parent of "fixed" positioning for the children elements
                scaleX(1)
            }
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


        mPaper {
            css {
                // alignItems = Align.center

                position = Position.relative
                padding(all = 2.em)
                height = 100.pct

                overflowY = Overflow.auto
            }

            if (text.isBlank()) {
                filePrompt()
            } else {
                copyIcon()

                styledPre {
                    css {
                        whiteSpace = WhiteSpace.preWrap
                        margin(all = 0.px)
                    }
                    +text
                }
            }
        }
    }

}
