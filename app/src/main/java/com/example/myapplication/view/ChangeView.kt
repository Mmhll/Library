package com.example.myapplication.view

import com.example.myapplication.room.BookEntity
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface ChangeView : MvpView{
    fun changeBook()
    fun showMessage(message : Int)
    fun showBook(book : BookEntity)
}