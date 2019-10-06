package com.zxz.android.disguise.tiktok.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.zxz.android.disguise.tiktok.R
import com.zxz.android.disguise.tiktok.adapter.VideoFlowViewPagerAdapter
import com.zxz.android.disguise.tiktok.model.Model
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {
    private val TAG = "MainActivity"

    private val mAdapter = VideoFlowViewPagerAdapter(this)
    private val mViewPager by lazy { view_pager }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initData()
    }

    private fun initView() {
        mViewPager.adapter = mAdapter
        mViewPager.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        Log.i(TAG, "onPageSelected position=$position")
    }

    private fun initData() {
        val videoList = Model.videoResList.map { resId ->
            Model.getVideoUrlById(this, resId)
        }
        mAdapter.setVideoData(videoList)
        mAdapter.notifyDataSetChanged()
    }


    override fun onDestroy() {
        super.onDestroy()
        mViewPager.clearOnPageChangeListeners()
    }


}