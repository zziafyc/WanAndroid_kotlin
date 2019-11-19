package com.zziafyc.wanandroidkotlin.base

import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import com.zziafyc.wanandroidkotlin.constant.Constant
import com.zziafyc.wanandroidkotlin.receiver.NetworkChangeReceiver
import com.zziafyc.wanandroidkotlin.utils.Preference
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {

    protected var isLogin: Boolean by Preference(Constant.LOGIN_KEY, false)
    //缓存上一次的网络状态
    protected var hasNetwork: Boolean by Preference(Constant.HAS_NETWORK_KEY, true)
    //网络变化
    protected var mNetworkChangeReceiver: NetworkChangeReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        if (useEventBus()) {
            EventBus.getDefault().register(this)
        }
        initView()
        initData()
        start()

    }

    override fun onResume() {
        val filter = IntentFilter()
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        mNetworkChangeReceiver = NetworkChangeReceiver()
        super.onResume()
    }

    /**
     *  布局文件
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 初始化view
     */
    abstract fun initView()

    /**
     * 初始化data
     */
    abstract fun initData()

    /**
     * 开始请求
     */
    abstract fun start()

    /**
     * 是否使用eventBus
     */
    open fun useEventBus(): Boolean = true


}