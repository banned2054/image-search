package top.banned.library.imagesearch

import com.google.gson.Gson
import okhttp3.*
import retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import top.banned.library.imagesearch.saucenao.SaucenaoInterface
import top.banned.library.imagesearch.saucenao.SaucenaoResponse
import top.banned.library.imagesearch.saucenao.SaucenaoResult
import top.banned.library.imagesearch.util.makePart
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy
import java.util.concurrent.CompletableFuture


/**
 *SauceNAO
 *-----------
 *Reverse image from https://saucenao.com\n
 *
 *
 *Params Keys
 *-----------
 *api_key: (str) Access key for SauceNAO (default=None)
 *
 *output_type: (int) 0=normal (default) html 1=xml api (not implemented) 2=json api default=2
 *
 *testmode: (int) Test mode 0=normal 1=test (default=0)
 *
 *numres: (int) output number (default=5)
 *
 *dbmask: (int) The mask used to select the specific index to be enabled (default=None)
 *
 *dbmaski: (int) is used to select the mask of the specific index to be disabled (default=None)
 *
 *db: (int) Search for a specific index number or all indexes (default=999), see https://saucenao.com/tools/examples/api/index_details.txt
 *
 *dbs: (list) Search for specific indexes number or all indexes (default=None), see https://saucenao.com/tools/examples/api/index_details.txt
 *
 *minsim: (int) Control the minimum similarity (default=30)
 *
 *hide: (int) result hiding control, 0=show all, 1=hide expected explicit, 2=hide expected and suspected explicit, 3=hide all but expected safe. Default is 0.
 
 */

/**
 * 使用saucenao库， https://saucenao.com
 
 * 要注册账号获取api key，才能使用它的api。
 
 *注册：https://saucenao.com/user.php
 
 *api key：https://saucenao.com/user.php?page=search-api
 
 *获取api key后使用addApiKey函数添加到对象里
 */
class Saucenao
{
    //是否使用代理
    private var _useProxy : Boolean = false
    
    //代理的地址
    private var _proxyUrl : String = ""
    
    //代理的端口
    private var _port : Int = 0
    
    //api
    private var _apiKey : String = ""
    
    //最小相似度
    private var _minSim : Int = 30
    
    //输出格式，2为json
    private var _outputType : Int = 2
    
    //数据库
    private var _db : Int = 999
    
    //是否是测试状态
    private var _testMode : Int = 0
    
    //输出结果个数
    private var _numRes : Int = 5
    
    
    /**
     * 添加代理，saucenao被墙了
     */
    fun addProxy(url : String, port : Int) : Saucenao
    {
        _useProxy = true
        _proxyUrl = url
        _port = port
        return this
    }
    
    /**
     * 设置api key，不然无法使用
     */
    fun setApiKey(apiKey : String) : Saucenao
    {
        _apiKey = apiKey
        return this
    }
    
    /**
     * 设置最小相似度,默认为30
     */
    fun setMinsim(minsim : Int) : Saucenao
    {
        _minSim = minsim
        return this
    }
    
    @JvmField
    val OUTPUT_TYPE_HTML = 0
    
    @JvmField
    val OUTPUT_TYPE_XML = 1
    
    
    @JvmField
    val OUTPUT_TYPE_JSON = 2
    
    /**
     * 输出格式，默认为[OUTPUT_TYPE_JSON]，
     *
     * 输出格式可以为：
     * - [OUTPUT_TYPE_HTML]
     *
     * - [OUTPUT_TYPE_XML]
     *
     * - [OUTPUT_TYPE_JSON]
     */
    fun setOutputType(outputType : Int) : Saucenao
    {
        _outputType = outputType
        return this
    }
    
    /**
     * 数据库，默认为999（所有数据库）
     */
    fun setDB(db : Int) : Saucenao
    {
        _db = db
        return this
    }
    
    /**
     * 输出结果个数，默认为5
     */
    fun setNumRes(numRes : Int) : Saucenao
    {
        _numRes = numRes
        return this
    }
    
    @Throws(IOException::class)
    fun search(url : String) : Any
    {
        if (_apiKey == "")
        {
            throw IOException("缺少api key")
        }
    
        val request = getRequest()
    
        val call = request.saucenaoSearchWithURl(
                makePart("testmode", _testMode),
                makePart("numres", _numRes),
                makePart("output_type", _outputType),
                makePart("hide", 0),
                makePart("db", _db),
                makePart("minsim", _minSim),
                makePart("api_key", _apiKey),
                makePart("url", url)
                                                )
    
    
        val ans = getCallBack(call)
        if (ans.startsWith("success "))
        {
            if (_outputType == 2)
            {
                val response = Gson().fromJson(ans.substring(8), SaucenaoResponse::class.java)
                return getAns(response.results!!)
            }
            return ans
        }
        throw Exception(ans.substring(6))
    }
    
    @Throws(IOException::class)
    fun search(file : File) : Any
    {
        if (_apiKey == "")
        {
            throw IOException("缺少api key")
        }
        
        val request = getRequest()
        
        val call = request.saucenaoSearchWithFile(
                makePart("testmode", _testMode),
                makePart("numres", _numRes),
                makePart("output_type", _outputType),
                makePart("hide", 0),
                makePart("db", _db),
                makePart("minsim", _minSim),
                makePart("api_key", _apiKey),
                makePart("file", file)
                                                 )
        
        val ans = getCallBack(call)
        if (ans.startsWith("success "))
        {
            if (_outputType == 2)
            {
                val response = Gson().fromJson(ans.substring(8), SaucenaoResponse::class.java)
                return getAns(response.results!!)
            }
            return ans
        }
        throw Exception(ans.substring(6))
    }
    
    private fun getRequest() : SaucenaoInterface
    {
        var retrofit = Retrofit.Builder().baseUrl("https://saucenao.com")
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
        if (_useProxy)
        {
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(_proxyUrl, _port))
            val client : OkHttpClient = OkHttpClient.Builder().proxy(proxy).build()
            retrofit = retrofit.client(client)
        }
    
        return retrofit.build().create(SaucenaoInterface::class.java)
    }
    
    private fun getCallBack(call : Call<ResponseBody>) : String
    {
        val ansFuture = CompletableFuture<String>()
        call.enqueue(object : Callback<ResponseBody>
                     {
                         override fun onResponse(call : Call<ResponseBody>, response : Response<ResponseBody>)
                         {
                             if (response.isSuccessful)
                             {
                                 val ans = response.body()!!.string()
                                 ansFuture.complete("success $ans")
                             }
                         }
            
                         override fun onFailure(call : Call<ResponseBody>, t : Throwable)
                         {
                             ansFuture.complete("wrong " + t.message)
                         }
                     }
                    )
        return ansFuture.get()!!
    }
    
    private fun getAns(items : List<SaucenaoResponse.SaucenaoItem>) : SaucenaoResult
    {
        val ans = SaucenaoResult()
        for (item in items)
        {
            val thumbnail = item.header?.thumbnail!!
            val url = getUrl(item)
            if (item.header == null || item.data == null) continue
            if (url == "" || thumbnail == "") continue
            val newRaw = SaucenaoResult.SaucenaoRaw(
                    Gson().toJson(item),
                    item.header!!.similarity,
                    item.header!!.thumbnail,
                    item.header!!.index_id,
                    item.header!!.index_name,
                    getTitle(item),
                    getUrl(item),
                    item.data!!.ext_urls,
                    getAuthor(item),
                    getAuthorUrl(item),
                    item.data!!.source
                                                   )
            ans.items.add(newRaw)
        }
        return ans
    }
    
    private fun getUrl(item : SaucenaoResponse.SaucenaoItem) : String
    {
        if (item.data!!.pixiv_id != null) if (item.data!!.pixiv_id != "")
        {
            return "https://www.pixiv.net/artworks/" + item.data!!.pixiv_id.toString()
        }
        if (item.data!!.pawoo_user_acct != null) if (item.data!!.pawoo_user_acct != "")
        {
            if (item.data!!.pawoo_id != "")
            {
                return "https://pawoo.net/@" + item.data!!.pawoo_user_acct + "/" + item.data!!.pawoo_id
            }
        }
        if (item.data!!.getchu_id != null) if (item.data!!.getchu_id != "")
        {
            return "https://www.getchu.com/soft.phtml?id=" + item.data!!.getchu_id
        }
        if (item.data!!.ext_urls != null)
        {
            if (item.data!!.ext_urls!!.isNotEmpty())
            {
                return item.data!!.ext_urls!![0]
            }
        }
        return ""
    }
    
    private fun getAuthorUrl(item : SaucenaoResponse.SaucenaoItem) : String
    {
        if (item.data!!.pixiv_id != null) if (item.data!!.member_id != "")
        {
            return "https://www.pixiv.net/user/" + item.data!!.member_id.toString()
        }
        if (item.data!!.seiga_id != null) if (item.data!!.member_id != "")
        {
            return "https://seiga.nicovideo.jp/user/illust/" + item.data!!.member_id.toString()
        }
        if (item.data!!.nijie_id != null) if (item.data!!.member_id != "")
        {
            return "https://nijie.info/members.php?id=" + item.data!!.member_id
        }
        if (item.data!!.bcy_id != null) if (item.data!!.member_id != "")
        {
            return "https://bcy.net/u/" + item.data!!.member_id
        }
        if (item.data!!.tweet_id != null) if (item.data!!.twitter_user_id != "")
        {
            return "https://twitter.com/intent/user?user_id=" + item.data!!.twitter_user_id
        }
        if (item.data!!.pawoo_user_acct != null) if (item.data!!.pawoo_user_acct != "")
        {
            return "https://pawoo.net/@" + item.data!!.pawoo_user_acct
        }
        if (item.data!!.author_url != null) if (item.data!!.author_url != "")
        {
            return item.data!!.author_url!!
        }
        return ""
    }
    
    private fun getTitle(item : SaucenaoResponse.SaucenaoItem) : String
    {
        if (item.data!!.title != null)
        {
            if (item.data!!.title != "")
            {
                return item.data!!.title!!
            }
        }
        if (item.data!!.material != null)
        {
            if (item.data!!.material != "")
            {
                return item.data!!.material!!
            }
        }
        
        if (item.data!!.jp_name != null)
        {
            if (item.data!!.jp_name != "")
            {
                return item.data!!.jp_name!!
            }
        }
        
        if (item.data!!.eng_name != null)
        {
            if (item.data!!.eng_name != "")
            {
                return item.data!!.eng_name!!
            }
        }
        
        
        if (item.data!!.source != null)
        {
            if (item.data!!.source != "")
            {
                return item.data!!.source!!
            }
        }
        if (item.data!!.created_at != null)
        {
            if (item.data!!.created_at != "")
            {
                return item.data!!.created_at!!
            }
        }
        return ""
    }
    
    private fun getAuthor(item : SaucenaoResponse.SaucenaoItem) : String
    {
        if (item.data!!.creator != null) if (item.data!!.creator != "") return item.data!!.creator!!
        if (item.data!!.author != null) if (item.data!!.author != "") return item.data!!.author!!
        if (item.data!!.member_name != null) if (item.data!!.member_name != "") return item.data!!.member_name!!
        if (item.data!!.twitter_user_handle != null) if (item.data!!.twitter_user_handle != "") return item.data!!.twitter_user_handle!!
        if (item.data!!.pawoo_user_display_name != null) if (item.data!!.pawoo_user_display_name != "") return item.data!!.pawoo_user_display_name!!
        if (item.data!!.author_name != null) if (item.data!!.author_name != "") return item.data!!.author_name!!
        if (item.data!!.user_name != null) if (item.data!!.user_name != "") return item.data!!.user_name!!
        if (item.data!!.artist != null) if (item.data!!.artist != "") return item.data!!.artist!!
        if (item.data!!.company != null) if (item.data!!.company != "") return item.data!!.company!!
        return ""
    }
}