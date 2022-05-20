package com.example.myapplication.presenter

import android.content.Context
import androidx.room.Room
import com.example.myapplication.R
import com.example.myapplication.room.AppDatabase
import com.example.myapplication.room.BookEntity
import com.example.myapplication.view.ChangeView
import moxy.MvpPresenter

class ChangePresenter(databaseName: String, context: Context) : MvpPresenter<ChangeView>() {

    override fun attachView(view: ChangeView?) {
        super.attachView(view)
    }

    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        databaseName
    ).allowMainThreadQueries().build()

    fun getData(id: Int): BookEntity {
        return db.bookDao().getCurrentBook(id)
    }

    fun showData(id: Int) {
        val book = getData(id)
        viewState.showBook(book = book)
    }

    fun changeData(book: BookEntity) {
        if (checkData(book)) {
            db.bookDao().putBook(book)
            viewState.changeBook()
        } else {
            viewState.showMessage(R.string.error)
        }
    }

    private fun checkData(book: BookEntity): Boolean {
        if (
            book.about.isNotEmpty() &&
            book.uri.isNotEmpty() &&
            book.title.isNotEmpty() &&
            book.year.isNotEmpty() &&
            book.author.isNotEmpty()
        ) {
            return true
        }
        return false
    }
}