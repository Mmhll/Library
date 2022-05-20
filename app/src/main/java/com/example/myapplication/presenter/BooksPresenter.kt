package com.example.myapplication.presenter

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.view.BooksView
import moxy.MvpPresenter

class BooksPresenter(context : Context, databaseName : String) : MvpPresenter<BooksView>() {




    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        databaseName
    ).allowMainThreadQueries().build()


    fun getBooks() {
        val dao = db.bookDao()
        val books = dao.getBooks()
        if (books.isNotEmpty()) {
            viewState.fillRecycler(data = books)
        }
        else {
            viewState.booksIsEmpty()
        }
    }

    fun getManual(){
        viewState.showManual(R.string.help)
    }

    fun deleteBook(id : Int){
        db.bookDao().deleteById(id)
    }

    override fun attachView(view: BooksView?) {
        super.attachView(view)
        getBooks()
    }
}