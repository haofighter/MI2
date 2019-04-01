package com.hao.show.moudle.main.novel

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.hao.lib.base.Rx.Rx
import com.hao.lib.base.Rx.RxMessage
import com.hao.show.R
import com.hao.show.base.BaseActivity
import com.hao.show.moudle.main.novel.Entity.NovelChapter
import com.hao.show.moudle.main.novel.Entity.NovelChapterItemAdapter
import com.hao.show.spider.SpiderNovelFromBiQu
import com.hao.show.spider.SpiderUtils
import kotlinx.android.synthetic.main.activity_novel_detail.*

class NovelDetailActivity : BaseActivity() {
    override fun findView() {
        showLoading();
        mainContentView.visibility = View.GONE
        val nowUrl = intent.getStringExtra("detailUrl");
        Log.i("获取到的小说详情", nowUrl)
        getDetailHtml(nowUrl)
        novel_chaper_list.adapter = NovelChapterItemAdapter(this)
        novel_chaper_list.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this@NovelDetailActivity, NovelContentActivity::class.java)
            intent.putExtra("chapterUrl", (novel_chaper_list.adapter.getItem(position) as NovelChapter).chapterUrl)
            startActivity(intent)
        }
    }

    override fun initViewID(): Int {
        return R.layout.activity_novel_detail;
    }

    //通过网址获取网页抓取数据
    private fun getDetailHtml(url: String) {
        Rx.getInstance().addRxMessage(object : RxMessage() {
            override fun rxDo(tag: String, o: Any) {
                Log.i("获取节点  回调", "$tag       $o")
                try {
                    setViewDate(tag, o)
                } catch (e: Exception) {
                    runOnUiThread { setViewDate(tag, o) }
                }
            }
        })
        SpiderUtils.getHtml(url, "detail");
    }

    /**
     * 抓取标题栏的数据
     *
     * @param o
     */
    fun setViewDate(tag: String, o: Any) {
        if (tag == "detail") {
            val classifies = SpiderNovelFromBiQu.getNovelDetail(o as String)
            mainContentView.visibility = View.VISIBLE
            (novel_chaper_list.adapter as NovelChapterItemAdapter).setNovelChapterList(classifies.novelChapters)
            Glide.with(this).load(classifies.imageUrl).into(novel_image)
            novel_title.text = classifies.novel_title
            novel_author.text = classifies.novel_auther
            novel_type.text = classifies.novel_type.substring(0, 3)
            novel_lenth.visibility = View.GONE
            novel_introduce.text = classifies.novel_introduce
            novel_last_chaper.text = classifies.last_chapter_name
            dismisLoading()
        }
    }
}
