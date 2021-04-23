package com.zwt.zwttransmit.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.zwt.zwttransmit.R;

public class SideBar extends AppCompatTextView {
    public static String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    // 画笔
    private Paint textPaint;
    private Paint bigTextPaint;

    private ISideBarSelectCallBack callBack;

    //事件触碰时的y轴坐标
    private float eventY;
    private float w;
    private float sideTextWidth;

    //是否重新测量宽高
    private boolean isTouching = false;
    //一个字母的高度
    private float itemH;

    //振幅
    private float A = dp(50);
    //波峰与bigText之间的距离
    private int gapBetweenText = dp(10);
    //开口数量
    private int openCount = 5;
    //字体缩放，基于textSize
    private float fontScale = 1;
    //最大字体
    private float bigTextSize;

    public SideBar(@NonNull Context context) {
        super(context);
        init(null);
    }

    public SideBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public SideBar(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public SideBar setFontScale(float fontScale){
        this.fontScale = fontScale;
        return this;
    }

    public void setDataResource(String[] data) {
        letters = data;
        invalidate();
    }

    public void setOnStrSelectCallBack(ISideBarSelectCallBack callBack) {
        this.callBack = callBack;
    }

    public SideBar setBigTextSize(float bigTextSize) {
        this.bigTextSize = bigTextSize;
        bigTextPaint.setTextSize(bigTextSize);
//        invalidate();
        return this;
    }

    public SideBar setA(float a) {
        A = a;
//        invalidate();
        return this;
    }

    public SideBar setGapBetweenText(int gapBetweenText) {
        this.gapBetweenText = gapBetweenText;
//        invalidate();
        return this;
    }

    public SideBar setOpenCount(int openCount) {
        this.openCount = openCount;
//        invalidate();
        return this;
    }

    private void caculateAW(int height) {
        // 计算单个字母高度，-2为了好看
        itemH = (height) * 1.0f / letters.length;
        // 开口宽度
        float opendWidth = itemH * openCount;
        //角速度 2PI/t 周期
        w = (float) (Math.PI * 2.0f / (opendWidth * 2));
    }

    private int dp(int v) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (v * scale + 0.5f);
    }

    // 初始化
    private void init(AttributeSet attrs){
        if (attrs != null) {
            @SuppressLint("Recycle")
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SideBar);
            A = dp(typedArray.getInteger(R.styleable.SideBar_A, 100));
            fontScale = typedArray.getFloat(R.styleable.SideBar_fontScale, 1);
            bigTextSize = typedArray.getFloat(R.styleable.SideBar_bigTextSize, getTextSize() * 3);
            gapBetweenText = dp(typedArray.getInteger(R.styleable.SideBar_gapBetweenText, 50));
            openCount = typedArray.getInteger(R.styleable.SideBar_openCount, 13);
        } else {
            bigTextSize = getTextSize() * 3;
        }
        // 设置画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(getCurrentTextColor());
        textPaint.setTextSize(getTextSize());
        textPaint.setTextAlign(Paint.Align.CENTER);

        bigTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bigTextPaint.setColor(getCurrentTextColor());
        bigTextPaint.setTextSize(bigTextSize);
        bigTextPaint.setTextAlign(Paint.Align.CENTER);


        float sideTextHeight = textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent;
        sideTextWidth = textPaint.measureText("W");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //计算一边隆起的数量
        int singleSideCount = openCount / 2;
        //如果过已经触碰，且触碰的y坐标在屏幕范围内，index 就等与（触碰的y坐标值 / 每个字母的高多值）<==> 触碰的字母下标值
        int index = isTouching && eventY >= 0 && eventY <= getMeasuredHeight() ? (int) Math.floor((eventY / itemH)) : -(singleSideCount + 1);
//        index=Math.min(letters.length,index);
//        CLog.e("index:" + index + "eventY:" + eventY);
        //sidebar x轴中心点到右边边框的距离
        float sideX = sideTextWidth / 2 + getPaddingRight();
        // 画每一个字母
        for (int i = 0; i < letters.length; i++) {
            //rest textsize
            textPaint.setTextSize(getTextSize());
            int y = (int) (itemH * (i + 1));
            int x;
            if (Math.abs(i - index) > singleSideCount) { //要画字母的 y 轴位置不在弧度隆起的范围内
                x = (int) (getMeasuredWidth() - sideX);
            } else { //计算弧度范围内的字母坐标
                float percent = eventY / itemH;
                int t = (int) (i * itemH - eventY);
                double v = A * Math.sin(w * t + Math.PI / 2);

                //如果算出来小于字体宽度 就取字体宽度
                v = Math.max(v, sideX);
                x = (int) (getMeasuredWidth() - v);
                //根据delta缩放字体
                if (v == sideX) {
                    textPaint.setTextSize(getTextSize());
                } else {
                    float delta = (Math.abs((i - percent)) / singleSideCount);
                    float textSize = getTextSize() + (1 - delta) * getTextSize() * fontScale;
//                    textSize=Math.max(textSize,getTextSize());
                    textPaint.setTextSize(textSize);
                }

            }
            // 画出字母
            canvas.drawText(letters[i], x, y, textPaint);
        }
        //判断是否有触碰的 字母 item，有就画出大字母图标
        if (index != -(singleSideCount + 1)) {
            index = Math.max(index, 0);
            // 会有超出界限的问题，
            index = Math.min(index, letters.length-1);
            // 画出最大的那个字母
            canvas.drawText(letters[index], getPaddingLeft() + getBigTextWidth() / 2, (int) (itemH * (index + 1)), bigTextPaint);
            if (callBack != null) {
                // 将触碰的字母坐标，和字母回掉给Activity界面
                callBack.onSelectStr(index, letters[index]);
            }
        }
    }

    // 获得字母的宽度，这里获得 W 字母的宽度
    private float getBigTextWidth() {
        return bigTextPaint.measureText("W");
    }

    /**
     * 触碰事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int startTouchX = (int) (getMeasuredWidth() - A);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN://第一个触点按下之后
            case MotionEvent.ACTION_MOVE://当触点在屏幕上移动时触发，触点在屏幕上停留也是会触发的，主要是由于它的灵敏度很高，而我们的手指又不可能完全静止（即使我们感觉不到移动，但其实我们的手指也在不停地抖动）
                if (event.getX() > startTouchX) {
                    eventY = event.getY();
                    if (!isTouching) {
                        isTouching = true;
                        requestLayout();
                    } else {
                        invalidate();
                    }

                } else {
                    if (isTouching) {
                        resetDefault();
                    }
                }
                return true;
            case MotionEvent.ACTION_CANCEL://不是由用户直接触发，由系统在需要的时候触发，例如当父view通过使函数onInterceptTouchEvent()返回true,从子view拿回处理事件的控制权时，就会给子view发一个ACTION_CANCEL事件，子view就再也不会收到后续事件
            case MotionEvent.ACTION_UP://当触点松开时被触发
                resetDefault();
                return true;

        }
        return super.onTouchEvent(event);
    }

    private void resetDefault() {
        isTouching = false;
        eventY = 0;
        requestLayout();
    }

    //设置自定义view大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int viewWidth = MeasureSpec.getSize(widthMeasureSpec);
        caculateAW(MeasureSpec.getSize(heightMeasureSpec));
        if (mode == MeasureSpec.UNSPECIFIED || mode == MeasureSpec.AT_MOST) {
            viewWidth = !isTouching ? (int) (sideTextWidth + getPaddingLeft() + getPaddingRight()) : (int) (A + gapBetweenText + getBigTextWidth() + getPaddingLeft() + getPaddingRight());
        }
//        CLog.e("width:" + viewWidth + "height:" + MeasureSpec.getSize(heightMeasureSpec));
        setMeasuredDimension(viewWidth, MeasureSpec.getSize(heightMeasureSpec));
    }

    public interface ISideBarSelectCallBack {
        void onSelectStr(int index,String selectStr);
    }
}