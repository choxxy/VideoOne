package com.example.videoone.network;

import android.content.Context
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
import java.io.InputStream
import java.util.*


/**
 * OkHttp3 interceptor which provides a mock response from local resource file.
 */
class MockResponseInterceptor(private val context: Context) : Interceptor {

    private val endpoint: String = "video"


    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        // Get resource ID for mock response file.
        var fileName = getFilename(chain.request(), endpoint)
        var resourceId = getResourceId(fileName)
        if (resourceId == 0) {
            // Attempt to fallback to default mock response file.
            fileName = getFilename(chain.request(), null)
            resourceId = getResourceId(fileName)
            if (resourceId == 0) {
                throw IOException("Could not find res/raw/$fileName")
            }
        }

        // Get input stream and mime type for mock response file.
        val inputStream: InputStream = context.resources.openRawResource(resourceId)
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val mimeType = "application/json"

        // Build and return mock response.
        return Response.Builder()
            .addHeader("content-type", mimeType)
            .body(
               ResponseBody.create(
                   mimeType.toMediaTypeOrNull(),
                   buffer
                )
            )
            .code(200)
            .message("Mock response from res/raw/$fileName")
            .protocol(Protocol.HTTP_1_0)
            .request(chain.request())
            .build()
    }

    @Throws(IOException::class)
    private fun getFilename(request: Request, endpoint: String?): String {
        val requestedMethod: String = request.method
        val prefix = if (endpoint == null) "" else endpoint + "_"
        var filename = prefix + requestedMethod + request.url.toUrl().path
        filename = filename.replace("/", "_").replace("-", "_").toLowerCase(Locale.getDefault())
        return filename
    }

    private fun getResourceId(filename: String): Int {
        return context.resources.getIdentifier(filename, "raw", context.packageName)
    }

}