package com.jozefv.o2app.feat_dashboard.data.o2

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotEmpty
import assertk.assertions.isNull
import com.jozefv.o2app.core.data.o2.O2ApiDefaults.CODE_ACTIVE_STATUS_CODE
import com.jozefv.o2app.core.domain.util.ErrorResult
import com.jozefv.o2app.core.domain.util.ResultHandler
import com.jozefv.o2app.feat_dashboard.domain.util.O2CodeAlias
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Api
import com.jozefv.o2app.feat_dashboard.domain.util.o2.O2Code
import com.jozefv.o2app.util.TestDispatchers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class O2RepositoryTest {

    private lateinit var testDispatchers: TestDispatchers
    private lateinit var o2Repository: O2Repository
    private var fakeAndroidApiValue: Int = 0

    @Before
    fun setUp() {
        testDispatchers = TestDispatchers()
        o2Repository = O2Repository(
            o2Api = object : O2Api {
                override suspend fun activateCode(code: O2CodeAlias): ResultHandler<O2, ErrorResult.NetworkError> {
                    return ResultHandler.Success(O2(fakeAndroidApiValue))
                }
            },
            applicationScope = CoroutineScope(testDispatchers.testDispatcher),
            coroutineDispatcherProvider = testDispatchers
        )
    }

    @Test
    fun testIfCodeStringIsGenerated() = runTest(testDispatchers.testDispatcher) {
        val generatedCode = o2Repository.generateCode()
        assertThat(generatedCode).isNotEmpty()
    }

    @Test
    fun testIfCodeIsSaved() = runTest(testDispatchers.testDispatcher) {
        o2Repository.observeCode().test {
            val o2Code = O2Code(code = "ABC", isActive = false)
            val initialSaveCodeEmission = awaitItem()
            assertThat(initialSaveCodeEmission).isNull()

            o2Repository.saveCode(o2Code.code)
            val savedCodeEmission = awaitItem()
            assertThat(savedCodeEmission).isEqualTo(expected = o2Code)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun testReturnedApiCodeIsBelowThresholdCodeShouldBeInactive() =
        runTest(testDispatchers.testDispatcher) {
            fakeAndroidApiValue = CODE_ACTIVE_STATUS_CODE - 1
            val o2Code = O2Code(code = "ABC", isActive = false)
            o2Repository.observeCode().test {
                val initialSaveCodeEmission = awaitItem()
                assertThat(initialSaveCodeEmission).isNull()

                o2Repository.activateCode(o2Code.code)
                val receivedEmission = awaitItem()
                assertThat(receivedEmission).isEqualTo(expected = o2Code)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun testReturnedApiCodeIsAtThresholdCodeShouldBeInactive() =
        runTest(testDispatchers.testDispatcher) {
            fakeAndroidApiValue = CODE_ACTIVE_STATUS_CODE
            val o2Code = O2Code(code = "ABC", isActive = false)
            o2Repository.observeCode().test {
                val initialSaveCodeEmission = awaitItem()
                assertThat(initialSaveCodeEmission).isNull()

                o2Repository.activateCode(o2Code.code)
                val receivedEmission = awaitItem()
                assertThat(receivedEmission).isEqualTo(expected = o2Code)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun testReturnedApiCodeIsAboveThresholdCodeShouldBeActive() =
        runTest(testDispatchers.testDispatcher) {
            fakeAndroidApiValue = CODE_ACTIVE_STATUS_CODE + 1
            val o2Code = O2Code(code = "ABC", isActive = true)
            o2Repository.observeCode().test {
                val initialSaveCodeEmission = awaitItem()
                assertThat(initialSaveCodeEmission).isNull()

                o2Repository.activateCode(o2Code.code)
                val receivedEmission = awaitItem()
                assertThat(receivedEmission).isEqualTo(expected = o2Code)
                cancelAndConsumeRemainingEvents()
            }
        }

    @Test
    fun testActivateCodeReturnsNoInternetErrorShouldBeReturned() =
        runTest(testDispatchers.testDispatcher) {
            val includedData =
                ResultHandler.Error<ErrorResult.NetworkError>(ErrorResult.NetworkError.NO_INTERNET)
            o2Repository = O2Repository(
                o2Api = object : O2Api {
                    override suspend fun activateCode(code: O2CodeAlias): ResultHandler<O2, ErrorResult.NetworkError> {
                        return includedData
                    }
                },
                applicationScope = CoroutineScope(testDispatchers.testDispatcher),
                coroutineDispatcherProvider = testDispatchers
            )
            val errorResult = o2Repository.activateCode("ABC")
            assertThat(errorResult).isEqualTo(includedData)
        }

    @Test
    fun testActivateCodeReturnSuccessAndInactiveCodeShouldBeReturned() =
        runTest(testDispatchers.testDispatcher) {
            val o2Code = O2Code(code = "ABC", isActive = false)
            o2Repository = O2Repository(
                o2Api = object : O2Api {
                    override suspend fun activateCode(code: O2CodeAlias): ResultHandler<O2, ErrorResult.NetworkError> {
                        return ResultHandler.Success(O2(fakeAndroidApiValue))
                    }
                },
                applicationScope = CoroutineScope(testDispatchers.testDispatcher),
                coroutineDispatcherProvider = testDispatchers
            )
            val successResult = o2Repository.activateCode(o2Code.code)
            assertThat(successResult).isEqualTo(ResultHandler.Success(o2Code))
        }
}