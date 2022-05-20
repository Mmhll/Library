package com.example.myapplication.presenter

import android.content.Context
import androidx.room.Room
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.view.OneBookView
import moxy.MvpPresenter

class OneBookPresenter(context : Context, databaseName : String, val id : Int) : MvpPresenter<OneBookView>() {

    override fun attachView(view: OneBookView?) {
        super.attachView(view)
        getData()
    }

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        databaseName
    ).allowMainThreadQueries().build()

    fun getData(){
        var book = db.bookDao().getCurrentBook(id)
        viewState.getBook(book = book)
    }
}