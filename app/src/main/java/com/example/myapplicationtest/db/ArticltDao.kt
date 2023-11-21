package com.example.myapplicationtest.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.myapplicationtest.bean.ArticleBean

@Dao
interface ArticltDao {

    @Insert
    fun insertArticle(article: ReadedArticle)

    @Update
    fun updateArticle(article: ReadedArticle)

    @Query("select * from ReadedArticle")
    fun findAll(): List<ReadedArticle>

    @Query("select * from ReadedArticle where id =:id")
    fun findReadedArticle(id: Int): List<ReadedArticle>


}

