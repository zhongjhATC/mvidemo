package com.zhongjh.mvidemo.phone.search

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.entity.SearchConditions
import com.zhongjh.mvidemo.entity.SearchType
import com.zhongjh.mvidemo.phone.main.fragment.shopping.ShopPingFragment
import com.zhongjh.mvidemo.diffcallback.DiffSearchHistoryCallback
import com.zhongjh.mvidemo.phone.search.adapter.SearchHistoryAdapter
import com.zhongjh.mvidemo.phone.search.adapter.SearchNavigatorAdapter
import com.zhongjh.mvidemo.phone.search.adapter.SearchViewPagerAdapter
import com.zhongjh.mvilibrary.base.BaseActivity
import com.zhongjh.mvilibrary.listener.ThrottleOnClickListener
import com.zhongjh.mvilibrary.rxjava.textview.TextViewTextChangeNullEventObservable
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.fragment_shopping.*
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

/**
 * 搜索的Activity
 */
class SearchActivity : BaseActivity<SearchView, SearchPresenter>(), SearchView {

    private val mTag = SearchActivity::class.qualifiedName

    /**
     * 当前的搜索的条件
     */
    private val mSearchConditions = SearchConditions()

    /**
     * 用于记录索引，点击搜索时随时更新该position
     */
    private var mViewPagerPosition = 0

    private lateinit var mCommonNavigatorAdapter: SearchNavigatorAdapter
    private val mSearchHistoryAdapter = SearchHistoryAdapter()
    private val mSearchViewPagerAdapter: SearchViewPagerAdapter by lazy {
        SearchViewPagerAdapter(supportFragmentManager, lifecycle)
    }

    private val mTvSearchClickObservable: Observable<Boolean> by lazy {
        RxView.clicks(tvSearch).share().map { true }
    }

    /**
     * 文本为空时立即触发
     */
    private val mTvSearchTextChangesObservable: Observable<Boolean> by lazy {
        TextViewTextChangeNullEventObservable(etSearch) {
            switchShowSearchView()
        }.share().map { true }
    }

    override fun initLayoutId() = R.layout.activity_search

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        imgBack.setOnClickListener(object : ThrottleOnClickListener() {
            override fun onClick() {
                this@SearchActivity.finish()
            }
        })
    }

    override fun initialize() {
        initMagicIndicator()
        initRlSearchHistory()
    }

    override fun createPresenter() = SearchPresenter()

    override fun searchClickIntent(): Observable<String> {
        return mTvSearchClickObservable
            // filter完成一个条件过滤和筛选,如果filter判断的值为真，则交给观察者，否则跳过
            .filter { etSearch.length() > 0 }
            // 转换成搜索文本的数据
            .map {
                mSearchConditions.content = etSearch.text.toString()
                etSearch.text.toString()
            }
    }

    override fun searchTextChangesIntent(): Observable<String> {
        return mTvSearchTextChangesObservable
            // 满足一定时间延时后才发射数据
            .debounce(1, TimeUnit.SECONDS)
            // 转换成搜索文本的数据
            .map {
                mSearchConditions.content = etSearch.text.toString()
                etSearch.text.toString()
            }
    }

    override fun render(state: SearchState) {
        when (state) {
            is SearchState.SearchNoticeState -> searchNoticeState()
            is SearchState.InitSearchContentsState -> initSearchContentsState(state)
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
     * 初始化搜索记录历史列表
     */
    private fun initRlSearchHistory() {
        val manager: FlexboxLayoutManager =
            object : FlexboxLayoutManager(this, FlexDirection.ROW, FlexWrap.WRAP) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
        rlSearchHistory.layoutManager = manager
        rlSearchHistory.adapter = mSearchHistoryAdapter
        mSearchHistoryAdapter.setDiffCallback(DiffSearchHistoryCallback())
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
                    mSearchConditions.type = it
                }
                mSearchViewPagerAdapter.search(position, mSearchConditions.content)
                mViewPagerPosition = position
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
    private fun searchNoticeState() {
        Log.i(mTag, "searchNoticeState")
        if (!TextUtils.isEmpty(mSearchConditions.content)) {
            showDataListView()
        }
        mSearchViewPagerAdapter.search(mViewPagerPosition, mSearchConditions.content)
        // 更新搜索历史

    }

    /**
     * 初始化搜索历史
     */
    private fun initSearchContentsState(state: SearchState.InitSearchContentsState) {
        Log.i(mTag, "initSearchContentsState")
        mSearchHistoryAdapter.setDiffNewData(state.searchContents)
    }

    /**
     * 显示搜索列表，隐藏产品列表
     */
    private fun switchShowSearchView() {
        Log.i(mTag, "switchShowSearchView")
        groupSearchHistory.visibility = View.VISIBLE
        viewPager2.visibility = View.GONE
        mSearchViewPagerAdapter.clear(mViewPagerPosition)
    }

    /**
     * 显示产品列表，隐藏搜索列表
     */
    private fun showDataListView() {
        groupSearchHistory.visibility = View.GONE
        viewPager2.visibility = View.VISIBLE
    }
}