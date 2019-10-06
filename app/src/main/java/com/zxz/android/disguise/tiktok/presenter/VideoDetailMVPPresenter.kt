package com.zxz.android.disguise.tiktok.presenter

import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.zxz.android.disguise.tiktok.VideoDetailFragment
import com.zxz.android.disguise.tiktok.consts.Consts
import com.zxz.android.disguise.tiktok.mvp.BaseMVPPresenter
import com.zxz.android.disguise.tiktok.view.IVideoDetailView

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
class VideoDetailMVPPresenter(v: IVideoDetailView) : BaseMVPPresenter<IVideoDetailView>(v) {
    private val TAG = "VideoDetailMVPPresenter"
    private var mUri: Uri? = null

    init {
        Log.i(TAG, "init")
    }

    override fun initData(bundle: Bundle?) {
        mUri = bundle?.getParcelable(Consts.KEY_URI)
    }

    fun getUri(): Uri? {
        return mUri
    }
}