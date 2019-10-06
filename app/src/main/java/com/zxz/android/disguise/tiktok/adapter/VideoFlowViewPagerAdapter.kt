package com.zxz.android.disguise.tiktok.adapter

import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import com.zxz.android.disguise.tiktok.VideoDetailFragment
import com.zxz.android.disguise.tiktok.consts.Consts

/**
 * Created by zhuxuezhi on 2019-10-04.
 */
class VideoFlowViewPagerAdapter(private val activity: AppCompatActivity) :
    FragmentStatePagerAdapter(
        activity.supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {
    private val mFragments = ArrayList<Fragment>()
    fun setVideoData(list: List<Uri>) {
        list.forEach { uri ->
            val videoDetailFragment = VideoDetailFragment(activity)
            videoDetailFragment.arguments = Bundle().apply {
                putParcelable(Consts.KEY_URI, uri)
            }
            mFragments.add(videoDetailFragment)
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position % mFragments.size]
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }
}