package top.banned.library.imagesearch.saucenao

class SaucenaoResult
{
    class SaucenaoRaw constructor(var origin : String?,
                                  var similarity : String?,
                                  var thumbnail : String?,
                                  var index_id : Int?,
                                  var index_name : String?,
                                  var title : String?,
                                  var url : String?,
                                  var ext_urls : List<String>?,
                                  var author : String?,
                                  var author_url : String?,
                                  var source : String?)
    
    var items = mutableListOf<SaucenaoRaw>()
}