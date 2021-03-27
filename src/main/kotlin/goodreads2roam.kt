@file:JsModule("goodreads2roam")
@file:JsNonModule

@JsName("parseBooks")
external fun parseBooks(booksCsv: String): dynamic

@JsName("bookToRoam")
external fun bookToRoam(book: dynamic): String

@JsName("shelves")
external fun shelves(book: dynamic): List<String>

@JsName("filterByShelves")
external fun filterByShelves(shelves: Array<String>, books: dynamic): dynamic

@JsName("cljMap")
external fun cljMap(cljFun: dynamic, cljCollection: dynamic): dynamic

@JsName("booksToRoam")
external fun booksToRoam(books: dynamic): dynamic

@JsName("toJs")
external fun toJs(cljs: dynamic): dynamic

@JsName("toClj")
external fun toClj(js: dynamic): dynamic
