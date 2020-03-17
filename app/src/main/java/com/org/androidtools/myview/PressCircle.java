package com.org.androidtools.myview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.org.androidtools.R;

public class PressCircle extends View {

    private Context mContext;
    //seekbar变化监听
    private OnSeekChangeListener mListener;
    //外圆环的paint
    private Paint circleColor;
    //内圆paint
    private Paint innerColor;
    //普通圆环
    private Paint circleRing;
    //危险圆环
    private Paint circleRingDangerous;
    //移动后的角度
    private float angle = 0;
    //12点位置的开始角度
    private int startAngle = 270;
    //圆环的宽度
    private int barWidth = (int) getTextSizeDip(10);
    //标记大小是圆环宽度的倍数
    private float seekMultiple = 3.0f;
    //view的宽
    private int width;
    //view 的高
    private int height;
    //seekbar的总值
    private int maxProgress = 100;
    //当前的百分值
    private float progress;
    //百分值
    private float progressPercent;
    //内圆半径
    private float innerRadius;
    //外圆半径
    private float outerRadius;
    //圆心x坐标
    private float cx;
    //圆心y坐标
    private float cy;
    //画圆图层左边距离
    private float left;
    //画圆图层右边距离
    private float right;
    //画圆图层上边距离
    private float top;
    //画圆图层下边距离
    private float bottom;
    //背景图层左边距离
    private float bgLeft;
    //背景图层右边距离
    private float bgRight;
    //背景图层上边距离
    private float bgTop;
    //背景图层下边距离
    private float bgBottom;
    //progressMark x坐标
    private float dx;
    //progressMark y坐标
    private float dy;
    //12点钟位置x坐标
    private float startPointX;
    //12点钟位置y坐标
    private float startPointY;
    //终点x坐标
    private float endPointX;
    //终点Y坐标
    private float endPointY;
    //标记的seekbar当前位置的x坐标，预设值12点位置
    private float markPointX;
    //标记的seekbar当前位置的Y坐标，预设值在12点位置
    private float markPointY;
    //进度调整指数
    private float adjustmentFactor = 100;
    //手指按下后正常的进度条
    private Bitmap progressNormalMark;
    //危险进度条
    private Bitmap progressDangerousMark;
    //中间背景图
    private Bitmap progressBg;
    //是否手指按下的标记
    private boolean IS_PRESSED = false;
    //当前圆弧的角度
    private float degrees;
    //上次弧度的调度
    private float lastDegrees;
    //是否危险标记
    private boolean isDangerous = false;
    //是否画环形尾巴上的标记
    private boolean isDrawSeek = true;
    //绘制圆中间的背景图Paint
    private Paint paint = null;
    private Canvas canvas = new Canvas();
    //圆图层
    private RectF rect = new RectF();
    //背景图层
    private RectF rectBg = new RectF();

    /**
     * {}
     * 局部代码块：
     * 局部位置，用于限定变量的生命周期。
     * 构造代码块：
     * 在类中的成员位置，用{}括起来的代码。每次调用构造方法执行前，都会先执行构造代码块。
     * 　　　　作用：可以把多个构造方法中的共同代码放到一起，对对象进行初始化。
     * 静态代码块：
     * 在类中的成员位置，用{}括起来的代码，用static修饰。
     * 　　　　作用：一般是对类进行初始化。
     * 三者的执行顺序：
     * 静态代码块-->构造代码块-->构造方法
     * @param context
     */
    {
        /**
         * Paint.ANTI_ALIAS_FLAG ：抗锯齿标志
         * Paint.FILTER_BITMAP_FLAG : 使位图过滤的位掩码标志
         * Paint.DITHER_FLAG : 使位图进行有利的抖动的位掩码标志
         * Paint.UNDERLINE_TEXT_FLAG : 下划线
         * Paint.STRIKE_THRU_TEXT_FLAG : 中划线
         * Paint.FAKE_BOLD_TEXT_FLAG : 加粗
         * Paint.LINEAR_TEXT_FLAG : 使文本平滑线性扩展的油漆标志
         * Paint.SUBPIXEL_TEXT_FLAG : 使文本的亚像素定位的绘图标志
         * Paint.EMBEDDED_BITMAP_TEXT_FLAG : 绘制文本时允许使用位图字体的绘图标志
         */
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circleColor = new Paint();
        innerColor = new Paint();
        circleRing = new Paint();
        circleRingDangerous = new Paint();
        //设置外圆颜色蓝
        circleColor.setColor(Color.parseColor("#1A574cfc"));
        //设置内圆颜色白
        innerColor.setColor(Color.parseColor("#ffffff"));
        //设置外环普通颜色
        circleRing.setColor(Color.parseColor("#574cfc"));
        //设置圆环危险颜色
        circleRingDangerous.setColor(Color.parseColor("#ff635b"));
        //设置抗锯齿
        circleColor.setAntiAlias(true);
        innerColor.setAntiAlias(true);
        circleRing.setAntiAlias(true);
        circleRingDangerous.setAntiAlias(true);
        //设置笔宽
        circleColor.setStrokeWidth(5);
        innerColor.setStrokeWidth(5);
        circleRing.setStrokeWidth(5);
        circleRing.setStyle(Paint.Style.FILL);
        circleRingDangerous.setStrokeWidth(5);
        circleRingDangerous.setStyle(Paint.Style.FILL);
    }

    public PressCircle(Context context) {
        super(context);
        mContext = context;
        initDrawable();
    }

    public PressCircle(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initDrawable();
    }

    public PressCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initDrawable();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PressCircle(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initDrawable();
    }

    //初始化图片
    public void initDrawable() {
        progressNormalMark = setImgSize(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.bg_button_gls),seekMultiple*barWidth,seekMultiple*barWidth);
        progressDangerousMark = setImgSize(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.bg_button_gls2),seekMultiple*barWidth,seekMultiple*barWidth);
        progressBg = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.bg_scale2);
    }

    public Bitmap setImgSize(Bitmap bm,float newWidth,float newHeight){
        int width = bm.getWidth();
        int height = bm.getHeight();
        //计算缩放比例
        float scaleWidth = newWidth / width;
        float scaleHeight = newHeight / height;
        //取得缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        //得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm,0,0,width,height,matrix,true);
        return newbm;
    }

    /**
     * 重写view计算方法
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int size = width>height?height:width;
        cx = width / 2;
        cy = height / 2;
        outerRadius = size / 2 * 90 / 100;
        innerRadius = outerRadius - barWidth;
        left = cx - outerRadius;
        right = cx + outerRadius;
        top = cy - outerRadius;
        bottom = cy + outerRadius;
        bgLeft = cx - innerRadius;
        bgRight = cx + innerRadius;
        bgTop = cy - innerRadius;
        bgBottom = cy + innerRadius;

        startPointX = getXByProgress(0);
        startPointY = getYByProgress(0) + barWidth/2;
        markPointX = getXByProgress(progress);
        markPointY = getYByProgress(progress)+barWidth/2;
        rect.set(left,top,right,bottom);
        rectBg.set(bgLeft,bgTop,bgRight,bgBottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(cx,cy,outerRadius,circleColor);
        //画圆弧
        if (isDangerous){
            canvas.drawArc(rect,startAngle,angle,true,circleRingDangerous);
        }else {
            canvas.drawArc(rect,startAngle,angle,true,circleRing);
        }
        //绘制圆弧开始尾巴
        drawMarkAtStart(canvas);
        //绘制圆弧结束尾巴
        if (!isDrawSeek){
            drawMarkAtEnd(canvas);
        }
        //画内圆
        canvas.drawCircle(cx,cy,innerRadius,innerColor);
        //画中间背景
        canvas.drawBitmap(progressBg,null,rectBg,paint);
        //获取seek点x坐标
        dx = getXFromAngle();
        //获取seek点Y坐标
        dy = getYFromAngle();
        //绘制seek位置
        if (isDrawSeek){
            drawMarkerAtProgress(canvas);
        }
        super.onDraw(canvas);
    }

    private void drawMarkerAtProgress(Canvas canvas) {
        if (isDangerous){
            canvas.drawBitmap(progressDangerousMark,dx,dy,null);
        }else {
            canvas.drawBitmap(progressNormalMark,dx,dy,null);
        }
    }

    private float getYFromAngle() {
        int size1 = progressNormalMark.getHeight();
        int size2 = progressDangerousMark.getHeight();
        int adjust = size1>size2?size1:size2;
        float y = markPointY - (adjust/2);
        return y;
    }

    private float getXFromAngle() {
        int size1 = progressNormalMark.getWidth();
        int size2 = progressDangerousMark.getWidth();
        int adjust = size1>size2?size1:size2;
        float x = markPointX - (adjust/2);
        return x;
    }

    private void drawMarkAtEnd(Canvas canvas) {
        if (degrees>0){
            endPointX = (float) (cx + (outerRadius - barWidth/2) * Math.cos(Math.PI * (angle-90)/180));
            endPointY = (float) (cy + (outerRadius - barWidth/2) * Math.sin(Math.PI * (angle-90)/180));
            if (isDangerous){
                canvas.drawCircle(endPointX,endPointY,barWidth/2,circleRingDangerous);
            }else {
                canvas.drawCircle(endPointX,endPointY,barWidth/2,circleRing);
            }
        }
    }

    private void drawMarkAtStart(Canvas canvas) {
        if (isDangerous){
            canvas.drawCircle(startPointX,startPointY,barWidth/2,circleRingDangerous);
        }else {
            canvas.drawCircle(startPointX,startPointY,barWidth/2,circleRing);
        }
    }

    private float getYByProgress(float progress) {
        float y = 0;
        float angle = (float) (2 * progress * Math.PI / 100);
        y = (float) (cy + outerRadius * Math.sin(angle - Math.PI / 2));
        return  y;
    }

    private float getXByProgress(float progress) {
        float x = 0;
        float angle = (float) (2 * progress * Math.PI / 100);
        x = (float) (cx + outerRadius * Math.cos(angle - Math.PI / 2));
        return x;
    }

    /**
     * 设置seekBar变化监听
     * @param listener
     */
    public void setSeekBarChangeListener(OnSeekChangeListener listener){
        mListener = listener;
    }

    /**
     * 获取seekBar变化监听
     * @return
     */
    public OnSeekChangeListener getSeekBarChangeListener(){
        return mListener;
    }

    /**
     * 定义seekbar变化监听接口
     */
    public interface OnSeekChangeListener{
        /**
         *
         * @param view
         * @param newProcess
         */
        public void onProgressChange(PressCircle view, float newProcess);
    }

    /**
     *
     * @return
     */
    public int getMaxProgress(){
        return maxProgress;
    }

    /**
     * 设置最大进度
     * @param maxProgress
     */
    public void setMaxProgress(int maxProgress){
        this.maxProgress = maxProgress;
    }

    public float getProgress(){
        return progress;
    }

    public void setProgress(float progress){
        this.progress = progress;
        if (!IS_PRESSED){
            float newPercent = this.progress * 100 / this.maxProgress;
            float newAngle = (newPercent * 360) / 100;
            this.setAngle(newAngle);
            this.setProgressPercent(newPercent);
        }
        if (mListener != null){
            mListener.onProgressChange(this,this.getProgress());
        }
    }

    /**
     * 设置圆弧角度
     * @param newAngle
     */
    private void setAngle(float newAngle) {
        this.angle = newAngle;
        float donePercent = (((float) this.angle)/360) *100;
        float progress = (donePercent/100)*getMaxProgress();
        setProgressPercent(donePercent);
        setProgress(progress);
    }

    private void setProgressPercent(float donePercent) {
        this.progressPercent = donePercent;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        boolean up = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                moved(x,y,up);
                break;
            case MotionEvent.ACTION_MOVE:
                moved(x,y,up);
                break;
            case MotionEvent.ACTION_UP:
                moved(x,y,up);
                break;
        }
        return true;
    }

    /**
     *
     * @param x
     * @param y
     * @param up
     */
    private void moved(float x, float y, boolean up) {
        if (!isDrawSeek) return;
        float distance = (float) (Math.sqrt(Math.pow(x-cx,2))
                        +Math.sqrt(Math.pow(y-cy,2)));
        if (distance<outerRadius + adjustmentFactor
        && distance > innerRadius - adjustmentFactor && !up){
            IS_PRESSED = true;
            //Math.atan2(y,x出来的结果angel是一个弧度值,也可以表示相对直角三角形对角的角，其中 x 是临边边长，而 y 是对边边长。
            float markPointx1 = (float) (cx + (outerRadius - barWidth/2)
                                * Math.cos(Math.atan2(x-cx,cy - y)-(Math.PI/2)));
            float markPointy1 = (float) (cy + (outerRadius - barWidth/2)
                                * Math.sin(Math.atan2(x-cx,cy-y)-(Math.PI/2)));
            //Math.toDegrees()用于将以弧度测量的角度转换为以度为单位的近似等效角度,也就是将-π/2到π/2之间的弧度值转化为度
            degrees = (float) ((float) (Math.toDegrees(Math.atan2(x-cx,cy-y))+360.0)%360.0);
            if (degrees<0){
                degrees += 2*Math.PI;
            }
            if (!isMove()){
                return;
            }
            lastDegrees = degrees;
            markPointX = markPointx1;
            markPointY = markPointy1;
            setAngle(degrees);
            invalidate();
        }else {
            IS_PRESSED = false;
            invalidate();
        }
    }

    private boolean isMove() {
        if (Math.abs(degrees-lastDegrees)>45){
            return false;
        }
        return true;
    }

    public void setIsDangerous(boolean isDangerous){
        this.isDangerous = isDangerous;
    }

    public void setDrawSeek(boolean drawSeek){
        this.isDrawSeek = drawSeek;
    }

    private float getTextSizeDip(float value){
        //把Android系统中的非标准度量尺寸转变为标准度量尺寸 (Android系统中的标准尺寸是px, 即像素)
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value,getResources().getDisplayMetrics());
    }
}
