package com.zziafyc.wanandroidkotlin.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zziafyc.wanandroidkotlin.constant.Constant
import com.zziafyc.wanandroidkotlin.utils.Preference
import org.greenrobot.eventbus.EventBus

abstract class BaseFragment : Fragment() {
    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)
    //视图是否加载完毕
    private var isViewPrepare = false
    //数据是否加载过了
    private var hasLoadData = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState:
        Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        isViewPrepare = true
        initView(view)
        lazyLoadDataIfPrepared()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }

    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }

    /**
     *  布局文件
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView(view: View)

    /**
     * 懒加载
     */
    abstract fun lazyLoad()

    /**
     * 是否使用eventBus
     */
    open fun useEventBus(): Boolean = false

    override fun onDestroy() {
        super.onDestroy()
        if (useEventBus()) {
            EventBus.getDefault().unregister(this)
        }
    }

}

