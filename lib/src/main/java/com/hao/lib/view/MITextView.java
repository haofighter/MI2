package com.hao.lib.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AccelerateInterpolator;
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
    private int textColor;//字体颜色
    private Typeface typeface = Typeface.DEFAULT;
    private List<String> textArray = new ArrayList<>();//每一行数据为一个元素  以行数据为单位
    private int show = 0;//当前文本展示的页面
    float textPadingVar = 0;//垂直方向的间隔距离
    float textPadingHor = 0;//横向方向的间隔距离
    int pageSize = 0;//文字填充的页数

    float offsetHor = 0;//画布横向方向偏移量
    float offsetVar = 0;//画布垂直方向偏移量
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
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MITextView);
        lineSpacingExtra = array.getDimension(R.styleable.MITextView_lineSpacingExtra, 6);//行间距
        wordSpacingExtra = array.getDimension(R.styleable.MITextView_wordSpaceExtra, 5);//字间距
        textColor = array.getColor(R.styleable.MITextView_textColor, 0xFFFFFFFF);//颜色
        textSize = array.getDimension(R.styleable.MITextView_textSize, SystemUtils.INSTANCE.sp2px(context, 20));//文字大小
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
        initViewConfig();
    }


    private void initViewConfig() {
        lineTextNum = (int) (viewWidth / textSize);
        lineNum = (int) (viewhigh / (textSize + lineSpacingExtra));
        //                //文本垂直方向距离边缘的位置  通过计算一行被填满时所占用的位置，算出空出的位置长度,主要用于文本居中处理
        offsetVar = textPadingVar = (viewhigh - lineNum * ((textSize + lineSpacingExtra))) / 2;
        //文本水平方向距离边缘的位置  通过计算一行被填满时所占用的位置，算出空出的位置长度,主要用于文本居中处理
        offsetHor = textPadingHor = (viewWidth - lineTextNum * textSize) / 2;
        Log.i("自定义", "测量" + viewWidth + "   高度：" + viewhigh + "     每页行数：" + lineNum + "     每行字数：" + lineTextNum + "  字体大小：" + textSize);
    }

    private void initDate() {
        textArray = new ArrayList<>();
        String[] strs = textContent.split("\n");
        for (int i = 0; i < strs.length; i++) {
            if (strs[i].length() <= lineTextNum) {
                textArray.add(strs[i]);
                continue;
            }
            if (strs[i].length() % lineTextNum == 0) {//刚好整除  此段文字尴尬后被整数行容纳
                for (int j = 0; j < strs[i].length() / lineTextNum; j++) {
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
                    textArray.add(nowLineText);
                }
            }
        }

        if (textArray.size() % lineNum == 0) {
            pageSize = textArray.size() / lineNum;
        } else {
            pageSize = textArray.size() / lineNum + 1;
        }

        Log.i("textArray", "textArray=" + textArray.size() + "   textArray.size()=" + textArray.size() + "   lineNum=" + lineNum);
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
        paint.setColor(textColor);
        for (int i = 0; i < textArray.size(); i++) {
            float drawTextY = offsetVar + (i % lineNum + 1) * (textSize + lineSpacingExtra);
            for (int j = 0; j < textArray.get(i).length(); j++) {
                float drawTextX = offsetHor + textSize * j + (int) (i / lineNum) * viewWidth;
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
        offsetVar = textPadingHor + show * viewWidth;
        invalidate();
    }


    float fingerNowX = 0;
    float fingerNowY = 0;
    float moveX = 0;//拖动的距离
    float moveY = 0;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            fingerNowX = ev.getX();
            fingerNowY = ev.getY();
//            attemptClaimDrag();
            return true;
        } else if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            moveX = moveX + ev.getX() - fingerNowX;
            moveY = moveY + ev.getY() - fingerNowY;
            fingerNowX = ev.getX();
            fingerNowY = ev.getY();
            if ((moveX < 0 && show < pageSize - 1) || (moveX > 0 && show > 0)) {
                offsetHor = textPadingVar + moveX - show * viewWidth;
//            offsetVar = textPadingVar + moveY;
                invalidate();
                attemptClaimDrag();
                return true;
            }
        } else if (ev.getAction() == MotionEvent.ACTION_UP) {
            Log.i("手势结束", "moveX=" + moveX + "   show=" + show + "  pageSize=" + pageSize);
            if ((moveX < 0 && show < pageSize - 1) || (moveX > 0 && show > 0)) {
                setValueAnimal(new BackCall() {
                    @Override
                    public void call(Object o) {
                        if (Math.abs(moveX) < viewWidth / 2) {
                            offsetHor = (1 - Float.parseFloat(o + "") / 100) * moveX + textPadingVar - show * viewWidth;
                        } else {
                            if (moveX < 0) {
                                offsetHor = moveX - (viewWidth + moveX) * Float.parseFloat(o + "") / 100 - show * viewWidth;
                            } else {
                                offsetHor = moveX + (viewWidth - moveX) * Float.parseFloat(o + "") / 100 - show * viewWidth;
                            }
                        }
                        invalidate();
                        attemptClaimDrag();
                        if ((int) o == 100) {//动画执行完成后 重置移动距离
                            if (Math.abs(moveX) >= viewWidth / 2) {
                                if (moveX > 0) {
                                    show--;
                                } else {
                                    show++;
                                }
                            }
                            moveX = 0;
                            moveY = 0;
                        }
                    }
                });
            } else {
                moveX = 0;
                moveY = 0;
            }
            return true;
        }
        return super.onTouchEvent(ev);
    }

    private void attemptClaimDrag() {
        ViewParent parent = getParent();
        if (parent != null) {
            // 如果控件有父控件，那么请求父控件不要劫取事件
            // 以便此控件正常处理所有触摸事件
            // 而不是被父控件传入ACTION_CANCEL去截断事件
            parent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public void setValueAnimal(final BackCall backCall) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 100);
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                backCall.call((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }
}