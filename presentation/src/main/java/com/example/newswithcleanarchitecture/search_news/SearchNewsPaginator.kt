package com.example.newswithcleanarchitecture.search_news

import android.annotation.SuppressLint
import com.example.data.Constants
import com.example.domain.model.Article
import com.example.domain.use_case.SearchNewsUseCase
import com.example.domain.util.Resource
import com.example.newswithcleanarchitecture.util.NewsPaginator

class SearchNewsPaginator(
    private val getSearchedNewsUseCase: SearchNewsUseCase,
    private val initialKey: Int,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val getNextKey: suspend () -> Int,
    private inline val onError: suspend (String) -> Unit,
    private inline val onSuccess: suspend (Items:List<Article>, currentPage: Int, newKey: Int, totalPages: Int) -> Unit
): NewsPaginator<Int, Article> {

    private var currentKey = initialKey

    private var searchQuery = ""

    private var isMakingRequest = false

    @SuppressLint("SuspiciousIndentation")
    override suspend fun loadNextItems() {
        if(isMakingRequest) {
            return
        }
        isMakingRequest = true

        getSearchedNewsUseCase(searchQuery = searchQuery, page = currentKey)
            .collect { result ->

                when (result) {
                    is Resource.Success -> {
                        isMakingRequest = false
                        onLoadUpdated(false)
                        currentKey = getNextKey()

                        result.data?.totalResults?.let { totalresults ->

                            var totalPages= totalresults / Constants.SEARCHINGNEWS_PAGE_SIZE
                            if(totalresults % Constants.SEARCHINGNEWS_PAGE_SIZE > 0) {
                                totalPages++
                            }
                            onSuccess((result.data?.articles ?: emptyList()), currentKey-1, currentKey, totalPages)
                        }
                    }
                    is Resource.Error -> {
                        isMakingRequest = false
                        onLoadUpdated(false)
                        onError(result.message ?: "An unexpected error occured")

                    }
                    is Resource.Loading -> {
                        onLoadUpdated(true)//뷰모델에서 가지는 state값의 loading여부값 업데이트
                    }
                }
            }
    }
    fun setSearchQuery(queryString: String) {
        searchQuery = queryString
        reset()
    }
    override fun reset() {
        currentKey = initialKey
    }
}