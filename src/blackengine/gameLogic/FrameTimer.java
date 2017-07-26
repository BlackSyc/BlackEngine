/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic;

import org.lwjgl.Sys;

/**
 *
 * @author Blackened
 */
public class FrameTimer {

    private long lastFrameTime;

    /**
     * Delta in seconds.
     */
    private float delta;

    private int fps;

    private int frameCount;

    private int fpsResolution;

    private long watchPointStart;

    public long getLastFrameTime() {
        return lastFrameTime;
    }

    public float getDelta() {
        return delta;
    }

    public float getFps() {
        return fps;
    }

    public FrameTimer() {
        this.lastFrameTime = this.getCurrentTime();
        this.watchPointStart = this.getCurrentTime();
        this.delta = 0;
        this.fps = 0;
        this.fpsResolution = 100;
    }

    public void registerFrame() {
        long currentFrameTime = this.getCurrentTime();
        this.delta = (currentFrameTime - this.lastFrameTime) / 1000f;
        this.lastFrameTime = currentFrameTime;

        this.calculateFps();
    }

    /**
     * Gets the current time in milliseconds.
     *
     * @return The current time in milliseconds.
     */
    public final long getCurrentTime() {
        return Sys.getTime() * 1000 / Sys.getTimerResolution();
    }

    private void calculateFps() {
        this.frameCount++;

        if (this.frameCount >= this.fpsResolution) {
            long watchDelta = this.getCurrentTime() - this.watchPointStart;
            float watchDeltaSeconds = watchDelta / 1000f;
            this.fps = (int) (this.frameCount / watchDeltaSeconds);

            this.watchPointStart = this.lastFrameTime;
            this.frameCount = 0;
        }
    }
}
