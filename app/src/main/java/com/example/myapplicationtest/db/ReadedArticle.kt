package com.example.myapplicationtest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ReadedArticle(
    val link: String,
    val id: Int,
    val isReaded: Boolean,
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,
)
