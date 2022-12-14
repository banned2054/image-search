import com.google.gson.Gson

class TT
{

}

fun main(args : Array<String>)
{
    class test
    {
        val a : Any? = null
    }
    
    val origin = "{\"creator\": [\n" + "                    \"Unknown\"\n" + "                ]}"
    println(origin)
    val a = Gson().fromJson(origin, test::class.java)
    if (a == null) println("null1")
    if (a.a == null) println("null2")
}