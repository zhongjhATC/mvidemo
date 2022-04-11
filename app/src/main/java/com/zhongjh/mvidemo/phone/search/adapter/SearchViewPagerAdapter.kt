package com.zhongjh.mvidemo.phone.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zhongjh.mvidemo.entity.SearchType
import com.zhongjh.mvidemo.phone.search.yuanshen.YuanShenFragment

/**
 * SearchActivity的ViewPager的适配器
 *
 * @author zhongjh
 * @date 2022/4/07
 */
class SearchViewPagerAdapter(private val fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    var searchContent: String = ""

    override fun createFragment(position: Int): Fragment {
        return YuanShenFragment()
    }

    override fun getItemCount(): Int {
        return SearchType.values().size
    }

    /**
     * 该事件初始化时会调用，谨慎使用免得影响性能
     *
     * 切换到某个Fragment的时候，进行查询
     * 如果已经查询过了，则不再查询
     * 如果传值是“”，也不查询
     */
    override fun getItemId(position: Int): Long {
        val id = position.toLong()
        val yuanShenFragment = getPageFragment(id) as? YuanShenFragment
        yuanShenFragment.search(searchContent)
        return position.toLong()
    }

    private fun getPageFragment(id: Long): Fragment? {
        return fragmentManager.findFragmentByTag("f$id")
    }

}