package com.zxz.android.disguise.tiktok.presenter

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.zxz.android.disguise.tiktok.consts.Consts
import com.zxz.android.disguise.tiktok.mvp.BaseMVPPresenter
import com.zxz.android.disguise.tiktok.view.IVideoDetailView
import com.zxz.android.disguise.videoview.VideoData

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
class VideoDetailMVPPresenter(v: IVideoDetailView) : BaseMVPPresenter<IVideoDetailView>(v) {
    private val TAG = "VideoDetailMVPPresenter"
    private var mVideoData: VideoData? = null

    init {
        Log.i(TAG, "init")
    }

    override fun initData(bundle: Bundle?) {
        mVideoData = bundle?.getParcelable(Consts.KEY_VIDEO_DATA)
    }

    fun getVideoData(): VideoData? {
        return mVideoData
    }
}