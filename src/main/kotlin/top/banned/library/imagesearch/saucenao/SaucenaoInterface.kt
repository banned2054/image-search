package top.banned.library.imagesearch.saucenao

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


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
                             ) : Call<ResponseBody>
    
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
            file : MultipartBody.Part,
                              ) : Call<ResponseBody>
}