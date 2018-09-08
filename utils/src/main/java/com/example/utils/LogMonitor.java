package com.example.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steam_lb on 2018/9/8/008.
 */

public class LogMonitor {
    private static LogMonitor sInstance = new LogMonitor();
    private HandlerThread mLogThread = new HandlerThread("log");
    private Handler mIoHandler;
    private static final long TIME_BLOCK = 1000L;
    public static List<Runnable> mTasks = new ArrayList<>();
    private static boolean isStart = false;

    private LogMonitor() {
        mLogThread.start();
        mIoHandler = new Handler(mLogThread.getLooper());
    }

    private static Runnable mLogRunnable = new Runnable() {

        @Override
        public void run() {
            isStart = true;
            StringBuilder sb = new StringBuilder();
            StackTraceElement[] stackTrace = Looper.getMainLooper().getThread().getStackTrace();
            for (StackTraceElement s : stackTrace) {
                sb.append(s.toString() + "\n");
            }
            Log.w("LogMonitor", sb.toString());
            isStart = false;
        }
    };

    public static LogMonitor getInstance() {
        return sInstance;
    }

    public boolean isMonitor() {
        return mTasks.contains(mLogRunnable) && isStart;
    }

    public void startMonitor() {
        if (!mTasks.contains(mLogRunnable)) {
            mTasks.add(mLogRunnable);
        }
        mIoHandler.postDelayed(mLogRunnable, TIME_BLOCK);
    }

    public void removeMonitor() {
        if (mTasks.contains(mLogRunnable)) {
            mTasks.remove(mLogRunnable);
        }
        mIoHandler.removeCallbacks(mLogRunnable);
    }
}
