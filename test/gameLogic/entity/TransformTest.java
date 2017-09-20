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
package gameLogic.entity;

import blackengine.gameLogic.Entity;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class TransformTest {
    
    public TransformTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testChildScaling(){
        // For both entities, the relative and absolute scale should be the same when they do not have a parent.
        Entity parent = new Entity("parent", new Vector3f(), new Vector3f(), new Vector3f(2,2,4));
        Entity child = new Entity("child", new Vector3f(), new Vector3f(), new Vector3f(0.5f,0.5f,0.5f));
        
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getRelativeScale());
        
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), child.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), child.getTransform().getRelativeScale());
        
        
        // After adding the child to the parent, the relative and absolute of the parent remains unchanged,
        // but the absolute of the child should be with respect to the parent.
        parent.addChild(child);
        
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getRelativeScale());
        
        assertEquals(new Vector3f(1f,1f,2f), child.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), child.getTransform().getRelativeScale());
        
        // When changing the scale of the parent, both (the relative and absolute) scales should stay the same
        // as the parent itself does not have a parent.
        parent.getTransform().setAbsoluteScale(new Vector3f(3,3,6));
        
        assertEquals(new Vector3f(3,3,6), parent.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(3,3,6), parent.getTransform().getRelativeScale());
        
        // The childs absolute scale however, should have changed. The relative scale should remain the same.
        assertEquals(new Vector3f(1.5f,1.5f,3f), child.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), child.getTransform().getRelativeScale());
        
        // The same should happen when we change the relative scale of the parent back.
        parent.getTransform().setRelativeScale(new Vector3f(2,2,4));
        
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getRelativeScale());
        assertEquals(new Vector3f(1f,1f,2f), child.getTransform().getAbsoluteScale());
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), child.getTransform().getRelativeScale());
        
        // If we now change the relative scale of the child, its absolute scale should change as well, and the 
        // parents scales should remain unchanged.
        child.getTransform().setRelativeScale(new Vector3f(1,1,1));
        
        assertEquals(new Vector3f(1,1,1), child.getTransform().getRelativeScale());
        assertEquals(new Vector3f(2,2,4), child.getTransform().getAbsoluteScale());
        
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getRelativeScale());
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getAbsoluteScale());
        
        // If we change the absolute scale of the child, its relative scale should change as well, and the 
        // parents scales should remain unchanged.
        child.getTransform().setAbsoluteScale(new Vector3f(1,1,2));
        
        assertEquals(new Vector3f(0.5f,0.5f,0.5f), child.getTransform().getRelativeScale());
        assertEquals(new Vector3f(1,1,2), child.getTransform().getAbsoluteScale());
        
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getRelativeScale());
        assertEquals(new Vector3f(2,2,4), parent.getTransform().getAbsoluteScale());
        
    }
    
    @Test
    public void testChildPositioning(){
        Entity parent = new Entity("parent", new Vector3f(), new Vector3f(), new Vector3f(1,1,1));
        Entity child = new Entity("child", new Vector3f(1,0,0), new Vector3f(), new Vector3f(1,1,1));
        
        parent.addChild(child);
        
        parent.getTransform().setAbsoluteEulerRotation(new Vector3f(0,90,0));
        
        assertTrue(areEqual(new Vector3f(0,0,-1), child.getTransform().getAbsolutePosition()));
        
        parent.getTransform().setAbsoluteEulerRotation(new Vector3f(0,0,0));
        
        assertTrue(areEqual(new Vector3f(1,0,0), child.getTransform().getAbsolutePosition()));
    }
    
    private boolean areEqual(Vector3f left, Vector3f right){
        return Math.abs(left.x - right.x) < 0.001f &&
                Math.abs(left.y - right.y) < 0.001f &&
                Math.abs(left.z - right.z) < 0.001f;
    }

}
