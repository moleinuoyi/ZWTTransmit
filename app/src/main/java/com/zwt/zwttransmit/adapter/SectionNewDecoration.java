package com.zwt.zwttransmit.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SectionNewDecoration extends RecyclerView.ItemDecoration{
    private final Paint paint;
    private final int mDecorationHeight = 150;
    private final OnTagListener listener;
    private final TextPaint mTextPaint;
    private final Paint.FontMetrics fontMetrics;

    public SectionNewDecoration(OnTagListener listener) {
        super();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#FFFFFF"));
        this.listener = listener;

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.DKGRAY);
        mTextPaint.setTextSize(48);
        mTextPaint.setTextAlign(Paint.Align.LEFT);
        fontMetrics = mTextPaint.getFontMetrics();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int position = parent.getChildAdapterPosition(view);
        if(!listener.getTag(position).equals("-1")){
            outRect.top = mDecorationHeight;
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int childCount = parent.getChildCount();
        int left = parent.getLeft();
        int right = parent.getRight();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int decorationBottom = child.getTop();  //item的top 也就是decoration的bottom
            int top = decorationBottom - mDecorationHeight; //计算出decoration的top
            c.drawRect(left,top,right,decorationBottom,paint); //绘制
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int itemCount = state.getItemCount();//获取所有item的数量
        int childCount = parent.getChildCount();//获取当前屏幕显示的item数量
        int left = parent.getLeft();
        int right = parent.getRight();
        String preTag;
        String curTag = null;
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);//获取在列表中的位置
            preTag = curTag;
            curTag = listener.getTag(position);//获取当前位置tag
            if (curTag==null|| TextUtils.equals(preTag,curTag) || curTag.equals("-1")) //如果两个item属于同一个tag，就不绘制
                continue;

            int bottom = child.getBottom(); //获取item 的bottom
            float tagBottom = Math.max(mDecorationHeight,child.getTop());//计算出tag的bottom
            if (position+1<itemCount)  //判断是否是最后一个
            {
                String nextTag = listener.getTag(position + 1); //获取下个tag
                if (!TextUtils.equals(curTag,nextTag)&&bottom<tagBottom) //被顶起来的条件 当前tag与下个tag不等且item的bottom已小于分割线高度
                {
                    tagBottom = bottom; //将item的bottom值赋给tagBottom 就会实现被顶上去的效果
                }
            }
            c.drawRect(left,tagBottom-mDecorationHeight,right,tagBottom,paint); //绘制tag文字
//            c.drawText(curTag,right/2,tagBottom-mDecorationHeight/2,mTextPaint); //将tag绘制出来
            float distance = (fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
            float baseline = (tagBottom-mDecorationHeight/2)+distance;
            c.drawText(curTag,50,baseline,mTextPaint); //将tag绘制出来
        }

    }


    public interface OnTagListener{
        String getTag(int position); //为了获取当前tag
    }
}
