package com.jozefv.o2app.core.data

import com.jozefv.o2app.core.data.o2.O2ApiDefaults.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun build(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(Logging) {
                level = LogLevel.ALL // Log everything of our request
            }
            install(ContentNegotiation) {
                // Library for parsing data
                json(
                    json = Json {
                        // if the JSON response includes field which we don't have parse for, ignore it
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                url(BASE_URL)
            }
        }
    }
}