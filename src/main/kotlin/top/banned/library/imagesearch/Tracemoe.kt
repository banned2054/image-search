package top.banned.library.imagesearch

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import top.banned.library.imagesearch.tracemoe.TracemoeInterface
import java.io.File
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Proxy

class Tracemoe
{
    /**
     * param size: preview video/image size(can be:s/m/l)(small/medium/large)
     *
     * param mute: mute the preview video（default:False）
     */
    
    
    //视频静音
    private var _mute : Boolean = false
    
    //视频/图片尺寸
    private var _size : String? = null
    
    //是否使用代理
    private var _useProxy : Boolean = false
    
    //代理的地址
    private var _proxyUrl : String = ""
    
    //代理的端口
    private var _port : Int = 0
    
    /**
     * 添加代理
     */
    fun addProxy(url : String, port : Int) : Tracemoe
    {
        _useProxy = true
        _proxyUrl = url
        _port = port
        return this
    }
    
    /**
     * 视频设置为静音
     */
    fun setMute() : Tracemoe
    {
        _mute = true
        return this
    }
    
    /**
     * 设置视频/图片尺寸(s/m/l)对应small/medium/large
     */
    @Throws(IOException::class)
    fun setSize(size : String) : Tracemoe
    {
        if (size == "s" || size == "m" || size == "l")
            _size = size
        else
        {
            throw IOException("尺寸错误")
        }
        return this
    }
    
    @Throws(IOException::class)
    fun search(file : File)
    {
    
    }
    
    
    private fun getRequest() : TracemoeInterface
    {
        var retrofit = Retrofit.Builder().baseUrl("https://api.trace.moe")
                .addConverterFactory(GsonConverterFactory.create()) //设置数据解析器
        if (_useProxy)
        {
            val proxy = Proxy(Proxy.Type.HTTP, InetSocketAddress(_proxyUrl, _port))
            val client : OkHttpClient = OkHttpClient.Builder().proxy(proxy).build()
            retrofit = retrofit.client(client)
        }
        
        val request = retrofit.build().create(TracemoeInterface::class.java)
        return request
    }
    
}