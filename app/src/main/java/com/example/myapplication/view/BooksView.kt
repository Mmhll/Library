package com.example.myapplication.view

import com.example.myapplication.room.BookEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface BooksView : MvpView {
    fun showManual(message : Int)
    fun fillRecycler(data : MutableList<BookEntity>)
    fun booksIsEmpty()
}