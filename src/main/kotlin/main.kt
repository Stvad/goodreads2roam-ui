import com.ccfraser.muirwik.components.mCssBaseline
import kotlinx.browser.document
import kotlinx.css.CSSBuilder
import kotlinx.css.body
import kotlinx.css.height
import kotlinx.css.html
import kotlinx.css.margin
import kotlinx.css.padding
import kotlinx.css.pct
import kotlinx.css.px
import react.child
import react.dom.render
import styled.injectGlobal

fun main() {
    val styles = CSSBuilder().apply {
        body {
            htmlAndBodyDefaults()
        }
        html {
            htmlAndBodyDefaults()
        }
    }

    injectGlobal(styles.toString())
    render(document.getElementById("root")) {
        mCssBaseline()

        child(App)
    }
}

private fun CSSBuilder.htmlAndBodyDefaults() {
    margin(0.px)
    padding(0.px)
    height = 100.pct
}

