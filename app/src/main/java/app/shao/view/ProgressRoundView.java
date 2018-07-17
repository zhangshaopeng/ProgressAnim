package app.shao.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import java.util.Random;
import app.shao.com.R;

/**
 * 自定义进度的progress
 */

public class ProgressRoundView extends View {

    /**
     * 画实心圆的画笔
     */
    private Paint mCirclePaint;
    /**
     * 画圆环的画笔
     */
    private Paint mRingPaint;
    /**
     * 画圆环的画笔背景色
     */
    private Paint mRingPaintBg;
    /**
     * 画字体的画笔
     */
    private Paint mTextPaint;
    /**
     * 画提示字体的画笔
     */
    private Paint mTextHintPaint;
    /**
     * 圆形颜色
     */
    private int mCircleColor;
    /**
     * 圆环颜色
     */
    private int mRingColor;
    /**
     * 圆环背景颜色
     */
    private int mRingBgColor;
    /**
     * 半径
     */
    private float mRadius;
    /**
     * 圆环半径
     */
    private float mRingRadius;
    /**
     * 圆环宽度
     */
    private float mStrokeWidth;
    /**
     * 圆心x坐标
     */
    private int mXCenter;
    /**
     * 圆心y坐标
     */
    private int mYCenter;
    /**
     * 字的长度
     */
    private float mTxtWidth;
    /**
     * 提示字长度
     */
    private float mTxtHintWidth;
    /**
     * 字的高度
     */
    private float mTxtHeight;
    /**
     * 提示字的高度
     */
    private float mTxtHintHeight;
    /**
     * 圆环总进度
     */
    private int mTotalProgress = 100;
    /**
     * 圆环当前进度
     */
    private int mCurrentProgress = 0;
    /**
     * 圆环进度
     */
    private int mProgress;
    /**
     * 文字进度
     */
    private int mTextProgress;
    /**
     * 文字当前进度
     */
    private int mTextCurrentProgress = 0;
    /**
     * 进度显示
     */
    private int mTextTotalProgress;
    /**
     * 进度加载时间
     */
    public final static int MPROGRESSTIME = 4000;
    private Random rand;


    public ProgressRoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initPanit();
        rand = new Random();
    }

    /**
     * 属性设置
     *
     * @param context
     * @param attrs
     */
    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.TasksCompletedView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.TasksCompletedView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.TasksCompletedView_strokeWidth, 7);
        mCircleColor = typeArray.getColor(R.styleable.TasksCompletedView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.TasksCompletedView_ringColor, 0xFFFFFFFF);
        mRingBgColor = typeArray.getColor(R.styleable.TasksCompletedView_ringBgColor, 0xFFFFFFFF);

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    /**
     * 初始化画笔
     */
    private void initPanit() {
        //最外层圆
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        //空心
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setStrokeWidth(5);

        //与进度重叠圆
        mRingPaintBg = new Paint();
        mRingPaintBg.setAntiAlias(true);
        mRingPaintBg.setColor(mRingBgColor);
        mRingPaintBg.setStyle(Paint.Style.STROKE);
        mRingPaintBg.setStrokeWidth(5);

        //进度圆
        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        // 圆角画笔
        mRingPaint.setStrokeCap(Paint.Cap.ROUND);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        //中间进度字
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setColor(mRingColor);
        mTextPaint.setTextSize(mRadius / 2);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

        //提示字
        mTextHintPaint = new Paint();
        mTextHintPaint.setAntiAlias(true);
        mTextHintPaint.setStyle(Paint.Style.FILL);
        mTextHintPaint.setColor(mRingColor);
        mTextHintPaint.setTextSize(mRadius / 5);
        Paint.FontMetrics fm1 = mTextHintPaint.getFontMetrics();
        mTxtHintHeight = (int) Math.ceil(fm1.descent - fm1.ascent);

    }

    /**
     * 开始画图
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;
        //内圆
        canvas.drawCircle(mXCenter, mYCenter, mRadius + 50, mCirclePaint);
        //外圆弧背景
        RectF oval1 = new RectF();
        oval1.left = (mXCenter - mRingRadius);
        oval1.top = (mYCenter - mRingRadius);
        oval1.right = mRingRadius * 2 + (mXCenter - mRingRadius);
        oval1.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
        //圆弧所在的椭圆对象、圆弧的起始角度、圆弧的角度、是否显示半径连线
        canvas.drawArc(oval1, 0, 360, false, mRingPaintBg);

        //外圆弧
        if (mProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            canvas.drawArc(oval, -90, ((float) mProgress / mTotalProgress) * 360, false, mRingPaint);

            //字体
            String txt = mTextProgress + "%";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter - 30 + mTxtHeight / 4, mTextPaint);


            String txt1 = "查询成功率";
            mTxtHintWidth = mTextHintPaint.measureText(txt1, 0, txt1.length());
            canvas.drawText(txt1, mXCenter - mTxtHintWidth / 2, mYCenter + 70 + mTxtHintHeight / 4, mTextHintPaint);


        }
    }

    /**
     * 设置进度调进度
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.mTextTotalProgress = progress;
        mProgress = 0;
        mTextProgress = 0;
        mCurrentProgress = 0;
        mTextCurrentProgress = 0;
        new Thread(new ProgressRunable()).start();
        new Thread(new TextProgressRunable()).start();
    }

    /**
     * 圆环进度
     */
    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                mProgress = mCurrentProgress;
                postInvalidate();//重绘
                try {
                    Thread.sleep(4000 / mTotalProgress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 文字进度
     */
    class TextProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mTextCurrentProgress < mTextTotalProgress) {
                mTextCurrentProgress += 1;
                //使用随机数
                mTextProgress = rand.nextInt(40) + 26;
                if (mTextCurrentProgress + 1 == mTextTotalProgress) {
                    mTextProgress = mTextTotalProgress;
                    return;
                }
                try {
                    Thread.sleep(MPROGRESSTIME / mTextTotalProgress);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 查询完页面展示
     */
    public void setView() {
        mCirclePaint.setStrokeWidth(0);
        mCirclePaint.setColor(Color.argb(0, 0, 0, 0));
        mCirclePaint.setStrokeWidth(0);
        mRingPaint.setStrokeWidth(0);
        mRingPaint.setColor(Color.argb(0, 0, 0, 0));
        mRingPaint.setStrokeWidth(0);
        mRingPaintBg.setColor(Color.rgb(61, 145, 220));
        //重新绘制
        postInvalidate();
    }

    public void onAgine() {
        initPanit();
        setProgress(0);
        postInvalidate();
    }
}
