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
package blackengine.rendering;

import blackengine.openGL.frameBuffer.FrameBufferObject;
import blackengine.openGL.texture.FBOTexture;
import blackengine.rendering.pipeline.Resolution;
import blackengine.toolbox.math.ImmutableVector3;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 *
 * @author Blackened
 */
public class CameraFrameBuffer extends FrameBufferObject {

    private final Subject<FBOTexture> colour = PublishSubject.create();

    private final Subject<FBOTexture> depth = PublishSubject.create();

    public Observable<FBOTexture> getColour() {
        return colour;
    }

    public Observable<FBOTexture> getDepth() {
        return depth;
    }

    public CameraFrameBuffer(Resolution resolution, ImmutableVector3 clearColour) {
        super(resolution, clearColour);
    }

    @Override
    public void stopFrame() {
        super.stopFrame();
        if (this.colour.hasObservers()) {
            this.colour.onNext(super.getColourTexture());
        }
        if(this.depth.hasObservers()){
            this.depth.onNext(super.getDepthTexture());
        }
    }
}
