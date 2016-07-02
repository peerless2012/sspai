package com.peerless2012.sspai.view.widget;

import java.util.Random;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;

/**
* @Author peerless2012
* @Email  peerless2012@126.com
* @HomePage http://peerless2012.github.io
* @DateTime 2016年6月15日 下午3:42:32
* @Version V1.0
* @Description: 少数派的自定义logo
*/
public class SSPaiView extends View {
	
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

	/**
	 * View缺省宽度
	 */
	private int defaultWidth;
	
	/**
	 * View缺省高度
	 */
	private int defaultHeight;
	
	private boolean isPointsDirty = false;
	
	private int mContentWidth = 0;
	
	private int centerX;
	
	private int centerY;
	
	private int radius;
	
	private int edgeWidth = 2;
	
	private float progress = 0f;

	private AnimatorListener mAnimatorListener;
	
	public SSPaiView(Context context) {
		super(context);
		init(context,null);
	}

	public SSPaiView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context,attrs);
	}
	
	public SSPaiView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	private void init(Context context, AttributeSet attrs) {
		defaultWidth = defaultHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, context.getResources().getDisplayMetrics());
		edgeWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, context.getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		
		//只要是不是精确指定的，就设置为默认宽高。
		if (widthMode == MeasureSpec.UNSPECIFIED) {
			width = defaultWidth;
		}
		if (heightMode == MeasureSpec.UNSPECIFIED) {
			height = defaultHeight;
		}
		int widthLast = Math.min(width, height);
		setMeasuredDimension(widthLast, widthLast);
		int newWidth = widthLast - getPaddingLeft() - getPaddingRight();
		int newHeight = widthLast - getPaddingTop() - getPaddingBottom();
		mContentWidth = Math.max(newWidth, newHeight);
		
		centerX = mContentWidth / 2 + getPaddingLeft();
		centerY = mContentWidth / 2 + getPaddingTop();
		radius = mContentWidth / 2 - edgeWidth;
		
		isPointsDirty = true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if (isPointsDirty) {
			initPath();
			isPointsDirty = false;
		}
		
		canvas.translate(centerX, centerY);
		
		if (progress <= 1) {
			destPath.reset();
			mPaint.setColor(Color.RED);
			mPaint.setStrokeWidth(edgeWidth);
			mPaint.setStyle(Style.STROKE);
			float length = mPathMeasure.getLength();
			float topEnd = (length / 2) * progress;
			Log.i("SSPaiView", "111111   " + topEnd);
			mPathMeasure.getSegment(0, topEnd, destPath, true);
			canvas.drawPath(destPath, mPaint);
			
			mPathMeasure.getSegment(length - topEnd, length, destPath, true);
			canvas.drawPath(destPath, mPaint);
		}else {
			mPaint.setColor(Color.RED);
			
			mPaint.setStyle(Style.STROKE);
			canvas.drawPath(mEdgePath, mPaint);
			
			float top = radius - (progress - 1) * radius *2;
			canvas.clipRect(-radius, top, radius, radius);
			
			canvas.clipPath(mTextPath, android.graphics.Region.Op.DIFFERENCE);
			
			mPaint.setColor(Color.RED);
			mPaint.setStyle(Style.FILL);
			canvas.drawPath(mEdgePath, mPaint);
		}
		
		
	}

	private Path mEdgePath = new Path();
	
	private Path destPath = new Path();
	
	private Path mTextPath = new Path();
	
	private RectF mEdgeRectF = new RectF();

	private ValueAnimator valueAnimator;
	
	PathMeasure mPathMeasure = new PathMeasure();
	
	private void initPath() {
		
		mEdgePath.reset();
		mEdgeRectF.set(-radius, -radius, radius, radius);
		mEdgePath.moveTo(radius, -radius);
		mEdgePath.lineTo(radius, 0);
		mEdgePath.arcTo(mEdgeRectF, 0, 270);
		mEdgePath.lineTo(radius, -radius);
		mPathMeasure.setPath(mEdgePath, false);
		
		mTextPath.reset();
		mTextPath.moveTo((-1.15f/1.75f) * radius, (-0.35f / 1.75f) * radius);//1
//		mTextPath.lineTo((-0.65f / 1.75f) * radius, (-0.95f / 1.75f) * radius);//2需要切换成圆弧
		mTextPath.quadTo((-1.15f/1.75f) * radius, (-0.95f / 1.75f) * radius, (-0.65f / 1.75f) * radius, (-0.95f / 1.75f) * radius);
		mTextPath.lineTo((1.15f / 1.75f) * radius, (-0.95f / 1.75f) * radius);//3
//		mTextPath.lineTo((1.35f / 1.75f) * radius, (-1.15f / 1.75f) * radius);//4需要圆弧
		mTextPath.quadTo((1.35f / 1.75f) * radius, (-0.95f / 1.75f) * radius, (1.35f / 1.75f) * radius, (-1.15f / 1.75f) * radius);
//		mTextPath.lineTo((1.1f / 1.75f) * radius, (-0.55f / 1.75f) * radius);//5 圆弧
		mTextPath.quadTo((1.35f / 1.75f) * radius, (-0.55f / 1.75f) * radius, (1.0f / 1.75f) * radius, (-0.55f / 1.75f) * radius);
		mTextPath.lineTo((0.80f / 1.75f) * radius, (-0.55f / 1.75f) * radius);//6
		mTextPath.lineTo((0.60f / 1.75f) * radius, (0.65f / 1.75f) * radius);//7
//		mTextPath.lineTo((1.05f / 1.75f) * radius, (0.55f / 1.75f) * radius);//8圆弧
		mTextPath.quadTo((0.75f / 1.75f) * radius,(0.75f / 1.75f) * radius, (1.10f / 1.75f) * radius, (0.45f / 1.75f) * radius);
//		mTextPath.lineTo((0.55f / 1.75f) * radius, (0.95f / 1.75f) * radius);//8和9中间
//		mTextPath.lineTo((0.05f / 1.75f) * radius, (0.85f / 1.75f) * radius);//9
		mTextPath.cubicTo((0.95f / 1.75f) * radius, (1.25f / 1.75f) * radius, (0.15f / 1.75f) * radius, (1.15f / 1.75f) * radius, (0.15f / 1.75f) * radius, (0.75f / 1.75f) * radius);
		mTextPath.lineTo((0.35f / 1.75f) * radius, (-0.55f / 1.75f) * radius);//10
		mTextPath.lineTo((-0.05f / 1.75f) * radius, (-0.55f / 1.75f) * radius);//11
		mTextPath.lineTo((-0.45f / 1.75f) * radius, (1.05f / 1.75f) * radius);//12
		mTextPath.lineTo((-0.95f / 1.75f) * radius, (1.05f / 1.75f) * radius);//13
		mTextPath.lineTo((-0.55f / 1.75f) * radius, (-0.55f / 1.75f) * radius);//114
		mTextPath.lineTo((-0.85f / 1.75f) * radius, (-0.55f / 1.75f) * radius);//15
//		mTextPath.lineTo((-1.15f/1.75f) * radius, (-0.35f / 1.75f) * radius);//16
		mTextPath.quadTo((-1.15f/1.75f) * radius, (-0.55f / 1.75f) * radius, (-1.15f/1.75f) * radius, (-0.35f / 1.75f) * radius);
	}
	
	private Random mRandom = new Random();
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		valueAnimator = ValueAnimator.ofFloat(0,2.0f);
		valueAnimator.setDuration(3000);
		valueAnimator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				progress = (float) animation.getAnimatedValue();
				invalidate();
			}
		});
//		valueAnimator.setRepeatCount(Animation.INFINITE);
		valueAnimator.addListener(mAnimatorListener);
		valueAnimator.start();
		
	}
	
	@Override
	protected void onDetachedFromWindow() {
		valueAnimator.removeAllListeners();
		valueAnimator.end();
		super.onDetachedFromWindow();
	}

	public void addOnAnimateListener(AnimatorListener listener){
		mAnimatorListener = listener;
	}
}
