package com.zziafyc.wanandroidkotlin.ui.fragment

import android.annotation.SuppressLint
import android.util.Log
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhouwei.mzbanner.MZBannerView
import com.zhouwei.mzbanner.holder.MZHolderCreator
import com.zziafyc.wanandroidkotlin.R
import com.zziafyc.wanandroidkotlin.adapter.HomeAdapter
import com.zziafyc.wanandroidkotlin.adapter.viewholder.BannerViewHolder
import com.zziafyc.wanandroidkotlin.base.BaseMvpFragment
import com.zziafyc.wanandroidkotlin.mvp.contract.HomeContract
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Article
import com.zziafyc.wanandroidkotlin.mvp.model.bean.ArticleResponseBody
import com.zziafyc.wanandroidkotlin.mvp.model.bean.Banner
import com.zziafyc.wanandroidkotlin.mvp.presenter.HomePresenter
import com.zziafyc.wanandroidkotlin.widget.SpaceItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_home.*

/**
 *
 * @作者 zziafyc
 * @创建日期 2019/11/18 0018
 * @description
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(),
    HomeContract.View {


    private var bannerLayout: View? = null
    private var mBannerView: MZBannerView<Banner>? = null
    private lateinit var bannerDatas: ArrayList<Banner>
    private val datas = mutableListOf<Article>()
    private var isRefresh = true


    companion object {
        fun getInstance(): HomeFragment = HomeFragment()
    }

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun initView(view: View) {
        super.initView(view)

        bannerLayout = layoutInflater.inflate(R.layout.bannerlayout, null)
        mBannerView = bannerLayout!!.findViewById(R.id.banner)
        recyclerView.run {
            layoutManager = linearLayoutManager
            homeAdapter.addHeaderView(bannerLayout)
            homeAdapter.setOnLoadMoreListener(onRequestLoadMoreListener, this)
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }

    }

    override fun lazyLoad() {
        mPresenter?.requestHomeData()
    }

    /**
     * RefreshListener
     */
    private val onRefreshListener = SwipeRefreshLayout.OnRefreshListener {
        isRefresh = true
        homeAdapter.setEnableLoadMore(false)
        mPresenter?.requestHomeData()
    }

    /**
     * LoadMoreListener
     */
    private val onRequestLoadMoreListener = BaseQuickAdapter.RequestLoadMoreListener {
        isRefresh = false
        swipeRefreshLayout.isRefreshing = false
        val page = homeAdapter.data.size / 20
        mPresenter?.requestArticles(page)
    }
    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }
    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }
    /**
     * Home Adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, datas)
    }

    override fun scrollToTop() {
    }

    @SuppressLint("CheckResult")
    override fun setBanner(banners: List<Banner>) {
        bannerDatas = banners as ArrayList<Banner>
        mBannerView?.setPages(bannerDatas,
            MZHolderCreator { BannerViewHolder() })
        mBannerView?.start()

    }

    override fun setArticles(articles: ArticleResponseBody) {
        articles.datas.let {
            homeAdapter.run {
                if (isRefresh) {
                    replaceData(it)
                } else {
                    addData(it)
                }
                val size = it.size
                if (size < articles.size) {
                    loadMoreEnd(isRefresh)
                } else {
                    loadMoreComplete()
                }
            }
        }
    }

    override fun showCollectSuccess(success: Boolean) {
    }

    override fun showCancelCollectSuccess(success: Boolean) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
        swipeRefreshLayout?.isRefreshing = false
        if (isRefresh) {
            homeAdapter.run {
                setEnableLoadMore(true)
            }
        }
    }

    override fun showDefaultMsg(msg: String) {
    }

    override fun showMsg(msg: String) {
    }

    override fun showError(errorMsg: String) {
    }
}