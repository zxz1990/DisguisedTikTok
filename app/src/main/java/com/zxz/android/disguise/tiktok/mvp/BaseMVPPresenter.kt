package com.zxz.android.disguise.tiktok.mvp

import android.os.Bundle

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
abstract class BaseMVPPresenter<V : IBaseMVPView>(val mvpView: V) {
    abstract fun initData(bundle:Bundle?)
}