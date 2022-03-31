package com.zhongjh.mvidemo.phone.main

import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.zhongjh.mvidemo.R
import com.zhongjh.mvidemo.phone.main.viewpager.ViewPagerFragmentStateAdapter
import com.zhongjh.mvilibrary.base.BaseActivity
import com.zhongjh.mvilibrary.base.BaseApplication
import com.zhongjh.mvilibrary.utils.ScreenUtil
import com.zhongjh.mvilibrary.utils.StatusBarUtil
import devlight.io.library.ntb.NavigationTabBar
import devlight.io.library.ntb.NavigationTabBar.OnTabBarSelectedIndexListener
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 首页
 * @author zhongjh
 * @date 2022/3/23
 */
class MainActivity : BaseActivity<MainView, MainPresenter>(), MainView {

    private val mAdapter: ViewPagerFragmentStateAdapter by lazy {
        ViewPagerFragmentStateAdapter(supportFragmentManager, lifecycle)
    }

    override fun initLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initParam(savedInstanceState: Bundle?) {
    }

    override fun initListener() {
        navigationTabBar.onTabBarSelectedIndexListener = object : OnTabBarSelectedIndexListener {
            override fun onStartTabSelected(model: NavigationTabBar.Model, index: Int) {
                viewpager2.currentItem = index
            }

            override fun onEndTabSelected(model: NavigationTabBar.Model, index: Int) {}
        }
        viewpager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                navigationTabBar.modelIndex = position
                super.onPageSelected(position)
            }
        })
    }

    override fun initialize() {
        StatusBarUtil.initStatusBarHeight(viewpager2)
        BaseApplication.instance.init()
        initViewPager()
        initTabBar()
    }

    override fun createPresenter() = MainPresenter()

    private fun initViewPager() {
        viewpager2.adapter = mAdapter
        viewpager2.isUserInputEnabled = false
        viewpager2.offscreenPageLimit = 5
    }

    /**
     * 初始化tabBar
     * 必须要赋值，否则会报错奔溃
     */
    private fun initTabBar() {
        val models = ArrayList<NavigationTabBar.Model>()
        models.add(
            NavigationTabBar.Model.Builder(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_round_alarm_24, theme),
                R.color.lilac
            ).title(getString(R.string.shop))
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_round_alarm_add_24, theme),
                R.color.lilac
            ).title(getString(R.string.auction))
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_round_alarm_off_24, theme),
                R.color.lilac
            ).title(getString(R.string.fast_auction))
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                ResourcesCompat.getDrawable(resources, R.drawable.ic_round_alarm_on_24, theme),
                R.color.lilac
            ).title(getString(R.string.exchange))
                .build()
        )
        models.add(
            NavigationTabBar.Model.Builder(
                ResourcesCompat.getDrawable(
                    resources,
                    R.drawable.ic_round_insert_emoticon_24,
                    theme
                ),
                R.color.lilac
            ).title(getString(R.string.my))
                .build()
        )
        models[0].showBadge()
        models[0].badgeTitle = "2"
        navigationTabBar.models = models
    }

    override fun render(state: MainState) {
    }


}