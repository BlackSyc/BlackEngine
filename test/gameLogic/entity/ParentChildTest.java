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
import java.util.Optional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Blackened
 */
public class ParentChildTest {
    
    public ParentChildTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void hello() {
         Entity truus = Entity.create("truus", new ImmutableVector3());
         Entity truusKind = Entity.create("truusKind", new ImmutableVector3());
         Entity truusKleinKind = Entity.create("OokJan", new ImmutableVector3());
         Entity truusSuperKleinKind = Entity.create("OokJan", new ImmutableVector3());
         
         truusKleinKind.addChild(truusSuperKleinKind);
         truusKind.addChild(truusKleinKind);
         truus.addChild(truusKind);
         
         long resultCount = truus.findAll("OokJan").count();
         assertEquals(resultCount, 2);
         
         Optional<Entity> result = truus.getEntity("truusKind/OokJan/OokJan");
         
         assertEquals(result.get(), truusSuperKleinKind);
         
     
     
     }
}
