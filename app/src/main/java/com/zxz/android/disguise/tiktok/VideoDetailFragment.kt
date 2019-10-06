package com.zxz.android.disguise.tiktok

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.zxz.android.disguise.tiktok.mvp.BaseMVPFragment
import com.zxz.android.disguise.tiktok.presenter.VideoDetailMVPPresenter
import com.zxz.android.disguise.tiktok.view.IVideoDetailView
import com.zxz.android.disguise.videoview.VideoConfig
import com.zxz.android.disguise.videoview.VideoLayout
import kotlinx.android.synthetic.main.fragment_video_detail.*

/**
 * Created by zhuxuezhi on 2019-10-04.
 */
class VideoDetailFragment(private val activity: AppCompatActivity) :
    BaseMVPFragment<VideoDetailMVPPresenter>(), IVideoDetailView {
    private val TAG = "VideoDetailFragment"

    init {
        Log.i(TAG, "VideoDetailFragment init")
    }

    private lateinit var videoView: VideoLayout

    override fun getFragmentLayoutId(): Int {
        return R.layout.fragment_video_detail
    }

    override fun createPresenter(): VideoDetailMVPPresenter {
        return VideoDetailMVPPresenter(this)
    }

    override fun initView() {

        videoView = video_view

        /**播放 res/raw 目录下的文件
         * android.resource:// ：前缀固定
         * com.example.administrator.helloworld：为当前类的所在的包路径，可以使用 String packageName = getPackageName(); 动态获取
         * R.raw.la_isla：最后接 res/raw 目录中的文件名
         * */
        videoView.setConfig(VideoConfig().apply {
            loop = true
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "onViewCreated")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume userVisibleHint=$userVisibleHint")
        if (view != null) {
//            Log.i(TAG, "setUserVisibleHint $userVisibleHint")
            if (userVisibleHint) {
                videoView.uri = presenter.getUri()
                videoView.start()
            } else {
//                videoView.stop()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause")
        videoView.stop()

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "onDestroy")
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        Toast.makeText(activity, "setUserVisibleHint=$isVisibleToUser", Toast.LENGTH_SHORT)
            .show()
    }

    override fun onVideoPlay() {
        Toast.makeText(activity, "start play", Toast.LENGTH_SHORT).show()
    }

    override fun onVideoStop() {
        Toast.makeText(activity, "start stop", Toast.LENGTH_SHORT).show()
    }
}