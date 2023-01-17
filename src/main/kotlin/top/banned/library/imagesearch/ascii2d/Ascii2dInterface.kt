package top.banned.library.imagesearch.ascii2d

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import top.banned.library.imagesearch.saucenao.SaucenaoResponse

interface Ascii2dInterface
{
    @Multipart
    @POST("/search/uri")
    fun saucenaoSearchWithURl(
            @Part
            url : MultipartBody.Part,
                             ) : Call<SaucenaoResponse>
    
    @Multipart
    @POST("/search/file")
    fun saucenaoSearchWithFile(
            @Part
            file : MultipartBody.Part,
                              ) : Call<SaucenaoResponse>
}