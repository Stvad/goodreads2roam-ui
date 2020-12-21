import react.FunctionalComponent
import react.RBuilder
import react.ReactElement
import react.child

fun <Props> extension(component: FunctionalComponent<Props>): RBuilder.(Props.() -> Unit) -> ReactElement {
    fun RBuilder.builder(handler: Props.() -> Unit) = child(component) {
        attrs {
            handler()
        }
    }

    return RBuilder::builder
}
