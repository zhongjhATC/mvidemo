package com.zhongjh.mvidemo.phone.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingFragment
import com.zhongjh.mvidemo.phone.search.adapter.SearchNavigatorAdapter
import com.zhongjh.mvidemo.phone.search.adapter.SearchViewPagerAdapter
import com.zhongjh.mvilibrary.base.BaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search.*
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*

class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView {

    override fun initLayoutId() = R.layout.activity_search

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {

    }

    override fun initialize() {
        initMagicIndicator()
    }

    override fun createPresenter() = SearchPresenter()

    /**
     * 初始化tab
     */
    private fun initMagicIndicator() {
        val listTitle: MutableList<String> = ArrayList()
        val listFragments: MutableList<Fragment> = ArrayList()

        listTitle.add("威士忌")
        listFragments.add(ShopPingFragment())
        listTitle.add("商品")
        listFragments.add(ShopPingFragment())
        listTitle.add("拍卖")
        listFragments.add(ShopPingFragment())
        listTitle.add("帖子")
        listFragments.add(ShopPingFragment())
        listTitle.add("咨询")
        listFragments.add(ShopPingFragment())
        listTitle.add("用户")
        listFragments.add(ShopPingFragment())
        listTitle.add("酒吧")
        listFragments.add(ShopPingFragment())

        val commonNavigator = CommonNavigator(this)
        val commonNavigatorAdapter = SearchNavigatorAdapter(listTitle, viewPager2)
        commonNavigator.adapter = commonNavigatorAdapter
        commonNavigator.leftPadding = UIUtil.dip2px(this, 10.0)
        commonNavigator.rightPadding = UIUtil.dip2px(this, 15.0)
        magicIndicator.navigator = commonNavigator
        initViewPager(listFragments)
    }

    /**
     * 初始化viewPager
     */
    private fun initViewPager(listFragments : MutableList<Fragment>) {
        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                magicIndicator.onPageScrolled(
                    position,
                    positionOffset,
                    positionOffsetPixels
                )
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                magicIndicator.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                magicIndicator.onPageScrollStateChanged(state)
            }
        })

        val homeFragmentPageAdapter = SearchViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )
        viewPager2.adapter = homeFragmentPageAdapter
        viewPager2.offscreenPageLimit = listFragments.size
    }

    override fun searchIntent(): Observable<String> {

    }


}