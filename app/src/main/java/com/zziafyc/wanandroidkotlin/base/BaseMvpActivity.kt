package com.zziafyc.wanandroidkotlin.base

/**
 * @author chenxz
 * @date 2018/9/7
 * @desc BaseMvpActivity
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseMvpActivity<in V : IView, P : IPresenter<V>> : BaseActivity(), IView {

    /**
     * Presenter
     */
    protected var mPresenter: P? = null

    protected abstract fun createPresenter(): P

    override fun initView() {
        mPresenter = createPresenter()
        mPresenter?.attachView(this as V)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.detachView()
        this.mPresenter = null
    }

}