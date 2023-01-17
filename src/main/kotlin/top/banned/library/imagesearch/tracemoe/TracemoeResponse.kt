package top.banned.library.imagesearch.tracemoe

class TracemoeResponse
{
    var frameCount : Int? = null
    var error : String? = null
    var result : List<TracemoeItem>? = null
    
    class TracemoeItem
    {
        var anilist : Int? = null
        var filename : String? = null
        var episode : Int? = null
        var from : Float? = null
        var to : Float? = null
        var similarity : Float? = null
        var video : String? = null
        var image : String? = null
    }
}