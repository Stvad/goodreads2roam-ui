@file:JsModule("react-share")
@file:JsNonModule

import react.RClass
import react.RProps

@JsName("EmailIcon")
external val emailIcon: RClass<IconProps>

@JsName("TwitterIcon")
external val twitterIcon: RClass<IconProps>

@JsName("EmailShareButton")
external val emailShareButton: RClass<ShareButtonProps>

@JsName("TwitterShareButton")
external val twitterShareButton: RClass<ShareButtonProps>

external interface IconProps : RProps {
    var size: Int
    var round: Boolean
}

external interface ShareButtonProps : RProps {
    var url: String
}
