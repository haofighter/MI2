package com.hao.show.moudle.main.novel

import android.util.Log
import com.hao.mi2.base.Rx.Rx
import com.hao.mi2.base.Rx.RxMessage
import com.hao.show.R
import com.hao.show.base.BaseActivity
import com.hao.show.moudle.main.novel.adapter.NovelContentAdapter
import com.hao.show.spider.SpiderNovelFromBiQu
import com.hao.show.spider.SpiderUtils
import kotlinx.android.synthetic.main.activity_novel_content.*

class NovelContentActivity : BaseActivity() {
    override fun findView() {
        showLoading()
        getDetailHtml(intent.getStringExtra("chapterUrl"))
    }

    override fun initViewID(): Int {
        return R.layout.activity_novel_content
    }

    //通过网址获取网页
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
        SpiderUtils.getHtml(url, "content");
    }

    //解析数据
    private fun setViewDate(string: String, o: Any) {
        if (string.equals("content")) {
            dismisLoading()
            val novelContent = SpiderNovelFromBiQu.getNovelContent(o as String)
            novel_content.adapter = NovelContentAdapter(this, novelContent);
        }
    }
}