import com.ccfraser.muirwik.components.mCssBaseline
import kotlinx.browser.document
import react.child
import react.dom.render

fun main() {
    render(document.getElementById("root")) {
        mCssBaseline()

        child(App)
    }
}

