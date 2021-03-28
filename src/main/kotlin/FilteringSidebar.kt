import com.ccfraser.muirwik.components.*
import com.ccfraser.muirwik.components.form.mFormControl
import com.ccfraser.muirwik.components.form.mFormGroup
import com.ccfraser.muirwik.components.form.mFormLabel
import com.ccfraser.muirwik.components.input.mInput
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.title
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
        setCheckedState(props.shelves.associateWith { false } + checkedState)
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
            padding(left = 0.5.em, right = 0.5.em)
            height = 100.vh
        }
        styledDiv {
            css {
                alignSelf = Align.center
                marginTop = 1.em
                textAlign = TextAlign.center
            }

            mTypography(
                variant = MTypographyVariant.h4,
                align = MTypographyAlign.center,
            ) {
                +"Goodreads2Roam"
            }

            mTypography(
                variant = MTypographyVariant.body2,
                color = MTypographyColor.textSecondary,
                align = MTypographyAlign.center,
            ) {
                css {
                    padding(vertical = 0.5.em)
                }

                +"Supported by "
                styledA {
                    css {
                        textDecoration = TextDecoration.none
                        color = Color("#3182ce")
                    }
                    attrs.href = "https://roam.garden"
                    +"\uD83C\uDF31 Roam Garden"
                }
                +" a service to generate beautiful static sites (digital gardens) from your RoamResearch Graph"
            }

            styledIframe {
                css {
                    borderWidth = 0.px
                }

                attrs.src = "https://github.com/sponsors/Stvad/button"
                attrs.title = "Sponsor Stvad"
                attrs.height = "35"
                attrs.width = "116"
            }
        }
        styledHr {
            css {
                width = 100.pct
                borderWidth = 0.px
                borderTopWidth = 1.px
            }
        }

        mFormControl {
            css {
                minHeight = 0.px
                display = Display.flex
                flexDirection = FlexDirection.column
            }

            mFormLabel("Shelves (combined through AND)", component = "legend", className = "shelves-title") {
                css {
                    marginTop = 0.5.em
                    textAlign = TextAlign.center
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
