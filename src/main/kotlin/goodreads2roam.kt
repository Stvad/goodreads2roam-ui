@file:JsModule("goodreads2roam")
@file:JsNonModule

@JsName("parseBooks")
external fun parseBooks(booksCsv: String): dynamic

@JsName("toJs")
external fun toJs(cljs: dynamic): dynamic

