package com.example.myapplication

import androidx.room.*


@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    fun getBooks() : Array<BookEntity>
    @Query("SELECT * FROM books WHERE id = :bookId")
    fun getCurrentBook(bookId : Int) : BookEntity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun putBook(book : BookEntity)
    @Delete(entity = BookEntity::class)
    fun deleteById(vararg id : DeleteId)
}

data class DeleteId(val id : Int)