package saucenao

class SaucenaoResponse
{
    var header : Map<String, Any>? = null
    var results : List<SaucenaoItem>? = null
    
    /**
     * 搜索结果
     */
    class SaucenaoItem
    {
        var header : SaucenaoItemHeader? = null
        var data : SaucenaoItemData? = null
    }
    
    /**
     * header里有图片的缩略图地址
     */
    class SaucenaoItemHeader
    {
        var similarity : String? = null
        var thumbnail : String? = null
        var index_id : Int? = null
        var index_name : String? = null
        var dupes : Int? = null
        var hidden : Int? = null
    }
    
    class SaucenaoItemData
    {
        var ext_urls : List<String>? = null
        var danbooru_id : Int? = null
        var gelbooru_id : Int? = null
        var creator : Any? = null
        var material : String? = null
        var characters : String? = null
        var source : String? = null
        var pixiv_data : String? = null
        var member_name : String? = null
        var member_id : Int? = null
        var title : String? = null
    }
}
