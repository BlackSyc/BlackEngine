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
import blackengine.toolbox.math.ImmutableVector3;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

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
        Entity parent = new Entity("parent", new ImmutableVector3(), new ImmutableVector3(), new ImmutableVector3(2,2,4));
        Entity child = new Entity("child", new ImmutableVector3(), new ImmutableVector3(), new ImmutableVector3(0.5f,0.5f,0.5f));
        
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getRelativeScale(), 0.0001f));
        
        assertTrue(new ImmutableVector3(0.5f,0.5f,0.5f).equals(child.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(0.5f,0.5f,0.5f).equals(child.getTransform().getRelativeScale(), 0.0001f));
        
        
        // After adding the child to the parent, the relative and absolute of the parent remains unchanged,
        // but the absolute of the child should be with respect to the parent.
        parent.addChild(child);
        
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getRelativeScale(), 0.0001f));
        
        assertTrue(new ImmutableVector3(1f,1f,2f).equals(child.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(0.5f,0.5f,0.5f).equals(child.getTransform().getRelativeScale(), 0.0001f));
        
        // When changing the scale of the parent, both (the relative and absolute) scales should stay the same
        // as the parent itself does not have a parent.
        parent.getTransform().setAbsoluteScale(new ImmutableVector3(3,3,6));
        
        assertTrue(new ImmutableVector3(3,3,6).equals(parent.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(3,3,6).equals(parent.getTransform().getRelativeScale(), 0.0001f));
        
        // The childs absolute scale however, should have changed. The relative scale should remain the same.
        assertTrue(new ImmutableVector3(1.5f,1.5f,3f).equals(child.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(0.5f,0.5f,0.5f).equals(child.getTransform().getRelativeScale(), 0.0001f));
        
        // The same should happen when we change the relative scale of the parent back.
        parent.getTransform().setRelativeScale(new ImmutableVector3(2,2,4));
        
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getRelativeScale(), 0.0001f));
        assertTrue(new ImmutableVector3(1f,1f,2f).equals(child.getTransform().getAbsoluteScale(), 0.0001f));
        assertTrue(new ImmutableVector3(0.5f,0.5f,0.5f).equals(child.getTransform().getRelativeScale(), 0.0001f));
        
        // If we now change the relative scale of the child, its absolute scale should change as well, and the 
        // parents scales should remain unchanged.
        child.getTransform().setRelativeScale(new ImmutableVector3(1,1,1));
        
        assertTrue(new ImmutableVector3(1,1,1).equals(child.getTransform().getRelativeScale(), 0.0001f));
        assertTrue(new ImmutableVector3(2,2,4).equals(child.getTransform().getAbsoluteScale(), 0.0001f));
        
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getRelativeScale(), 0.0001f));
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getAbsoluteScale(), 0.0001f));
        
        // If we change the absolute scale of the child, its relative scale should change as well, and the 
        // parents scales should remain unchanged.
        child.getTransform().setAbsoluteScale(new ImmutableVector3(1,1,2));
        
        assertTrue(new ImmutableVector3(0.5f,0.5f,0.5f).equals(child.getTransform().getRelativeScale(), 0.0001f));
        assertTrue(new ImmutableVector3(1,1,2).equals(child.getTransform().getAbsoluteScale(), 0.0001f));
        
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getRelativeScale(), 0.0001f));
        assertTrue(new ImmutableVector3(2,2,4).equals(parent.getTransform().getAbsoluteScale(), 0.0001f));
        
    }
    
    @Test
    public void testChildPositioning(){
        Entity parent = new Entity("parent", new ImmutableVector3(), new ImmutableVector3(), new ImmutableVector3(1,1,1));
        Entity child = new Entity("child", new ImmutableVector3(1,0,0), new ImmutableVector3(), new ImmutableVector3(1,1,1));
        
        parent.addChild(child);
        
        parent.getTransform().setAbsoluteEulerRotation(new ImmutableVector3(0,90,0));
        
        assertTrue(areEqual(new ImmutableVector3(0,0,-1), child.getTransform().getAbsolutePosition()));
        
        parent.getTransform().setAbsoluteEulerRotation(new ImmutableVector3(0,0,0));
        
        assertTrue(areEqual(new ImmutableVector3(1,0,0), child.getTransform().getAbsolutePosition()));
    }
    
    @Test
    public void testRicksDing(){
        Entity parent = Entity.create("parent", new ImmutableVector3(1,1,1));
        assertTrue(parent.getTransform().getAbsolutePosition().equals(new ImmutableVector3(1,1,1), 0.001f));
        
        Entity child = Entity.create("child", new ImmutableVector3(0,-5,0));
        assertTrue(child.getTransform().getAbsolutePosition().equals(new ImmutableVector3(0,-5,0), 0.001f));
        
        parent.addChild(child);
        assertTrue(parent.getTransform().getAbsolutePosition().equals(new ImmutableVector3(1,1,1), 0.001f));
        assertTrue(child.getTransform().getAbsolutePosition().equals(new ImmutableVector3(1,-4,1), 0.001f));
        
        
    }
    
    private boolean areEqual(ImmutableVector3 left, ImmutableVector3 right){
        return Math.abs(left.getX() - right.getX()) < 0.001f &&
                Math.abs(left.getX() - right.getX()) < 0.001f &&
                Math.abs(left.getX() - right.getX()) < 0.001f;
    }

}
