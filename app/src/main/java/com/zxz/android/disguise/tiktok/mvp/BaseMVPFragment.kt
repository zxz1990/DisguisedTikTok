package com.zxz.android.disguise.tiktok.mvp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * Created by zhuxuezhi on 2019-10-05.
 */
abstract class BaseMVPFragment<P : BaseMVPPresenter<*>> : Fragment() {

    protected lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        presenter.initData(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getFragmentLayoutId(), container, false)
    }

    abstract fun getFragmentLayoutId(): Int
    abstract fun createPresenter(): P
    abstract fun initView()
}