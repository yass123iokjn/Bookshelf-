package com.example.bookshelf

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.bookshelf.ui.theme.BookshelfTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Création d'une instance du BooksRepository
        val apiService = ApiClient.retrofit.create(BooksApiService::class.java)
        val repository = BooksRepository(apiService)

        // Initialisation du ViewModel via ViewModelProvider
        val viewModel = ViewModelProvider(this, BooksViewModelFactory(repository))
            .get(BooksViewModel::class.java)

        // Appel initial à la méthode de recherche
        viewModel.searchBooks("android") // Vous pouvez changer la requête par défaut ici

        setContent {
            BookshelfTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    BooksScreen(viewModel = viewModel)
                }
            }
        }
    }
}

// Factory pour instancier BooksViewModel avec le BooksRepository
class BooksViewModelFactory(private val repository: BooksRepository) :
    androidx.lifecycle.ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BooksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BooksViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
