package com.example.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Books")
data class BookEntity(
    @PrimaryKey var id : Int,
    @ColumnInfo(name = "title") var title : String,
    @ColumnInfo(name = "img_source") var uri : String,
    @ColumnInfo(name = "about") var about : String,
    @ColumnInfo(name = "author") var author : String,
    @ColumnInfo(name = "year") var year : String
)
