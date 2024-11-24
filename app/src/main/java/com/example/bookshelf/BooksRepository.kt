package com.example.bookshelf

class BooksRepository(private val apiService: BooksApiService) {
    suspend fun searchBooks(query: String): List<BookItem> {
        return apiService.searchBooks(query).items.orEmpty()
    }
}
