package searchInterface

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

fun makePart(key : String, value : String) : MultipartBody.Part
{
    return MultipartBody.Part.createFormData(key, value)
}

fun makePart(key : String, value : Int) : MultipartBody.Part
{
    return MultipartBody.Part.createFormData(key, value.toString())
}

fun makePart(key : String, file : File) : MultipartBody.Part
{
    val requestFile : RequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
    
    return MultipartBody.Part.createFormData("file", file.name, requestFile)
}