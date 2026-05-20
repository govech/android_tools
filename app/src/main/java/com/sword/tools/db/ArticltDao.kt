package com.sword.tools.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sword.tools.bean.ArticleBean

@Dao
interface ArticltDao {

    @Insert
    fun insertArticle(article: ReadedArticle)

    @Update
    fun updateArticle(article: ReadedArticle)

    @Query("select * from  readed_table")
    fun findAll(): List<ReadedArticle>

    @Query("select * from readed_table where id =:id")
    fun findReadedArticle(id: Int): List<ReadedArticle>

}

