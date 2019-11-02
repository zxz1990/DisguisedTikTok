package com.zxz.android.disguise.videoview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.SeekBar
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
class VideoLayout(
    context: Context,
    attrs: AttributeSet? = null
) :
    FrameLayout(context, attrs) {

    private val videoView by lazy { findViewById<TextureVideoView>(R.id.texture_video_view) }
    private val videoCover by lazy { findViewById<ImageView>(R.id.iv_cover) }

    init {
        View.inflate(context, R.layout.layout_video, this)
        videoView.onStartPlayListener = {
            videoCover.visibility = View.GONE
        }
    }

    fun start() {
        videoView.start()
    }

    fun stop() {
        videoView.stop()
//        videoCover.visibility = View.VISIBLE
    }

    fun pause() {
        videoView.pause()
    }

    fun setConfig(config: VideoConfig) {
        videoView.setConfig(config)
    }

    var videoData: VideoData? = null
        set(value) {
            videoView.uri = value?.uri
//            Glide.with(context)//封面图会黑屏
//                .setDefaultRequestOptions(
//                    RequestOptions()
//                        .frame(100000)
//                        .centerCrop()
//                )
//                .load(value?.coverUri)
//                .into(videoCover)
            field = value
        }

    var seekBar: SeekBar? = videoView.seekBar
}