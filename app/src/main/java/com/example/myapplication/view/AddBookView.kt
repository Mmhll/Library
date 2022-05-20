package com.example.myapplication.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndStrategy::class)
interface AddBookView : MvpView{
    fun putBook()
    fun showMessage(message : Int)
}