package com.example.videoone.model

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import com.example.videoone.network.VideoApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val videoRepositoryModule = module {
    factory { VideoRepository(androidApplication(),get()) }
}

/** simple data manager **/
class VideoRepository(private  val context: Context,private val videoApiService: VideoApiService) {

    var selection = MediaStore.Audio.Media.IS_MUSIC + " != 0"


    // MediaStore.Video.Thumbnails.DATA:视频缩略图的文件路径
    var videoThumbColumns = arrayOf(
        MediaStore.Video.Thumbnails.DATA,
        MediaStore.Video.Thumbnails.VIDEO_ID
    )

    // MediaStore.Video.Media.DATA：视频文件路径；
    var videoColumns = arrayOf(
        MediaStore.Video.Media._ID, MediaStore.Video.Media.DATA,
        MediaStore.Video.Media.DISPLAY_NAME, MediaStore.Video.Media.SIZE,
        MediaStore.Video.Media.MIME_TYPE, MediaStore.Video.Media.DATE_ADDED,
        MediaStore.Video.Media.TITLE, MediaStore.Video.Media.ALBUM,
        MediaStore.Video.Media.BUCKET_ID,
        MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
        MediaStore.Video.Media.WIDTH, MediaStore.Video.Media.HEIGHT
    )

    var cursor = context.contentResolver.query(
        MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
        videoColumns, null, null, MediaStore.Video.Media._ID + " DESC "
    )

    suspend fun getVideos(): Videos = withContext(Dispatchers.IO) {
        videoApiService.getApi().getVideos()
    }



    private fun getColumnIndex(cursor: Cursor, columnName: String): Int{
        return cursor.getColumnIndex(columnName)
    }

    suspend  fun getSongs(): List<Video>{

        val videoList =  mutableListOf<Video>()

        //Get songs from provider
        context.contentResolver.query(
            MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
            videoColumns,
            selection,
            null,
            "${MediaStore.Video.Media.DISPLAY_NAME} ASC" //Sort in alphabetical order based on display name.
        ).use {cursor ->
            if (cursor?.moveToFirst() == true) {
                do {

                    val id = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media._ID))
                    val path = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)))
                    val displayName = (
                        cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                MediaStore.Video.Media.DISPLAY_NAME
                            )
                        )
                    )
                    val size = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE)))
                    val mimeType = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)))
                    val dateAdded = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATE_ADDED)))
                    val title = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)))
                    val album = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.ALBUM)))
                    val bucketId = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID)))
                    val bucketDisplayName = (
                        cursor.getString(
                            cursor.getColumnIndexOrThrow(
                                MediaStore.Video.Media.BUCKET_DISPLAY_NAME
                            )
                        )
                    )
                    val width = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.WIDTH)))
                    val height = (cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.HEIGHT)))

                    val video = Video(
                        mediaId = id,
                        title = title

                    )

                } while (cursor.moveToNext())
            }
        }

        return videoList

    }

}








