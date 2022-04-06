package com.zhongjh.mvidemo.phone.main.fragment.shopping.adapter

import android.graphics.drawable.Drawable
import android.view.ViewGroup
import android.widget.ImageView
import com.blankj.utilcode.util.ScreenUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.entity.Product
import com.zhongjh.mvilibrary.base.BaseApplication.Companion.instance
import com.zhongjh.mvilibrary.utils.GlideRoundTransform


/**
 * 产品的适配器
 * @author zhongjh
 * @date 2022/4/2
 */
class ShopPingAdapter : BaseQuickAdapter<Product, BaseViewHolder>(R.layout.item_shop_horizontal) {

    var width = 0

    override fun convert(holder: BaseViewHolder, item: Product) {
        val imageView = holder.getView<ImageView>(R.id.imgProduct)

        // 设置每个item是屏幕的1/3 , -16 是控件的左右间距
        val layoutParams: ViewGroup.LayoutParams = holder.itemView.layoutParams
        if (width == 0) {
            width = (ScreenUtils.getScreenWidth()) / 3
            layoutParams.width = width
        }
        if (width != layoutParams.width) {
            layoutParams.width = width
        }

        // 显示图片
        val options = RequestOptions().placeholder(R.mipmap.ic_banner)
            .error(R.mipmap.ic_banner)
            .transform(GlideRoundTransform(10))
        Glide.with(instance)
            .load(item.pictureUrl)
            .apply(options)
            .into(imageView)
        holder.setText(R.id.tvProductName, item.name)
        holder.setText(R.id.tvPrice, "当前价:￥" + item.price)
    }

}