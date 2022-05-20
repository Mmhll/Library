package com.example.myapplication.presenter

import android.content.Context
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.room.BookEntity
import com.example.myapplication.view.AddBookView
import moxy.MvpPresenter

class AddBookPresenter(context : Context, databaseName : String) : MvpPresenter<AddBookView>(){

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        databaseName
    ).allowMainThreadQueries().build()


    fun putBook(about : String, uri : String, title : String, year : String, author : String) {
        if (checkData(arrayListOf(about, uri, title, year, author))) {
            val book = BookEntity(
                id = getId(),
                about = about,
                author = author,
                uri = uri,
                title = title,
                year = year
            )
            db.bookDao().putBook(book)
            viewState.putBook()
        } else {
            viewState.showMessage(R.string.error)
        }
    }


    //Internal logics

    private fun getId() : Int{
        return try {
            var id = db.bookDao().getBooks()[db.bookDao().getBooks().size - 1].id
            id + 1
        } catch (e : ArrayIndexOutOfBoundsException){
            0
        }
    }

    private fun checkData(data : ArrayList<String>): Boolean {
        data.forEach {
            if (it.isEmpty()){
                return false
            }
        }
        return true
    }
}