import com.ccfraser.muirwik.components.MOptionColor
import com.ccfraser.muirwik.components.form.MFormControlComponent
import com.ccfraser.muirwik.components.form.mFormControl
import com.ccfraser.muirwik.components.form.mFormGroup
import com.ccfraser.muirwik.components.form.mFormLabel
import com.ccfraser.muirwik.components.input.mInput
import com.ccfraser.muirwik.components.mCheckboxWithLabel
import kotlinx.css.*
import kotlinx.css.properties.*
import org.w3c.dom.HTMLInputElement
import react.*
import styled.*

val RBuilder.filteringSidebar
    get() = extension(FilteringSidebar)

external interface FilteringSidebarProps : RProps {
    var shelves: List<String>
    var setShelvesToShow: (List<String>) -> Unit
}

val FilteringSidebar = functionalComponent<FilteringSidebarProps> { props ->
    val (checkedState, setCheckedState) = useState(emptyMap<String, Boolean>())
    val (filter, setFilter) = useState("")

    useEffect(listOf(props.shelves)) {
        setCheckedState(props.shelves.associateWith { false })
    }

    fun handleChecked(name: String) {
        val newState = checkedState + (name to !checkedState.getOrElse(name) { false })
        setCheckedState(newState)
        props.setShelvesToShow(newState.filter { it.value }.keys.toList())
    }

    styledDiv {
        css {
            display = Display.flex
            flexDirection = FlexDirection.column
            // flexGrow = 1.0
            width = 20.pct
            alignItems = Align.stretch
            padding(left = 1.em, right = 1.em)
            height = 100.vh
        }
        styledDiv {
            css {
                alignSelf = Align.center
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

        mFormControl(component = MFormControlComponent.fieldSet) {
            css {
                // overflowY = Overflow.scroll
                height = 100.pct
            }

            mFormLabel("Shelves (combined through AND)", component = "legend", className = "shelves-title") {
                css {
                    marginTop = 0.5.em
                }
            }

            mInput(placeholder = "Filter", value = filter) {
                css {
                    marginTop = 0.7.em
                }

                attrs.onChange = { e -> setFilter(e.target.unsafeCast<HTMLInputElement>().value) }
            }

            styledDiv {
                css {
                    overflowY = Overflow.auto
                }

                mFormGroup {
                    checkedState.toList().filter { it.first.contains(filter) }.sortedBy { it.first }.forEach { shelf ->

                        mCheckboxWithLabel(shelf.first, shelf.second, color = MOptionColor.primary) {
                            attrs.onClick = { handleChecked(shelf.first) }
                        }
                    }
                }
            }
        }

    }
}
