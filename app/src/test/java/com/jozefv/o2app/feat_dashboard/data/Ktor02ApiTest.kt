package com.jozefv.o2app.feat_dashboard.data

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.jozefv.o2app.util.TestDispatchers
import com.jozefv.o2app.core.data.HttpClientFactory
import com.jozefv.o2app.core.data.o2.O2ApiDefaults.QUERY_CODE_PARAM
import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.feat_dashboard.data.o2.Ktor02Api
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class Ktor02ApiTest {
    private lateinit var testDispatchers: TestDispatchers

    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
    }

    @Test
    fun apiReceivedCorrectParameters() = runTest(testDispatchers.testDispatcher) {
        val code = "ABC"
        val mockHttpClient = HttpClientFactory.build(
            MockEngine { request ->
                assertThat(request.url.parameters[QUERY_CODE_PARAM]).isEqualTo(code)
                respond("")
            }
        )
        val ktorO2Api = Ktor02Api(
            httpClient = mockHttpClient,
            coroutineDispatcherProvider = testDispatchers
        )
        ktorO2Api.activateCode(code)
    }

    @Test
    fun apiSentInvalidResponseShouldReturnError() = runTest(testDispatchers.testDispatcher) {
        val code = "ABC"
        val exampleResponse = "{ \"android\": \"this should be int\" }"
        val mockHttpClient = HttpClientFactory.build(
            MockEngine {
                respond(
                    content = exampleResponse,
                    headers = headersOf(
                        name = HttpHeaders.ContentType,
                        value = ContentType.Application.Json.toString(),
                    ),
                    status = HttpStatusCode.OK
                )
            }
        )
        val ktorO2Api = Ktor02Api(
            httpClient = mockHttpClient,
            coroutineDispatcherProvider = testDispatchers
        )
        val response = ktorO2Api.activateCode(code)
        assertThat(response).isEqualTo(ResultHandler.Error(ErrorResult.NetworkError.SERIALIZATION))
    }

    @Test
    fun apiSentStatusInvalidShouldReturnError() = runTest(testDispatchers.testDispatcher) {
        val code = "ABC"
        val exampleResponse = "{ \"android\": \"287028\" }"
        val mockHttpClient = HttpClientFactory.build(
            MockEngine {
                respond(
                    content = exampleResponse,
                    headers = headersOf(
                        name = HttpHeaders.ContentType,
                        value = ContentType.Application.Json.toString()
                    ),
                    status = HttpStatusCode.TooManyRequests
                )
            }
        )
        val ktorO2Api = Ktor02Api(
            httpClient = mockHttpClient,
            coroutineDispatcherProvider = testDispatchers
        )
        val response = ktorO2Api.activateCode(code)
        assertThat(response).isEqualTo(ResultHandler.Error(ErrorResult.NetworkError.TOO_MANY_REQUESTS))
    }

    @Test
    fun apiSentValidResponseShouldReturnSuccess() = runTest(testDispatchers.testDispatcher) {
        val code = "ABC"
        val responseCode = "287028"
        val exampleResponse = "{ \"android\": \"$responseCode\" }"
        val mockHttpClient = HttpClientFactory.build(
            MockEngine {
                respond(
                    content = exampleResponse,
                    headers = headersOf(
                        name = HttpHeaders.ContentType,
                        value = ContentType.Application.Json.toString()
                    ),
                    status = HttpStatusCode.OK
                )
            }
        )
        val ktorO2Api = Ktor02Api(
            httpClient = mockHttpClient,
            coroutineDispatcherProvider = testDispatchers
        )
        val response = ktorO2Api.activateCode(code)
        assertThat(response).isEqualTo(
            ResultHandler.Success(O2(responseCode.toInt()))
        )
    }
}