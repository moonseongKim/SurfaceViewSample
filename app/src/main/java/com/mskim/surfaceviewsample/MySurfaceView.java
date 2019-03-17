package com.mskim.surfaceviewsample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private final Context mContext;
    private final SurfaceHolder mHolder;
    private final RenderingThread mRThread;

    public MySurfaceView(Context context) {
        super(context);
        mContext = context;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mRThread = new MySurfaceView.RenderingThread();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mRThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("MySurfaceView", "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        try {
            mRThread.join();
            Log.d("MySurfaceView", "surfaceDestroyed: ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    class RenderingThread extends Thread {
        final Bitmap img_android;

        RenderingThread() {
            Log.d("RenderingThread", "RenderingThread()");
            img_android = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.android);
        }

        public void run() {
            // 단말기마다 차이가 있지만 60fps로 동작.
            Log.d("RenderingThread", "run()");
            Canvas canvas;
            while (true) {
                canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    try {
                        synchronized (mHolder) {
                            canvas.drawBitmap(img_android, 0, 0, null);
                        }
                    } finally {
                        Log.d("RenderingThread", "run() exit 1");
                        mHolder.unlockCanvasAndPost(canvas);
                        Log.d("RenderingThread", "run() exit 2");
                    }
                } else
                    return;
            }
        }
    }
}
