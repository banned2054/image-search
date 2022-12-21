package saucenao

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*
import saucenao.SaucenaoResponse


interface SaucenaoInterface
{
    @Multipart
    @POST("/search.php")
    fun saucenaoSearchWithURl(
            @Part
            testMode : MultipartBody.Part,
            @Part
            numres : MultipartBody.Part,
            @Part
            outputType : MultipartBody.Part,
            @Part
            hide : MultipartBody.Part,
            @Part
            db : MultipartBody.Part,
            @Part
            minsim : MultipartBody.Part,
            @Part
            apiKey : MultipartBody.Part,
            @Part
            url : MultipartBody.Part,
                             ) : Call<SaucenaoResponse>
    
    @Multipart
    @POST("/search.php")
    fun saucenaoSearchWithFile(
            @Part
            testMode : MultipartBody.Part,
            @Part
            numres : MultipartBody.Part,
            @Part
            outputType : MultipartBody.Part,
            @Part
            hide : MultipartBody.Part,
            @Part
            db : MultipartBody.Part,
            @Part
            minsim : MultipartBody.Part,
            @Part
            apiKey : MultipartBody.Part,
            @Part
            url : MultipartBody.Part,
                              ) : Call<SaucenaoResponse>
}