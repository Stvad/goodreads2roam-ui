import com.ccfraser.muirwik.components.MOptionColor
import com.ccfraser.muirwik.components.form.mFormControl
import com.ccfraser.muirwik.components.form.mFormGroup
import com.ccfraser.muirwik.components.form.mFormLabel
import com.ccfraser.muirwik.components.mCheckboxWithLabel
import kotlinx.css.Align
import kotlinx.css.Color
import kotlinx.css.Display
import kotlinx.css.FlexDirection
import kotlinx.css.Overflow
import kotlinx.css.alignItems
import kotlinx.css.color
import kotlinx.css.display
import kotlinx.css.em
import kotlinx.css.flexDirection
import kotlinx.css.flexGrow
import kotlinx.css.overflowX
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.properties.TextDecoration
import kotlinx.css.textDecoration
import kotlinx.css.width
import react.RBuilder
import react.RProps
import react.functionalComponent
import react.useState
import styled.css
import styled.styledA
import styled.styledDiv
import styled.styledH2
import styled.styledH3
import styled.styledHr

val RBuilder.filteringSidebar
    get() = extension(FilteringSidebar)

external interface FilteringSidebarProps : RProps {
    var shelves: List<String>?
    var setShelvesToShow: (List<String>) -> Unit
}

val FilteringSidebar = functionalComponent<FilteringSidebarProps> { props ->
    val (activeShelves, setActiveShelves) = useState(emptySet<String>())
    fun handleChecked(name: String, checked: Boolean) {
        val newShelves = if (checked) activeShelves + name else activeShelves - name
        setActiveShelves(newShelves)
        props.setShelvesToShow(newShelves.toList())
    }

    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            flexGrow = 1.0
            alignItems = Align.center
        }
        styledDiv {
            css {
                padding(left = 2.em, right = 2.em)
            }

            styledH2 {
                +"Goodreads2Roam"
            }
            styledH3 {
                +"by "
                styledA {
                    css {
                        textDecoration = TextDecoration.none
                        color = Color("#3182ce")
                    }
                    attrs.href = "https://roam.garden"
                    +"\uD83C\uDF31 Roam Garden"
                }
            }
        }
        styledHr {
            css {
                width = 100.pct
            }
        }

        mFormControl {
            css {
                overflowX = Overflow.scroll
            }

            mFormLabel("Shelves (combined through AND)", component = "legend", className = "shelves-title")

            mFormGroup {
                props.shelves?.forEach {
                    shelfCheckbox {
                        name = it
                        handleChecked = ::handleChecked
                    }
                }
            }
        }

    }
}

external interface ShelfCheckboxProps : RProps {
    var name: String
    var handleChecked: (String, Boolean) -> Unit
}

val ShelfCheckbox = functionalComponent<ShelfCheckboxProps> { props ->
    val (checked, setChecked) = useState(false)

    mCheckboxWithLabel(props.name, checked, color = MOptionColor.primary) {
        attrs.onClick = {
            val newState = !checked
            setChecked(newState)
            props.handleChecked(props.name, newState)
        }
    }
}

val RBuilder.shelfCheckbox
    get() = extension(ShelfCheckbox)
