import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface TestInterface
{
    @POST("/")
    fun testConnection(@Body
                       test : TestRequest) : Call<TestResponse>
    
    
}