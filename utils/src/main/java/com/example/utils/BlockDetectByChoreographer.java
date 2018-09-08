package com.example.utils;

import android.view.Choreographer;

import java.util.concurrent.TimeUnit;

/**
 * Created by steam_lb on 2018/9/8/008.
 */

public class BlockDetectByChoreographer {
    public static void start() {
        Choreographer.getInstance().postFrameCallback(new Choreographer.FrameCallback() {
            long lastFrameTimeNanos = 0;
            long currentFrameTimeNanos = 0;

            @Override
            public void doFrame(long frameTimeNanos) {
//                Log.d("Choreographer", "FrameCallback: " + this);
                currentFrameTimeNanos = frameTimeNanos;
                long diffMs = TimeUnit.MILLISECONDS.convert(currentFrameTimeNanos - lastFrameTimeNanos, TimeUnit.NANOSECONDS);
                lastFrameTimeNanos = frameTimeNanos;
//                Log.d("Choreographer", "diffMs: " + diffMs);
                if (diffMs > 16.6f) {
                    //一次绘制超过了多少次的16.6f
                    long droppedCount = (long) (diffMs / 16.6);
//                    Log.d("Choreographer", "droppedCount: " + droppedCount);
                    if (LogMonitor.getInstance().isMonitor()) {
                        LogMonitor.getInstance().removeMonitor();
                    }
                    LogMonitor.getInstance().startMonitor();
                }
                Choreographer.getInstance().postFrameCallback(this);
            }
        });
    }
}
