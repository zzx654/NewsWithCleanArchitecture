package com.example.domain.use_case

import com.example.domain.Service.Companion.BREAKINGNEWS_PAGE_SIZE
import com.example.domain.repository.FakeNewsRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class BreakingNewsUseCaseTest {


    private lateinit var breakingNewsUseCase: BreakingNewsUseCase

    private lateinit var fakeNewsRepository: FakeNewsRepository

    @Before
    fun setUp() {
        fakeNewsRepository = FakeNewsRepository()
        breakingNewsUseCase = BreakingNewsUseCase(fakeNewsRepository)

    }
    @Test
    fun `Api call Success Test`() = runBlocking {

        val apiResponse = breakingNewsUseCase(page = 1).first()


        assertThat(apiResponse).isInstanceOf(Resource.Success::class.java)

        assertThat(apiResponse.data).isNotNull()

        assertThat(apiResponse.data?.articles).isNotNull()

        assertThat(apiResponse.data?.articles?.size).isEqualTo(BREAKINGNEWS_PAGE_SIZE)


    }



}