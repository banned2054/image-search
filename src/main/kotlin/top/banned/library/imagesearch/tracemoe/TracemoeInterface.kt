package top.banned.library.imagesearch.tracemoe

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TracemoeInterface
{
    @Multipart
    @POST("/search")
    fun TracemoeSearchWithURL() : Call<TracemoeResponse>
    
    @Multipart
    @POST("/search")
    fun TracemoeSearchWithFile(@Part
                               file : MultipartBody.Part) : Call<TracemoeResponse>
}