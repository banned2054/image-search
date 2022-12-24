package top.banned.library.imagesearch.saucenao

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
    }
    
    class SaucenaoItemData
    {
        var seiga_id : String? = null
        var nijie_id : String? = null
        var bcy_id : String? = null
        var tweet_id : String? = null
        
        //标题
        var title : String? = null
        var material : String? = null
        var jp_name : String? = null
        var eng_name : String? = null
        var source : String? = null
        var created_at : String? = null
        
        //url
        var pixiv_id : String? = null
        var pawoo_user_acct : String? = null
        var pawoo_id : String? = null
        var getchu_id : String? = null
        var ext_urls : List<String>? = null
        
        //author
        var creator : String? = null
        var author : String? = null
        var member_name : String? = null
        var twitter_user_handle : String? = null
        var pawoo_user_display_name : String? = null
        var author_name : String? = null
        var user_name : String? = null
        var artist : String? = null
        var company : String? = null
        
        //author url
        val member_id : String? = null
        var twitter_user_id : String? = null
        var author_url : String? = null
    }
}
