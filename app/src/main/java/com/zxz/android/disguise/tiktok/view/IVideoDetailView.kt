package com.zxz.android.disguise.tiktok.view

import com.zxz.android.disguise.tiktok.mvp.IBaseMVPView

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
interface IVideoDetailView : IBaseMVPView {

    fun onVideoPlay()

    fun onVideoStop()
}