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
package blackengine.rendering.pipeline;

/**
 *
 * @author Blackened
 */
public class CameraSettings {

    private final float fieldOfView;

    private final float nearClippingPlane;

    private final float farClippingPlane;

    public float getFieldOfView() {
        return fieldOfView;
    }

    public float getNearClippingPlane() {
        return nearClippingPlane;
    }

    public float getFarClippingPlane() {
        return farClippingPlane;
    }

    public CameraSettings(float fieldOfView, float nearClippingPlane, float farClippingPlane) {
        this.fieldOfView = fieldOfView;
        this.nearClippingPlane = nearClippingPlane;
        this.farClippingPlane = farClippingPlane;
    }
    
    public static CameraSettings createDefault(){
        return new CameraSettings(70, 0.1f, 500);
    }

}
