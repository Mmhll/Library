package com.example.myapplication.room

import androidx.room.*


@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getBooks() : MutableList<BookEntity>
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getCurrentBook(bookId : Int) : BookEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putBook(book : BookEntity)
    @Query("DELETE FROM books WHERE id = :id")
    fun deleteById(id : Int = 1)
}