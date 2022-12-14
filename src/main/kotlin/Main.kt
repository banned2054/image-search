import java.io.File

fun main(args : Array<String>)
{
    val file = File("C:\\Users\\banned\\Desktop\\QQ截图20221214132556.png")
    val s = Saucenao().setApiKey("c08f59a8b869126d75e42e3e36af098f967b71b4")
        .addProxy("127.0.0.1", 7890)
    val abs = s.search(file)
    println(abs.items[0].url)
    println(abs.items.size)
}