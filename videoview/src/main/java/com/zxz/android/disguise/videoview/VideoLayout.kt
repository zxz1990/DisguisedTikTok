package com.zxz.android.disguise.videoview

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.SeekBar

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
class VideoLayout(
    context: Context,
    attrs: AttributeSet? = null
) :
    FrameLayout(context, attrs) {

    private val videoView by lazy { findViewById<TextureVideoView>(R.id.texture_video_view) }

    init {
        View.inflate(context, R.layout.layout_video, this)
    }

    fun start() {
        videoView.start()
    }

    fun stop() {
        videoView.stop()
    }

    fun pause() {
        videoView.pause()
    }

    fun setConfig(config: VideoConfig) {
        videoView.setConfig(config)
    }

    var uri: Uri? = null
        get() {
            return videoView.uri
        }
        set(value) {
            videoView.uri = value
            field = value
        }

    var seekBar: SeekBar? = videoView.seekBar
}