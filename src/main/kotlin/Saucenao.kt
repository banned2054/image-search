import okhttp3.*
import retrofit2.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import saucenao.SaucenaoResponse
import saucenao.SaucenaoResult
import searchInterface.SaucenaoInterface
import searchInterface.makePart
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
    public fun addProxy(url : String, port : Int) : Saucenao
    {
        _useProxy = true
        _proxyUrl = url
        _port = port
        return this
    }
    
    /**
     * 设置api key，不然无法使用
     */
    public fun setApiKey(apiKey : String) : Saucenao
    {
        _apiKey = apiKey
        return this
    }
    
    /**
     * 设置最小相似度,默认为30
     */
    public fun setMinsim(minsim : Int) : Saucenao
    {
        _minSim = minsim
        return this
    }
    
    /**
     * 输出格式，默认为2，输出格式如下
     *
     * 0:html 1:xml 2:json
     */
    public fun setOutputType(outputType : Int) : Saucenao
    {
        _outputType = outputType
        return this
    }
    
    /**
     * 数据库，默认为999（所有数据库）
     */
    public fun setDB(db : Int) : Saucenao
    {
        _db = db
        return this
    }
    
    /**
     * 输出结果个数，默认为5
     */
    public fun setNumRes(numRes : Int) : Saucenao
    {
        _numRes = numRes
        return this
    }
    
    @Throws(IOException::class)
    public fun search(url : String) : SaucenaoResult?
    {
        val ansFuture = CompletableFuture<SaucenaoResult>();
        if (_apiKey == "")
        {
            throw IOException("缺少api key")
        }
        var retrofit = Retrofit.Builder()
            .baseUrl("https://saucenao.com")
            .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
        if (_useProxy)
        {
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(_proxyUrl, _port))
            val client : OkHttpClient = OkHttpClient.Builder()
                .proxy(proxy)
                .build()
            retrofit = retrofit.client(client)
        }
        
        val request = retrofit.build()
            .create(SaucenaoInterface::class.java)
        
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
        
        call.enqueue(object : Callback<SaucenaoResponse>
                     {
                         override fun onResponse(call : Call<SaucenaoResponse>, response : Response<SaucenaoResponse>)
                         {
                             if (response.isSuccessful)
                             {
                                 val body = response.body()
                                 if (body != null)
                                 {
                                     val ans = getAns(body.results!!)
                                     ansFuture.complete(ans)
                                 }
                             }
                         }
            
                         override fun onFailure(call : Call<SaucenaoResponse>, t : Throwable)
                         {
                             ansFuture.completeExceptionally(t)
                         }
                     }
        
                    )
        return ansFuture.get()
    }
    
    @Throws(IOException::class)
    public fun search(file : File) : SaucenaoResult
    {
        val ansFuture = CompletableFuture<SaucenaoResult>();
        if (_apiKey == "")
        {
            throw IOException("缺少api key")
        }
        var retrofit = Retrofit.Builder()
            .baseUrl("https://saucenao.com")
            .addConverterFactory(GsonConverterFactory.create())
        if (_useProxy)
        {
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(_proxyUrl, _port))
            val client : OkHttpClient = OkHttpClient.Builder()
                .proxy(proxy)
                .build()
            retrofit = retrofit.client(client)
        }
        
        val request = retrofit.build()
            .create(SaucenaoInterface::class.java)
        
        
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
        call.enqueue(object : Callback<SaucenaoResponse>
                     {
                         override fun onResponse(call : Call<SaucenaoResponse>, response : Response<SaucenaoResponse>)
                         {
                             if (response.isSuccessful)
                             {
                                 val body = response.body()
                                 if (body != null)
                                 {
                                     val ans = getAns(body.results!!)
                                     ansFuture.complete(ans)
                                 }
                             }
                         }
            
                         override fun onFailure(call : Call<SaucenaoResponse>, t : Throwable)
                         {
                             ansFuture.completeExceptionally(t)
                         }
                     }
        
                    )
        return ansFuture.get()
    }
    
    private fun getAns(items : List<SaucenaoResponse.SaucenaoItem>) : SaucenaoResult
    {
        val ans = SaucenaoResult()
        for (item in items)
        {
            val thumbnail = item.header?.thumbnail!!
            var url = ""
            if (item.data?.source != null)
            {
                if (item.data!!.source != "")
                {
                    url = item.data!!.source!!
                }
            }
            else
            {
                if (item.data?.ext_urls != null)
                {
                    if (item.data!!.ext_urls?.size!! > 0)
                    {
                        url = item.data!!.ext_urls?.get(0)!!
                    }
                }
                else
                {
                    continue
                }
            }
            val newRaw = SaucenaoResult.SaucenaoRaw(url, thumbnail)
            ans.items.add(newRaw)
        }
        return ans
    }
}