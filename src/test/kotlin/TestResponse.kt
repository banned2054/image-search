import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class TestResponse
{
    @SerializedName("name")
    @Expose
    var name : String? = null
    
    @SerializedName("type")
    @Expose
    var type : String? = null
}

class ImageResult
{
    @SerializedName("image")
    @Expose
    var image : String? = null
    
    @SerializedName("state")
    @Expose
    var state : Boolean? = null
}