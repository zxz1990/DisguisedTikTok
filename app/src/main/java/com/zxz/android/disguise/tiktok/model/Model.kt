package com.zxz.android.disguise.tiktok.model

import android.content.Context
import android.net.Uri
import androidx.annotation.RawRes
import com.zxz.android.disguise.tiktok.R

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
object Model {

    val videoResList = arrayListOf(
        R.raw.video_01,
        R.raw.video_02,
        R.raw.video_03,
        R.raw.video_04,
        R.raw.video_05,
        R.raw.video_06,
        R.raw.video_07,
        R.raw.video_08,
        R.raw.video_09,
        R.raw.video_10,
        R.raw.video_11,
        R.raw.video_12,
        R.raw.video_13,
        R.raw.video_14,
        R.raw.video_15
    )

    fun getVideoUrlById(context: Context, @RawRes id: Int): Uri {
        return Uri.parse("android.resource://" + context.packageName + "/" + id)
    }
}