package com.example.newswithcleanarchitecture.breaking_news

import android.annotation.SuppressLint
import com.example.data.Constants.BREAKINGNEWS_PAGE_SIZE
import com.example.domain.model.Article
import com.example.domain.use_case.GetBreakingNewsUseCase
import com.example.domain.util.Resource
import com.example.newswithcleanarchitecture.util.NewsPaginator

class BreakingNewsPaginator (
    private val getBreakingNewsUseCase: GetBreakingNewsUseCase,
    private val initialKey: Int,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val getNextKey: suspend () -> Int,
    private inline val onError: suspend (String) -> Unit,
    private inline val onSuccess: suspend (Items:List<Article>, currentPage: Int, newKey: Int, totalPages: Int) -> Unit
): NewsPaginator<Int, Article> {
    // inline 키워드를 사용항여 람다 함수를 인자로 받게되면서 객체를 생성하지않고 컴파일러가 코드를 복사해줌으로써 메모리 절약

    private var currentKey = initialKey

    private var isMakingRequest = false

    @SuppressLint("SuspiciousIndentation")
    override suspend fun loadNextItems() {
        if(isMakingRequest) {
            return
        }
        isMakingRequest = true

        getBreakingNewsUseCase(currentKey)
            .collect { result ->

                when (result) {
                    is Resource.Success -> {
                        isMakingRequest = false
                        onLoadUpdated(false)
                        currentKey = getNextKey()

                        result.data?.totalResults?.let { totalresults ->
                            var totalPages= totalresults / BREAKINGNEWS_PAGE_SIZE
                            if(totalresults % BREAKINGNEWS_PAGE_SIZE > 0) {
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
    override fun reset() {
        currentKey = initialKey
    }
}