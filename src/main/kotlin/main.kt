import kotlinx.browser.document
import react.child
import react.dom.render

fun main() {
    console.log(toJs(parseBooks("one,two\nv1,v2")))

    render(document.getElementById("root")) {
        child(App)
    }
}

