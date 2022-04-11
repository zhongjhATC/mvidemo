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
import com.zhongjh.mvidemo.phone.search.adapter.SearchNavigatorAdapter
import com.zhongjh.mvidemo.phone.search.adapter.SearchViewPagerAdapter
import com.zhongjh.mvilibrary.base.BaseActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search.*
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator


class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView {

    private val mTag = SearchActivity::class.qualifiedName

    /**
     * 当前的搜索的条件
     */
    private val mSearchConditions = SearchConditions()
    private lateinit var mCommonNavigatorAdapter: SearchNavigatorAdapter
    private val mSearchViewPagerAdapter: SearchViewPagerAdapter by lazy {
        SearchViewPagerAdapter(supportFragmentManager, lifecycle)
    }
    private val mTvSearchClickObservable: Observable<Boolean> by lazy {
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
        return mTvSearchClickObservable
            // filter完成一个条件过滤和筛选,如果filter判断的值为真，则交给观察者，否则跳过
            .filter { etSearch.length() <= 0 }
            // 转换成搜索文本的数据
            .map {
                mSearchConditions.content = etSearch.text.toString()
                mSearchConditions
            }
    }

    override fun render(state: SearchState) {
        when (state) {
            is SearchState.ErrorState -> Log.d(mTag, "LoadingState")
            is SearchState.LoadingState -> Log.d(mTag, "LoadingState")
            is SearchState.SearchAuctionState -> Log.d(mTag, "LoadingState")
            is SearchState.SearchBarState -> Log.d(mTag, "LoadingState")
            is SearchState.SearchConsultState -> Log.d(mTag, "LoadingState")
            is SearchState.SearchInvitationState -> Log.d(mTag, "LoadingState")
            is SearchState.SearchProductState -> searchProductState()
            is SearchState.SearchUserState -> Log.d(mTag, "LoadingState")
            is SearchState.SearchYuanShenState -> Log.d(mTag, "LoadingState")
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
        mCommonNavigatorAdapter = SearchNavigatorAdapter(listTitle, viewPager2)
        commonNavigator.adapter = mCommonNavigatorAdapter
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
                mSearchViewPagerAdapter.searchContent = mSearchConditions.content
                super.onPageSelected(position)
                magicIndicator.onPageSelected(position)
                // 根据索引构建一个enum
                val searchType = SearchType.match(position)
                searchType?.let {
                    mSearchConditions.type = it
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                magicIndicator.onPageScrollStateChanged(state)
            }
        })
        viewPager2.adapter = mSearchViewPagerAdapter
        viewPager2.offscreenPageLimit = listFragments.size
    }

    /**
     * 查询数据
     */
    private fun searchProductState() {

    }
}