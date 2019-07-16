package com.hao.show.moudle.main.novel

import android.annotation.SuppressLint
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.view.ViewPager
import android.view.WindowManager
import com.hao.lib.base.Rx.Rx
import com.hao.lib.base.Rx.RxMessage
import com.hao.show.R
import com.hao.show.base.BaseActivity
import com.hao.show.db.manage.DBManager
import com.hao.show.moudle.main.novel.Entity.NovelChapter
import com.hao.show.moudle.main.novel.adapter.NovelContentAdapter
import com.hao.show.spider.SpiderNovelFromBiQu
import com.hao.show.spider.SpiderUtils
import kotlinx.android.synthetic.main.activity_novel_content.*

class NovelContentActivity : BaseActivity() {
    override fun findView() {
        showLoading()
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        var novelChapter = intent.getSerializableExtra("chapter") as NovelChapter;
        novel_content.setOffscreenPageLimit(1)
        novel_content.adapter = NovelContentAdapter(this, DBManager.selectNovelChapter(novelChapter.nid));
        novel_content.currentItem = novelChapter.cid.toInt()

        showLoading()
        getDetailHtml(novelChapter)
    }

    override fun initViewID(): Int {
        return R.layout.activity_novel_content
    }

    //通过网址获取网页
    private fun getDetailHtml(no: NovelChapter) {
        Rx.getInstance().addRxMessage(object : RxMessage {
            @SuppressLint("NewApi")
            @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
            override fun rxDo(tag: Any, o: Any) {
                if (tag is NovelChapter) {
                    try {
                        setViewDate(tag, o)
                    } catch (e: Exception) {
                        runOnUiThread { setViewDate(tag, o) }
                    }
                } else if (tag.equals("reload")) {
                    showLoading()
                    var chapterList = (novel_content.adapter as NovelContentAdapter).date
                    SpiderUtils.getHtml(chapterList.get(o as Int).chapterUrl, chapterList.get(o));
                }
            }
        })
        SpiderUtils.getHtml(no.chapterUrl, no);
    }

    //解析数据
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setViewDate(no: NovelChapter, o: Any) {
        SpiderNovelFromBiQu.getNovelContent(o as String, no)
        dismisLoading()
        novel_content.adapter!!.notifyDataSetChanged()
    }
}
