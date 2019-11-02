package com.zxz.android.disguise.tiktok.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zxz.android.disguise.tiktok.VideoDetailFragment
import com.zxz.android.disguise.tiktok.consts.Consts
import com.zxz.android.disguise.videoview.VideoData

/**
 * Created by zhuxuezhi on 2019-10-04.
 */
class VideoViewPagerAdapter2(private val activity: AppCompatActivity) :
    FragmentStateAdapter(activity) {
    private val mVideoDataList = ArrayList<VideoData>()
    fun setVideoData(list: List<VideoData>) {
        mVideoDataList.clear()
        mVideoDataList.addAll(list)
    }

    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun createFragment(position: Int): Fragment {
        val videoDetailFragment = VideoDetailFragment(activity)
        videoDetailFragment.arguments = Bundle().apply {
            putParcelable(Consts.KEY_VIDEO_DATA, mVideoDataList[position % mVideoDataList.size])
        }
        return videoDetailFragment
    }
}