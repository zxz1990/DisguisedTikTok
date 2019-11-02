package com.zxz.android.disguise.tiktok.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.zxz.android.disguise.tiktok.R
import com.zxz.android.disguise.tiktok.adapter.VideoViewPagerAdapter2
import com.zxz.android.disguise.tiktok.model.Model
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    private val mAdapter = VideoViewPagerAdapter2(this)
    private val mViewPager by lazy { view_pager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        mViewPager.adapter = mAdapter
    }

    private fun initData() {
//        val shuffledList = Model.videoResList.shuffled()
//        val videoList = shuffledList.map { resId ->
//            Model.getVideoUrlById(this, resId)
//        }
        val videoList = Model.getVideoUrls(this)
        mAdapter.setVideoData(videoList)
        mAdapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
    }


}