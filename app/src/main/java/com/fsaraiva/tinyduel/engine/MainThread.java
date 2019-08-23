package com.fsaraiva.tinyduel.engine;

import android.graphics.Canvas;
//import android.util.Log;
import android.view.SurfaceHolder;

import com.fsaraiva.tinyduel.engine.utils.audio.Sample;

import java.text.DecimalFormat;

public class MainThread extends Thread
{
    //private int FPS = 30;
    //private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private Stage stage;
    private boolean running;
    //public static Canvas canvas;

    private static final String TAG = MainThread.class.getSimpleName();

    private final static int    MAX_FPS = 30;                     // desired fps
    private final static int    MAX_FRAME_SKIPS = 5;              // maximum number of frames to be skipped
    private final static int    FRAME_PERIOD = 1000 / MAX_FPS;    // the frame period


    // Stuff for stats */
    private DecimalFormat df = new DecimalFormat("0.##");  // 2 dp
    // we'll be reading the stats every second
    private final static int    STAT_INTERVAL = 1000; //ms
    // the average will be calculated by storing the last n FPSs
    private final static int    FPS_HISTORY_NR = 10;
    // last time the status was stored
    private long lastStatusStore = 0;
    // the status time counter
    private long statusIntervalTimer    = 01;
    // number of frames skipped since the game started
    private long totalFramesSkipped         = 0l;
    // number of frames skipped in a store cycle (1 sec)
    private long framesSkippedPerStatCycle  = 0l;
    // number of rendered frames in an interval
    private int frameCountPerStatCycle = 0;
    private long totalFrameCount = 0l;
    // the last FPS values
    private double  fpsStore[];
    // the number of times the stat has been read
    private long    statsCount = 0;
    // the average FPS since the game started
    private double  averageFps = 0.0;

    public MainThread(SurfaceHolder surfaceHolder, Stage stage)
    {
        super();
        this.surfaceHolder = surfaceHolder;
        this.stage = stage;
    }
    // https://www.javacodegeeks.com/2011/07/android-game-development-game-loop.html

    @Override
    public void run()
    {
        Canvas canvas;

        //Log.d(TAG, "Starting game loop");
        long beginTime;     // the time when the cycle begun
        long timeDiff;      // the time it took for the cycle to execute
        int sleepTime;      // ms to sleep (<0 if we're behind)
        int framesSkipped;  // number of frames being skipped

        // initialise timing elements for stat gathering
        initTimingElements();
        sleepTime = 0;

        while(running) {
            //startTime = System.nanoTime();
            canvas = null;

            // try locking the canvas for pixel editing
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;  // resetting the frames skipped
                    this.stage.update();
                    this.stage.draw(canvas);

                    timeDiff = System.currentTimeMillis() - beginTime;        // calculate how long did the cycle take
                    sleepTime = (int)(FRAME_PERIOD - timeDiff);               // calculate sleep time

                    if (sleepTime > 0) {
                        // if sleepTime > 0 we're OK
                        try {
                            // send the thread to sleep for a short period
                            // very useful for battery saving
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }
                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                        // we need to catch up, update without rendering
                        this.stage.update();
                        // add frame period to check if in next frame
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                    // for statistics
                    framesSkippedPerStatCycle += framesSkipped;
                    // calling the routine to store the gathered statistics
                    storeStats();

                }
            } finally {
                // in case of an exception the surface is not left in an inconsistent state
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    public void setRunning (boolean b)
    {
        this.running = b;
    }

    /**
     * The statistics - it is called every cycle, it checks if time since last
     * store is greater than the statistics gathering period (1 sec) and if so
     * it calculates the FPS for the last period and stores it.
     *  It tracks the number of frames per period. The number of frames since
     *  the start of the period are summed up and the calculation takes part
     *  only if the next period and the frame count is reset to 0.
     */
    private void storeStats() {
        frameCountPerStatCycle++;
        totalFrameCount++;
        // check the actual time
        statusIntervalTimer += (System.currentTimeMillis() - statusIntervalTimer);

        if (statusIntervalTimer >= lastStatusStore + STAT_INTERVAL) {
            // calculate the actual frames pers status check interval
            double actualFps = (double)(frameCountPerStatCycle / (STAT_INTERVAL / 1000));
            //stores the latest fps in the array
            fpsStore[(int) statsCount % FPS_HISTORY_NR] = actualFps;
            // increase the number of times statistics was calculated
            statsCount++;

            double totalFps = 0.0;
            // sum up the stored fps values
            for (int i = 0; i < FPS_HISTORY_NR; i++) {
                totalFps += fpsStore[i];
            }
            // obtain the average
            if (statsCount < FPS_HISTORY_NR) {
                // in case of the first 10 triggers
                averageFps = totalFps / statsCount;
            } else {
                averageFps = totalFps / FPS_HISTORY_NR;
            }
            // saving the number of total frames skipped
            totalFramesSkipped += framesSkippedPerStatCycle;
            // resetting the counters after a status record (1 sec)
            framesSkippedPerStatCycle = 0;
            statusIntervalTimer = 0;
            frameCountPerStatCycle = 0;
            statusIntervalTimer = System.currentTimeMillis();
            lastStatusStore = statusIntervalTimer;
            stage.setAvgFps(averageFps);
        }
    }

    private void initTimingElements() {
        // initialise timing elements
        fpsStore = new double[FPS_HISTORY_NR];
        for (int i = 0; i < FPS_HISTORY_NR; i++) {
            fpsStore[i] = 0.0;
        }
        //Log.d(TAG + ".initTimingElements()", "Timing elements for stats initialised");
    }

}
