import kotlinx.css.*
import react.RProps
import react.functionalComponent
import react.useState
import styled.*

val App = functionalComponent<RProps> {
    val (shelves, setShelves) = useState(listOf("read", "to-read", "non-fiction"))
    val (shelvesToShow, setShelvesToShow) = useState(listOf<String>())

    styledDiv {
        css {
            display = Display.flex
            // maxHeight = 100.vh
            overflowY = Overflow.hidden
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
