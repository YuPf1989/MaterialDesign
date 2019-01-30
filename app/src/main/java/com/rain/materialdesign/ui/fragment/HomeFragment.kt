package com.rain.materialdesign.ui.fragment

import android.content.Intent
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.ImageView
import cn.bingoogolapple.bgabanner.BGABanner
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rain.materialdesign.App
import com.rain.materialdesign.R
import com.rain.materialdesign.base.BaseFragment
import com.rain.materialdesign.base.BaseMvpFragment
import com.rain.materialdesign.ext.showSnackMsg
import com.rain.materialdesign.ext.showToast
import com.rain.materialdesign.mvp.contract.HomeContract
import com.rain.materialdesign.mvp.model.entity.ArticleBean
import com.rain.materialdesign.mvp.model.entity.Banner
import com.rain.materialdesign.mvp.model.entity.HomePageArticleBean
import com.rain.materialdesign.mvp.presenter.HomePresenter
import com.rain.materialdesign.ui.activity.LoginActivity
import com.rain.materialdesign.ui.adapter.HomeAdapter
import com.rain.materialdesign.util.ImageLoader
import com.rain.materialdesign.util.NetWorkUtil
import com.rain.materialdesign.widget.SpaceItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.fragment_refresh_layout.*

/**
 * Author:rain
 * Date:2019/1/28 11:32
 * Description:
 */
class HomeFragment : BaseMvpFragment<HomeContract.View, HomeContract.Presenter>(), HomeContract.View {
    companion object {
        fun getInstance() = HomeFragment()
    }

    override fun createPresenter(): HomeContract.Presenter = HomePresenter()

    /**
     * datas
     */
    private val datas = mutableListOf<ArticleBean>()

    /**
     * banner datas
     */
    private lateinit var bannerDatas: ArrayList<Banner>

    /**
     * banner view
     */
    private var bannerView: View? = null

    /**
     * RecyclerView Divider
     */
    private val recyclerViewItemDecoration by lazy {
        activity?.let {
            SpaceItemDecoration(it)
        }
    }

    /**
     * Home Adapter
     */
    private val homeAdapter: HomeAdapter by lazy {
        HomeAdapter(activity, datas)
    }

    /**
     * Banner Adapter
     */
    private val bannerAdapter: BGABanner.Adapter<ImageView, String> by lazy {
        BGABanner.Adapter<ImageView, String> { bgaBanner, imageView, feedImageUrl, position ->
            ImageLoader.load(activity, feedImageUrl, imageView)
        }
    }

    /**
     * LinearLayoutManager
     */
    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(activity)
    }

    /**
     * is Refresh
     */
    private var isRefresh = true

    override fun attachLayoutRes(): Int = R.layout.fragment_refresh_layout

    override fun initView(view: View) {
        super.initView(view)
        mLayoutStatusView = multiple_status_view
        swipeRefreshLayout.run {
            setOnRefreshListener(onRefreshListener)
        }
        recyclerView.run {
            layoutManager = linearLayoutManager
            adapter = homeAdapter
            itemAnimator = DefaultItemAnimator()
            recyclerViewItemDecoration?.let { addItemDecoration(it) }
        }

        bannerView = layoutInflater.inflate(R.layout.item_home_banner, null)
        bannerView?.findViewById<BGABanner>(R.id.banner)?.run {
            setDelegate(bannerDelegate)
        }

        homeAdapter.run {
            bindToRecyclerView(recyclerView)
            setOnLoadMoreListener(onRequestLoadMoreListener, recyclerView)
            onItemClickListener = this@HomeFragment.onItemClickListener
            onItemChildClickListener = this@HomeFragment.onItemChildClickListener
            // setEmptyView(R.layout.fragment_empty_layout)
            addHeaderView(bannerView)
        }
    }

    override fun lazyLoad() {
        mLayoutStatusView?.showLoading()
        mPresenter?.requestHomeData()
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

    override fun showError(errorMsg: String) {
        super.showError(errorMsg)
        mLayoutStatusView?.showError()
        homeAdapter.run {
            if (isRefresh)
                setEnableLoadMore(true)
            else
                loadMoreFail()
        }
    }

    override fun scrollToTop() {
        recyclerView.run {
            if (linearLayoutManager.findFirstVisibleItemPosition() > 20) {
                scrollToPosition(0)
            } else {
                smoothScrollToPosition(0)
            }
        }
    }

    override fun setBanner(banners: List<Banner>) {
        bannerDatas = banners as ArrayList<Banner>
        val bannerFeedList = ArrayList<String>()
        val bannerTitleList = ArrayList<String>()
        Observable.fromIterable(banners)
            .subscribe { list ->
                bannerFeedList.add(list.imagePath)
                bannerTitleList.add(list.title)
            }
        bannerView?.findViewById<BGABanner>(R.id.banner)?.run {
            setAutoPlayAble(bannerFeedList.size > 1)
            setData(bannerFeedList, bannerTitleList)
            setAdapter(bannerAdapter)
        }
    }

    override fun setArticles(articles: HomePageArticleBean) {
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
        if (homeAdapter.data.isEmpty()) {
            mLayoutStatusView?.showEmpty()
        } else {
            mLayoutStatusView?.showContent()
        }
    }

    override fun showCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.collect_success))
        }
    }

    override fun showCancelCollectSuccess(success: Boolean) {
        if (success) {
            showToast(resources.getString(R.string.cancel_collect_success))
        }
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
     * ItemClickListener
     */
    private val onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
        if (datas.size != 0) {
            val data = datas[position]
//            Intent(activity, ContentActivity::class.java).run {
//                putExtra(Constant.CONTENT_URL_KEY, data.link)
//                putExtra(Constant.CONTENT_TITLE_KEY, data.title)
//                putExtra(Constant.CONTENT_ID_KEY, data.id)
//                startActivity(this)
//            }
        }
    }

    /**
     * BannerClickListener
     */
    private val bannerDelegate = BGABanner.Delegate<ImageView, String> { banner, imageView, model, position ->
        if (bannerDatas.size > 0) {
            val data = bannerDatas[position]
//            Intent(activity, ContentActivity::class.java).run {
//                putExtra(Constant.CONTENT_URL_KEY, data.url)
//                putExtra(Constant.CONTENT_TITLE_KEY, data.title)
//                putExtra(Constant.CONTENT_ID_KEY, data.id)
//                startActivity(this)
//            }
        }
    }

    /**
     * ItemChildClickListener
     */
    private val onItemChildClickListener =
        BaseQuickAdapter.OnItemChildClickListener { _, view, position ->
            if (datas.size != 0) {
                val data = datas[position]
                when (view.id) {
                    R.id.iv_like -> {
                        if (isLogin) {
                            if (!NetWorkUtil.isNetworkConnected(App.INSTANCE)) {
                                showSnackMsg(resources.getString(R.string.no_network))
                                return@OnItemChildClickListener
                            }
                            val collect = data.collect
                            data.collect = !collect
                            // todo 先setData再请求接口貌似有问题
                            homeAdapter.setData(position, data)
                            if (collect) {
                                mPresenter?.cancelCollectArticle(data.id)
                            } else {
                                mPresenter?.addCollectArticle(data.id)
                            }
                        } else {
                            Intent(activity, LoginActivity::class.java).run {
                                startActivity(this)
                            }
                            showToast(resources.getString(R.string.login_tint))
                        }
                    }
                }
            }
        }
}