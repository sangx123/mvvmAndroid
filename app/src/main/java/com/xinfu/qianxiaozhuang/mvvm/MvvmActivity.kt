package com.xinfu.qianxiaozhuang.mvvm

import android.app.Activity
import android.content.Context
import android.os.Bundle
import com.xinfu.qianxiaozhuang.activity.BaseActivity
import android.view.ViewGroup
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.MediaController
import android.widget.VideoView
import cn.bingoogolapple.bgabanner.BGABanner
import com.bumptech.glide.Glide
import com.xinfu.qianxiaozhuang.R
import com.xinfu.qianxiaozhuang.activity.home.BannerBean
import com.xinfu.qianxiaozhuang.mvvm.widgets.CardScaleHelper
import com.xinfu.qianxiaozhuang.utils.DisplayUtil
import com.xinfu.qianxiaozhuang.utils.viewpager.ViewPageAdapter
import kotlinx.android.synthetic.main.activity_mvvm.*
import org.jetbrains.anko.error


class MvvmActivity : BaseActivity(){
    var listViews=ArrayList<View>()
    var listVideoViews=ArrayList<VideoView>()
    private var mCardScaleHelper: CardScaleHelper? = null
    private var mLastPos = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm)
        initRecycleView()
        initBanner()

    }

    private fun initRecycleView() {

        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRecyclerView.layoutManager = linearLayoutManager
        mRecyclerView.adapter = CardAdapter(getDatas())
        // mRecyclerView绑定scale效果
        mCardScaleHelper = CardScaleHelper()
        mCardScaleHelper!!.currentItemPos = 0
        mCardScaleHelper!!.attachToRecyclerView(mRecyclerView)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLastPos == mCardScaleHelper!!.currentItemPos) return
                    mLastPos = mCardScaleHelper!!.currentItemPos
                    error { "mRecyclerView改变$mLastPos" }
                    mViewPager.currentItem=mLastPos
                }
            }
        })
    }

    private fun initBanner() {
        //初始化banner
        var list= ArrayList<VideoModel>()
        listViews=ArrayList<View>()
        var model= VideoModel()
        model.videoUrl="https://media.giphy.com/media/l0ExncehJzexFpRHq/giphy.mp4"
        list.add(model)
        model= VideoModel()
        model.videoUrl="https://media.giphy.com/media/26gsqQxPQXHBiBEUU/giphy.mp4"
        list.add(model)
        model= VideoModel()
        model.videoUrl="https://media.giphy.com/media/oqLgjAahmDPvG/giphy.mp4"
        list.add(model)
        model= VideoModel()
        model.videoUrl="https://media.giphy.com/media/d1E1szXDsHUs3WvK/giphy.mp4"
        list.add(model)
        model= VideoModel()
        model.videoUrl="https://media.giphy.com/media/OiJjUsdAb11aE/giphy.mp4"
        list.add(model)
        model= VideoModel()
        model.videoUrl="https://media.giphy.com/media/4My4Bdf4cakLu/giphy.mp4"
        list.add(model)
        val displayUtil = DisplayUtil(this)
        val width = displayUtil.screenWidth
        val height = width
        for(item in list){
            val view = LayoutInflater.from(this).inflate(R.layout.layout_vidio, null, false)
            val videoView = view.findViewById<VideoView>(R.id.video_view)
            videoView.layoutParams.height = height
            var mMediaController = MediaController(this)
            mMediaController.visibility=View.INVISIBLE
            videoView.setVideoPath(item.videoUrl)
            videoView.setMediaController(mMediaController)
            listVideoViews.add(videoView)
            listViews.add(view)
        }

        mViewPager.adapter=ViewPageAdapter(this,listViews,listVideoViews)
        rl_main.post{
            rl_main.layoutParams.height=height
        }

        mViewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(p0: Int) {
                            }

            override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                           }

            override fun onPageSelected(position: Int) {
                  error { "viewpager改变"+position }
                //todo 此处联动首位有bug
//                 if(mLastPos>position){
//                     mRecyclerView.smoothScrollToPosition(position-1)
//                 }else{
//                     mRecyclerView.smoothScrollToPosition(position+1)
//                 }
//                 mLastPos=position


            }

        })

    }


    class ViewPageAdapter( val context: Context,  var mViewList: ArrayList<View>,var mListVidieView:ArrayList<VideoView>) : PagerAdapter() {

        override fun getCount(): Int {
            return mViewList.size //页卡数
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view == obj
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(mViewList[position])
            mListVidieView[position].start()
            mListVidieView[position].setOnPreparedListener {
                it.start()
                it.isLooping=true
            }
            return mViewList[position]
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(mViewList[position])
        }
    }



    /***
     * 测试数据
     * @return List<Integer>
    </Integer> */
    fun getDatas(): List<String> {
       var list= ArrayList<String>()
        list.add("https://wpclipart.com/education/animal_numbers/animal_number_1.jpg")
        list.add("https://wpclipart.com/education/animal_numbers/animal_number_2.jpg")
        list.add("https://wpclipart.com/education/animal_numbers/animal_number_3.jpg")
        list.add("https://wpclipart.com/education/animal_numbers/animal_number_4.jpg")
        list.add("https://wpclipart.com/education/animal_numbers/animal_number_5.jpg")
        list.add("https://wpclipart.com/education/animal_numbers/animal_number_6.jpg")
        return list
    }
}
