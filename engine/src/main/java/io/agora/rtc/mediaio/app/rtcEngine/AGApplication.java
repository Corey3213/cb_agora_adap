package io.agora.rtc.mediaio.app.rtcEngine;

import android.app.Application;

import io.agora.rtc.mediaio.app.BaseActivity;


public class AGApplication extends Application {

    private WorkerThread mWorkerThread;

    @Override
    public void onCreate() {
        BaseActivity.agApplication = this;
        initWorkerThread();
        super.onCreate();
    }

    public synchronized void initWorkerThread() {
        if (mWorkerThread == null) {
            mWorkerThread = new WorkerThread(getApplicationContext());
            mWorkerThread.start();

            mWorkerThread.waitForReady();
        }
    }

    public synchronized WorkerThread getWorkerThread() {
        return mWorkerThread;
    }

    public synchronized void deInitWorkerThread() {
        mWorkerThread.exit();
        try {
            mWorkerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mWorkerThread = null;
    }
}
