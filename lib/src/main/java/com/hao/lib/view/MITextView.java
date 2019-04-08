package com.hao.lib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.hao.lib.R;
import com.hao.lib.Util.SystemUtils;
import com.hao.lib.base.BackCall;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义一个文本展示控件
 * 通过view大小来控制展示的文字数量
 * 并能够识别换行符
 * 并针对大文本进行分页处理
 */
public class MITextView extends View {
    private String textContent = "";
    private float lineSpacingExtra;//行间距
    private float wordSpacingExtra;//字间距
    private float textSize;//文字大小
    private Context context;
    private int lineTextNum;//每行字数
    private int lineNum;//容纳的行数
    private float textColor;//字体颜色
    private Typeface typeface = Typeface.DEFAULT;
    private List<String> textArray = new ArrayList<>();//每一行数据为一个元素  以行数据为单位
    private int show = 0;//当前文本展示的页面
    float textPadingVar = 0;//垂直方向的间隔距离
    float textPadingHor = 0;//横向方向的间隔距离
    int viewWidth;
    int viewhigh;

    public MITextView(Context context) {
        this(context, null);
    }

    public MITextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MITextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        Log.i("自定义", "初始化");
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MiTextView);
        lineSpacingExtra = array.getDimension(R.styleable.MiTextView_lineSpacingExtra, 5);//行间距
        wordSpacingExtra = array.getDimension(R.styleable.MiTextView_wordSpaceExtra, 5);//字间距
        textColor = array.getDimension(R.styleable.MiTextView_textColor, 0xffffff);//颜色
        textSize = array.getDimension(R.styleable.MiTextView_textSize, SystemUtils.INSTANCE.sp2px(context, 20));//文字大小
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextColor(0xFFFFFF);
                appendText("      测试一下双方一下safdlsfk");
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewhigh = getMeasuredHeight();
        Log.i("自定义", "测量" + viewWidth + "   高度：" + viewhigh);
        lineTextNum = (int) (viewWidth / textSize);
        lineNum = (int) (viewhigh / (textSize + lineSpacingExtra));
        //                //文本垂直方向距离边缘的位置  通过计算一行被填满时所占用的位置，算出空出的位置长度,主要用于文本居中处理
        textPadingVar = (viewhigh - lineNum * ((textSize + lineSpacingExtra))) / 2;
        //文本水平方向距离边缘的位置  通过计算一行被填满时所占用的位置，算出空出的位置长度,主要用于文本居中处理
        textPadingHor = (viewWidth - lineTextNum * textSize) / 2;

    }


    private void initDate() {
        textArray = new ArrayList<>();
        String[] strs = textContent.split("\n");
        for (int i = 0; i < strs.length; i++) {
            Log.i("当前行", "strs[i]    nowLineText=" + strs[i] + "\n");
            if (strs[i].length() <= lineTextNum) {
                textArray.add(strs[i]);
                continue;
            }
            if (strs[i].length() % lineTextNum == 0) {//刚好整除  此段文字尴尬后被整数行容纳
                for (int j = 0; j < strs[i].length() / lineTextNum; j++) {
                    Log.i("当前行", "strs[i]=" + strs[i].substring(j * lineTextNum, (j + 1) * lineTextNum) + "\n" + "字数：" + strs[i].substring(j * lineTextNum, (j + 1) * lineTextNum).length());
                    textArray.add(strs[i].substring(j * lineTextNum, (j + 1) * lineTextNum));
                }
            } else {
                int needLineNum = strs[i].length() / lineTextNum + 1;
                for (int k = 0; k < needLineNum; k++) {//获取到每段的字符串 判断能够容纳几行
                    String nowLineText = "";
                    if (k < needLineNum - 1) {
                        nowLineText = strs[i].substring(k * lineTextNum, (k + 1) * lineTextNum);
                    } else {
                        nowLineText = strs[i].substring(k * lineTextNum, strs[i].length());
                    }
                    Log.i("当前行", "nowLineText=" + nowLineText + "\n" + "字数：" + nowLineText.length());
                    textArray.add(nowLineText);
                }
            }
        }
    }

    public void setText(String string) {
        textContent = string;
        invalidate();
    }

    public void appendText(String string) {
        textContent = textContent + string;
        invalidate();
    }

    public void setTextColor(int color) {
        textColor = color;
        invalidate();
    }

    public void setTextColorResuoce(int color) {
        textColor = ContextCompat.getColor(context, color);
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initDate();
        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setFakeBoldText(false);
        paint.setTypeface(typeface);
        paint.setColor(Color.WHITE);
        for (int i = 0; i < textArray.size(); i++) {
            float drawTextY = textPadingHor + (i + 1) * (textSize + lineSpacingExtra);
            for (int j = 0; j < textArray.get(i).length(); j++) {
                float drawTextX = textPadingHor + textSize * j + (int) (i / lineNum) * viewWidth;
                canvas.drawText(textArray.get(i).substring(j, j + 1), drawTextX, drawTextY, paint);
            }
        }
    }

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    //设置文本中当前展示页
    public void setPage(int show) {
        this.show = show;
    }


    float downX = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            float moveX = ev.getX() - downX;
            textPadingHor = textPadingHor + moveX;
            invalidate();
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            float moveX = ev.getX() - downX;
            if (downX > viewWidth - 100 && moveX < 10) {
                return true;
            } else if (downX < 100 && moveX < 10) {
                if (show > 0) {
                    show--;
                }
                return true;
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setValueAnimal(final BackCall backCall) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                backCall.call((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }
}
