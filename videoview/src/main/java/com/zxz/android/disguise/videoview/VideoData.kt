package com.zxz.android.disguise.videoview

import android.net.Uri
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by zhuxuezhi on 2019-11-02.
 */
@Parcelize
data class VideoData(
    var uri: Uri? = null,
    var coverUri: Uri? = null
) : Parcelable