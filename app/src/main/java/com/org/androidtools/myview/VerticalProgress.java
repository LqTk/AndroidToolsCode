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
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.org.androidtools.R;

public class VerticalProgress extends View {

    private Context mContext;
    private RectF outRect = new RectF();
    private RectF inRect = new RectF();
    private RectF sizeRect = new RectF();
    private int width = (int) getSizeToDp(15);
    private int height = (int) getSizeToDp(50);
    private int progress=0;
    private int maxProgress = 100;
    private OnProgressChangeListener listener;
    private Bitmap touchView;
    private float startX;
    private float startY;
    private float touchViewX;
    private float touchViewY;
    private float touchSize = 3f;
    private float left;
    private float right;
    private float top;
    private float bottom;
    private float lastY;
    private Paint outColor = new Paint();
    private Paint inColor = new Paint();
    private Paint sizeColor = new Paint();
    {
        outColor = new Paint();
        outColor.setAntiAlias(true);
        outColor.setColor(Color.GRAY);
        inColor = new Paint();
        inColor.setAntiAlias(true);
        inColor.setColor(Color.parseColor("#FF5555"));
        sizeColor = new Paint();
        sizeColor.setAntiAlias(true);
        sizeColor.setColor(Color.parseColor("#00FFFFFF"));
    }

    public VerticalProgress(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public VerticalProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public VerticalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public VerticalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    private void initView() {
        intBitmap(width);
    }

    private void intBitmap(int size) {
        touchView = createNewBitmap(BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.bg_button_gls),size,size);
    }

    private Bitmap createNewBitmap(Bitmap bm, float newHeight, float newWidth){
        int height = bm.getHeight();
        int width = bm.getWidth();
        float scaleHeight = newHeight/height;
        float scaleWidth = newWidth/width;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth,scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bm,0,0,width,height,matrix,true);
        return bitmap;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //获得为px
        width = MeasureSpec.getSize(widthMeasureSpec);
        height = MeasureSpec.getSize(heightMeasureSpec);
        int smallerSize = width<height?width:height;
        int biggerSize = width>height?width:height;
        width = smallerSize;
        height = biggerSize;
        intBitmap(smallerSize);

        left = smallerSize/3;
        right = smallerSize/3*2;
        top = touchView.getHeight()/2;
        bottom = biggerSize-touchView.getHeight()/2;

        startX = 0;
        startY = biggerSize-touchView.getHeight();
        lastY = startY;
        touchViewX = startX;
        touchViewY = startY;
        outRect.set(left,top,right,bottom);
        inRect.set(left,bottom,right,bottom);
        sizeRect.set(left,0,right,biggerSize);
        refreshView();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        refreshView();
    }

    private void refreshView(){
        float totalHeight = height-touchView.getHeight();
        float cy = totalHeight/maxProgress * progress;
        touchViewY = totalHeight-cy;
        inRect.set(left,touchViewY+touchView.getHeight()/3,right,bottom);
        if (listener!=null){
            listener.progressOnChange(this, progress);
        }
        invalidate();
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public interface OnProgressChangeListener{
        void progressOnChange(VerticalProgress verticalProgress, int progress);
    }

    public OnProgressChangeListener getListener() {
        return listener;
    }

    public void setListener(OnProgressChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float cx = event.getX();
        float cy = event.getY();
        boolean isUp = false;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                moved(cx,cy,isUp);
                break;
            case MotionEvent.ACTION_MOVE:
                moved(cx,cy,isUp);
                break;
            case MotionEvent.ACTION_UP:
                moved(cx,cy,isUp);
                break;
        }
        return true;
    }

    private void moved(float cx, float cy, boolean isUp) {
        Log.d("xy=","x="+cx+",y="+cy+",bottom="+bottom+",height="+height);
        if (cx>=0 && cx<=width
        && cy<height-touchView.getHeight() && cy>=0 && !isUp){
//            touchViewY = cy;
//            inRect.set(left,cy+touchView.getHeight()/3,right,bottom);
            int progress = Math.round((bottom-cy-touchView.getHeight()/2)/(height-touchView.getHeight())*maxProgress);
            setProgress(progress);
//            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(sizeRect,sizeColor);
        canvas.drawRect(outRect,outColor);
        canvas.drawRect(inRect,inColor);
        canvas.drawBitmap(touchView,touchViewX, touchViewY,null);
    }

    private float getSizeToDp(float width){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,width,getResources().getDisplayMetrics());
    }
    private float getSizeToPx(float width){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX,width,getResources().getDisplayMetrics());
    }
}
