/* 
 * The MIT License
 *
 * Copyright 2017 Blackened.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
