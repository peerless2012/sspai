package com.peerless2012.sspai.view.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.peerless2012.sspai.R;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2015年7月11日 下午5:04:39
* @Version V1.0
* @Description: 支持百分比的FrameLayout
*/
public class PercentFrameLayout extends FrameLayout {
	
	private float mWidthPercent = -1f;
	
	private float mHeightPercent = -1f;
	
	public PercentFrameLayout(Context context) {
		this(context,null);
	}
	
	public PercentFrameLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public PercentFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		
		TypedArray typedArray = getResources().obtainAttributes(attrs, R.styleable.PercentFrameLayout);
		mHeightPercent = typedArray.getFloat(R.styleable.PercentFrameLayout_HeightPercent, -1);
		mWidthPercent = typedArray.getFloat(R.styleable.PercentFrameLayout_WidthPercent, -1);
		typedArray.recycle();
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(widthMeasureSpec);
		if (mHeightPercent > 0) {
			super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec((int)(width * mHeightPercent), heightMode));
//			height = (int)(height * mHeightPercent);
		}else if (mWidthPercent > 0) {
			super.onMeasure(MeasureSpec.makeMeasureSpec((int)(height * mWidthPercent), widthMode),heightMeasureSpec);
//			width = (int)(width * mWidthPercent);
		}else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
//		setMeasuredDimension(width,height);
	}

	/**
	 * 设置宽度对于高度的比例
	 * @param widthPercent 比例
	 */
	public void setWidthPercent(float widthPercent) {
		this.mWidthPercent = widthPercent;
		mHeightPercent = -1;
		requestLayout();
	}
	
	/**
	 * 设置高度对于宽度的比例
	 * @param heightPercent 比例
	 */
	public void setHeightPercent(float heightPercent) {
		this.mHeightPercent = heightPercent;
		mWidthPercent = -1;
		requestLayout();
	}
}
