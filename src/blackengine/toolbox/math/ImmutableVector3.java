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
package blackengine.toolbox.math;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class ImmutableVector3 {

    private final float x;

    private final float y;

    private final float z;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public ImmutableVector3(Vector3f vector) {
        this.x = vector.getX();
        this.y = vector.getY();
        this.z = vector.getZ();
    }

    public ImmutableVector3() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public ImmutableVector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f createMutable() {
        return new Vector3f(this.x, this.y, this.z);
    }

    public ImmutableVector3 add(ImmutableVector3 addition) {
        return new ImmutableVector3(this.x + addition.getX(), 
                this.y + addition.getY(), 
                this.z + addition.getZ());
    }
    
    public ImmutableVector3 add(float x, float y, float z) {
        return new ImmutableVector3(this.x + x, 
                this.y + y, 
                this.z + z);
    }
    
    public ImmutableVector3 subtract(ImmutableVector3 subtraction){
        return new ImmutableVector3(this.x - subtraction.getX(), 
                this.y - subtraction.getY(), 
                this.z - subtraction.getZ());
    }
    
    public ImmutableVector3 multiplyBy(ImmutableVector3 multiplication){
        return new ImmutableVector3(this.x * multiplication.getX(), 
                this.y * multiplication.getY(), 
                this.z * multiplication.getZ());
    }
    
    public ImmutableVector3 multiplyBy(float multiplication){
        return new ImmutableVector3(this.x * multiplication, 
                this.y * multiplication, 
                this.z * multiplication);
    }
    
    public ImmutableVector3 divideBy(ImmutableVector3 division){
        return new ImmutableVector3(this.x / division.getX(), 
                this.y / division.getY(), 
                this.z / division.getZ());
    }
    
    public ImmutableVector3 negate(){
        return new ImmutableVector3(-this.x,-this.y,-this.z);
    }

}
