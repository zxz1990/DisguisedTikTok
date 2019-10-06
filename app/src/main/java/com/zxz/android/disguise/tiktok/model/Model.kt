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
        R.raw.video_16,
        R.raw.video_17,
        R.raw.video_18,
        R.raw.video_19,
        R.raw.video_20,
        R.raw.video_21,
        R.raw.video_22,
        R.raw.video_23,
        R.raw.video_24,
        R.raw.video_25,
        R.raw.video_26,
        R.raw.video_27,
        R.raw.video_28,
        R.raw.video_29,
        R.raw.video_30,
        R.raw.video_31,
        R.raw.video_32,
        R.raw.video_33,
        R.raw.video_34,
        R.raw.video_35,
        R.raw.video_36,
        R.raw.video_37,
        R.raw.video_38,
        R.raw.video_39,
        R.raw.video_40,
        R.raw.video_41
    )

    fun getVideoUrlById(context: Context, @RawRes id: Int): Uri {
        return Uri.parse("android.resource://" + context.packageName + "/" + id)
    }
}