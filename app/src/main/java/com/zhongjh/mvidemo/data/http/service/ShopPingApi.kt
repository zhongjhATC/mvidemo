package com.jshvarts.mosbymvi.data

import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvidemo.entity.WanEntity
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * 商城的有关接口
 */
object ShopPingApi {

    /**
     * 获取商品
     */
    fun getProducts(): Observable<WanEntity<List<Product>>> = Observable.just(simulationProducts()).delay(1, TimeUnit.SECONDS);

    private fun simulationProducts(): WanEntity<List<Product>> {
        val products = ArrayList<Product>()
        val leiShen = Product()
        leiShen.name = "雷神"
        leiShen.pictureUrl =
            "https://img.jiaochengzhijia.com/uploadfile/2021/0821/20210821194851110.png"
        leiShen.price = "648"
        val hutao = Product()
        hutao.name = "胡桃"
        hutao.pictureUrl =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F1547145438590204400b8d3c9964804413dd0ee3.jpg&refer=http%3A%2F%2Fi0.hdslb.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651225760&t=795b44ee07bfcd45a8b572c03f83e24b"
        hutao.price = "648"
        val shenli = Product()
        shenli.name = "神里"
        shenli.pictureUrl =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.danji6.com%2Fuploadfile%2F2021%2F0929%2F20210929011844895.jpg&refer=http%3A%2F%2Fimg.danji6.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651225867&t=ffda79a24ef92bf8bc4a003cb2de099b"
        shenli.price = "648"
        val jiutun = Product()
        jiutun.name = "酒吞"
        jiutun.pictureUrl =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Finews.gtimg.com%2Fnewsapp_match%2F0%2F10582847208%2F0.jpg&refer=http%3A%2F%2Finews.gtimg.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651225925&t=fa342559ad1d374d7c02ab4d5d4071f0"
        jiutun.price = "648"
        val dishitian = Product()
        dishitian.name = "帝释天"
        dishitian.pictureUrl =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202105%2F03%2F20210503092243_50b78.thumb.1000_0.jpg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651226108&t=f97c73e5325dcc369b85230d2b82442f"
        dishitian.price = "648"
        val axiuluo = Product()
        axiuluo.name = "阿修罗"
        axiuluo.pictureUrl =
            "https://pics7.baidu.com/feed/d52a2834349b033b9a479ae4d525b7dbd439bd31.png?token=18676fd22242e20f87ccba20bbd6b0a2"
        axiuluo.price = "648"
        val yidou = Product()
        yidou.name = "一斗"
        yidou.pictureUrl =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fnimg.ws.126.net%2F%3Furl%3Dhttp%253A%252F%252Fdingyue.ws.126.net%252F2021%252F1018%252F12c76cbdj00r15yzo002ec000dw00fnc.jpg%26thumbnail%3D650x2147483647%26quality%3D80%26type%3Djpg&refer=http%3A%2F%2Fnimg.ws.126.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651226170&t=345453b92e48900446f8f6ab7a2c9c27"
        yidou.price = "648"
        val zhongli = Product()
        zhongli.name = "钟离"
        zhongli.pictureUrl =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202108%2F21%2F20210821192257_62b1f.thumb.1000_0.png&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1651226204&t=64f7cb601e8a8ee5285814c08fc0d1f3"
        zhongli.price = "648"
        products.addAll(arrayOf(leiShen, hutao, shenli, jiutun, dishitian, axiuluo, yidou, zhongli))
        val wanEntity = WanEntity<List<Product>>()
        wanEntity.data = products
        wanEntity.code = 200
        return wanEntity
    }
}

