package com.zhongjh.mvidemo.phone.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.entity.SearchType
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingFragment
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingState
import com.zhongjh.mvidemo.phone.search.adapter.SearchNavigatorAdapter
import com.zhongjh.mvidemo.phone.search.adapter.SearchViewPagerAdapter
import com.zhongjh.mvilibrary.base.BaseActivity
import com.zhongjh.mvilibrary.constant.Constants.TAG
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search.*
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator


class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView {

    /**
     * 当前的搜索的条件
     */
    private val searchConditions = SearchConditions()
    private lateinit var commonNavigatorAdapter: SearchNavigatorAdapter
    private val tvSearchClickObservable: Observable<Boolean> by lazy {
        RxView.clicks(tvSearch).share().map { true }
    }

    override fun initLayoutId() = R.layout.activity_search

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {

    }

    override fun initialize() {
        initMagicIndicator()
    }

    override fun createPresenter() = SearchPresenter()

    override fun searchIntent(): Observable<SearchConditions> {
        return tvSearchClickObservable
            // filter完成一个条件过滤和筛选,如果filter判断的值为真，则交给观察者，否则跳过
            .filter { etSearch.length() <= 0 }
            // 转换成搜索文本的数据
            .map {
                searchConditions.content = etSearch.text.toString()
                searchConditions
            }
    }

    override fun render(state: SearchState) {
        when (state) {
            is SearchState.ErrorState -> TODO()
            is SearchState.LoadingState -> TODO()
            is SearchState.SearchAuction -> TODO()
            is SearchState.SearchBar -> TODO()
            is SearchState.SearchConsult -> TODO()
            is SearchState.SearchInvitation -> TODO()
            is SearchState.SearchProduct -> TODO()
            is SearchState.SearchUser -> TODO()
            is SearchState.SearchYuanShen -> TODO()
        }
    }

    /**
     * 初始化tab
     */
    private fun initMagicIndicator() {
        val listTitle: MutableList<String> = ArrayList()
        val listFragments: MutableList<Fragment> = ArrayList()
        // 实例化所有枚举信息
        val searchTypes = SearchType.values()
        for (searchType in searchTypes) {
            listTitle.add(searchType.value)
            listFragments.add(ShopPingFragment())
        }

        val commonNavigator = CommonNavigator(this)
        commonNavigatorAdapter = SearchNavigatorAdapter(listTitle, viewPager2)
        commonNavigator.adapter = commonNavigatorAdapter
        commonNavigator.leftPadding = UIUtil.dip2px(this, 10.0)
        commonNavigator.rightPadding = UIUtil.dip2px(this, 15.0)
        magicIndicator.navigator = commonNavigator
        initViewPager(listFragments)
    }

    /**
     * 初始化viewPager
     */
    private fun initViewPager(listFragments: MutableList<Fragment>) {
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
                // 根据索引构建一个enum
                val searchType = SearchType.match(position)
                searchType?.let {
                    searchConditions.type = it
                }
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


}