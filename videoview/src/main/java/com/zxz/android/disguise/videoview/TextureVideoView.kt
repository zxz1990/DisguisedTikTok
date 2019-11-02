package com.zxz.android.disguise.videoview

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.SurfaceTexture
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.SeekBar

class TextureVideoView : TextureView {
    private val TAG = "TextureVideoView"
    private var mPlayer: MediaPlayer? = null
    //视频画面显示的载体
    private var mSurface: Surface? = null
    private var mVideoWidth: Int = 0
    private var mVideoHeight: Int = 0
    private var mConfig: VideoConfig? = null
    private var needPlay = false

    //更新的进度条
    //获取拖动到的位置
    //计算该进度对应的时间轴
    //设置播放位置
    var seekBar: SeekBar? = null
        set(seekBar) {
            field = seekBar
            seekBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {
                    if (mPlayer != null && mPlayer?.isPlaying == true) {
                        mHandler.removeMessages(MSG_UPDATE_POSITION)
                    }
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    if (mPlayer != null) {
                        val pos = seekBar.progress
                        val currentPos = pos * (mPlayer?.duration ?: 0) / 100
                        mPlayer?.seekTo(currentPos)
                        if (mPlayer?.isPlaying == true) {
                            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 200)
                        }
                    }
                }
            })
        }
    private var isStop = true
    private var playPos: Int = 0
    var uri: Uri? = null // http: file:
    var onStartPlayListener: (()->Unit)? = null

    private val onPreparedListener = object : MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer) {
            isStop = false
            //3、进入onPrepared状态(准备完毕)，可以去start、pause、seekTo、stop
            //获取总时间
            this@TextureVideoView.seekBar?.max = 100
            if (playPos > 0) {
                mp.seekTo(playPos)
            }
            mp.start()
            onStartPlayListener?.invoke()
            Log.i(TAG,"onPrepared")
            //更新播放时间
            mHandler.sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 200)
        }
    }

    //(解码)错误监听
    private val onErrorListener = MediaPlayer.OnErrorListener { mp, what, extra ->
        mHandler.removeMessages(MSG_UPDATE_POSITION)
        playPos = mp.currentPosition
        mp.stop()
        mp.reset() //进入idle状态
        isStop = true
        true
    }

    //播放完毕监听
    private val onCompletionListener = MediaPlayer.OnCompletionListener {
        mHandler.removeMessages(MSG_UPDATE_POSITION)
    }

    private val onVideoSizeChangedListener =
        MediaPlayer.OnVideoSizeChangedListener { mp, width, height ->
            mVideoWidth = mp.videoWidth
            mVideoHeight = mp.videoHeight
            if (mVideoWidth != 0 && mVideoHeight != 0) {
                requestLayout()
            }
        }

    private val mHandler = @SuppressLint("CI_HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            if (msg.what === MSG_UPDATE_POSITION) {
                if (mPlayer != null) {
                    val pos = mPlayer?.currentPosition //获取当前的播放时间
                    val duration = mPlayer?.duration ?: 0
                    val position = 100 * (pos ?: 0) / duration
                    this@TextureVideoView.seekBar?.progress = position
                    sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 200)
                }
            }
        }
    }

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    private fun init() {
        surfaceTextureListener = object : SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                mSurface = Surface(surface)
                mPlayer?.setSurface(mSurface)
                if (needPlay) {
                    start()
                }
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {

            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                stop()
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }
        }
    }

    private fun initPlayer() {
        if (null == mPlayer) {
            mPlayer = MediaPlayer()
            //设置准备监听
            mPlayer?.setOnPreparedListener(onPreparedListener)
            //设置错误监听
            mPlayer?.setOnErrorListener(onErrorListener)
            //设置播放完毕监听
            mPlayer?.setOnCompletionListener(onCompletionListener)
            mPlayer?.setOnVideoSizeChangedListener(onVideoSizeChangedListener)
            mPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            //设置视频画面显示位置
            mPlayer?.setSurface(mSurface)
            mPlayer?.isLooping = mConfig?.loop ?: false
        } else {
            mPlayer?.reset()
        }
        //1、让MediaPlayer进入idle状态
    }

    //开始解析文件
    private fun setDataAndPlay(uri: Uri?) {
        isStop = false
        //设置播放的数据源
        try {
            val path = uri?.path
            if (path?.startsWith("/android_asset/") == true) {
                val fileName = path.substring("/android_asset/".length, path.length)
                val afd = context.assets.openFd(fileName)
                mPlayer?.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            } else {
                mPlayer?.setDataSource(context, uri)
            }
            //2、进入initialized状态
            //准备
            mPlayer?.prepareAsync()
        } catch (e: Exception) {
            isStop = true
        }

    }

    fun setConfig(config: VideoConfig) {
        mConfig = config
    }

    fun start() {
        Log.i(TAG, "start")

        needPlay = true
        if (mPlayer == null) {
            initPlayer()
            val sc = uri?.getScheme()
            if ("http".equals(sc, ignoreCase = true) || "https".equals(sc, ignoreCase = true)) {
//                DownloadTack().execute(
//                    uri?.toString(),
//                    "/mnt/sdcard/" + System.currentTimeMillis() + ".mp3"
//                )
            } else {
                setDataAndPlay(uri)
            }
        } else {
            if (mPlayer?.isPlaying != true) {
//                setDataAndPlay(uri)
//                mPlayer?.start()
                mHandler.sendEmptyMessageDelayed(MSG_UPDATE_POSITION, 200)
            }
        }
    }

    fun pause() {
        Log.i(TAG, "pause")

        if (null != mPlayer && mPlayer?.isPlaying == true) {
            mHandler.removeMessages(MSG_UPDATE_POSITION)
            mPlayer?.pause()
        }
    }

    fun stop() {
        Log.i(TAG, "stop")

        needPlay = false
        if (null != mPlayer) {
            mHandler.removeMessages(MSG_UPDATE_POSITION)
            mPlayer?.stop()
            mPlayer?.release()
            mPlayer = null
            this.seekBar?.progress = 0
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //Log.i("@@@@", "onMeasure(" + MeasureSpec.toString(widthMeasureSpec) + ", "
        //        + MeasureSpec.toString(heightMeasureSpec) + ")");

        var width = getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = getDefaultSize(mVideoHeight, heightMeasureSpec)
        if (mVideoWidth > 0 && mVideoHeight > 0) {

            val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
            val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
            val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
            val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

            if (widthSpecMode == MeasureSpec.EXACTLY && heightSpecMode == MeasureSpec.EXACTLY) {
                // the size is fixed
                width = widthSpecSize
                height = heightSpecSize

                // for compatibility, we adjust size based on aspect ratio
                if (mVideoWidth * height < width * mVideoHeight) {
                    //Log.i("@@@", "image too wide, correcting");
                    width = height * mVideoWidth / mVideoHeight
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    //Log.i("@@@", "image too tall, correcting");
                    height = width * mVideoHeight / mVideoWidth
                }
            } else if (widthSpecMode == MeasureSpec.EXACTLY) {
                // only the width is fixed, adjust the height to match aspect ratio if possible
                width = widthSpecSize
                height = width * mVideoHeight / mVideoWidth
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    height = heightSpecSize
                }
            } else if (heightSpecMode == MeasureSpec.EXACTLY) {
                // only the height is fixed, adjust the width to match aspect ratio if possible
                height = heightSpecSize
                width = height * mVideoWidth / mVideoHeight
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // couldn't match aspect ratio within the constraints
                    width = widthSpecSize
                }
            } else {
                // neither the width nor the height are fixed, try to use actual video size
                width = mVideoWidth
                height = mVideoHeight
                if (heightSpecMode == MeasureSpec.AT_MOST && height > heightSpecSize) {
                    // too tall, decrease both width and height
                    height = heightSpecSize
                    width = height * mVideoWidth / mVideoHeight
                }
                if (widthSpecMode == MeasureSpec.AT_MOST && width > widthSpecSize) {
                    // too wide, decrease both width and height
                    width = widthSpecSize
                    height = width * mVideoHeight / mVideoWidth
                }
            }
        } else {
            // no size yet, just adopt the given spec sizes
        }
        setMeasuredDimension(width, height)
    }

    companion object {
        private val MSG_UPDATE_POSITION = 1
    }
}