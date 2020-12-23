@file:JsModule("goodreads2roam")
@file:JsNonModule

@JsName("parseBooks")
external fun parseBooks(booksCsv: String): dynamic

@JsName("bookToRoam")
external fun bookToRoam(book: dynamic): String

@JsName("booksToRoam")
external fun booksToRoam(books: dynamic): dynamic

@JsName("toJs")
external fun toJs(cljs: dynamic): dynamic

