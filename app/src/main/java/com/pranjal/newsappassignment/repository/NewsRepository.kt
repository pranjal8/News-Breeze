package com.pranjal.newsappassignment.repository

import com.pranjal.newsappassignment.api.RetrofitInstance
import com.pranjal.newsappassignment.db.ArticleDatabase
import com.pranjal.newsappassignment.models.Article

class NewsRepository(private val db:ArticleDatabase) {

    suspend fun getBreakingNews(countryCode: String , pageNumber :Int) =
            RetrofitInstance.api.getBreakingNews(countryCode, pageNumber)

   suspend fun searchNews(searchQuery: String, pageNumber: Int) =
           RetrofitInstance.api.searchForNews(searchQuery, pageNumber)


    suspend fun upser(article: Article) =db.getArticleDao().upsert(article)

    suspend fun deleteArticle(article: Article) =db.getArticleDao().deleteArticle(article)

    fun getSavedNews()=db.getArticleDao().getAllArticles()
}