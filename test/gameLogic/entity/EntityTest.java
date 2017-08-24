package gameLogic.entity;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import blackengine.gameLogic.Entity;
import blackengine.gameLogic.GameManager;
import blackengine.gameLogic.LogicEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class EntityTest {
    
    Entity testEntity;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        GameManager gameManager = new GameManager();
        gameManager.createEngine();
        this.testEntity = new Entity("testEntity", new Vector3f(1,1,1), new Vector3f(), new Vector3f());
    }
    
    @After
    public void tearDown() {
        this.testEntity.destroy();
        this.testEntity = null;
    }
    
        @Test
    public void testAddChild(){
        assertFalse(this.testEntity.containsChild("testChild"));
        assertNull(this.testEntity.getChild("testChild"));
        
        Vector3f position = new Vector3f(1,1,1);
        Entity testChild = new Entity("testChild", position, new Vector3f(), new Vector3f());
        
        assertEquals(position, testChild.getAbsolutePosition());
        assertEquals(position, testChild.getPosition());
        assertNull(testChild.getParent());
        
        this.testEntity.addChild(testChild);
        
        assertNotNull(testChild.getParent());
        assertEquals(testChild.getParent(), this.testEntity);
        assertEquals(position, testChild.getPosition());
        assertNotEquals(position, testChild.getAbsolutePosition());
        assertEquals(new Vector3f(2,2,2), testChild.getAbsolutePosition());
        assertTrue(this.testEntity.containsChild("testChild"));
        
        Entity retrievedChild = this.testEntity.getChild("testChild");
        
        assertEquals(testChild, retrievedChild);
    }
    
    @Test
    public void testDetachChild(){
        assertFalse(this.testEntity.containsChild("testChild"));
        assertNull(this.testEntity.getChild("testChild"));
        
        Vector3f position = new Vector3f(1,1,1);
        Entity testChild = new Entity("testChild", position, new Vector3f(), new Vector3f());
        
        this.testEntity.addChild(testChild);
        
        assertTrue(this.testEntity.containsChild("testChild"));
        assertNotNull(this.testEntity.getChild("testChild"));
        assertEquals(position, testChild.getPosition());
        assertNotEquals(position, testChild.getAbsolutePosition());
        assertEquals(new Vector3f(2,2,2), testChild.getAbsolutePosition());
        
        Entity detachedChild = this.testEntity.detachChild("testChild");
        
        assertEquals(testChild, detachedChild);
        assertEquals(position, testChild.getPosition());
        assertEquals(position, testChild.getAbsolutePosition());
        
        assertFalse(this.testEntity.containsChild("testChild"));
        assertNull(this.testEntity.getChild("testChild"));
    }
    
    @Test
    public void testDestroyChild(){
        assertNull(this.testEntity.getChild("testChild"));
        
        Entity testChild = new Entity("testChild", new Vector3f(), new Vector3f(), new Vector3f());
        
        this.testEntity.addChild(testChild);
        
        assertTrue(this.testEntity.containsChild("testChild"));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        // Indirect destruction
        this.testEntity.getChild("testChild").destroy();
        
        assertTrue(this.testEntity.containsChild("testChild"));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.update();
        
        assertFalse(this.testEntity.containsChild("testChild"));
        assertNull(this.testEntity.getChild("testChild"));
        
        // Direct destruction
        this.testEntity.addChild(new Entity("testChild", new Vector3f(), new Vector3f(), new Vector3f()));
        
        assertTrue(this.testEntity.containsChild("testChild"));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.destroyChild("testChild");
        
        assertFalse(this.testEntity.containsChild("testChild"));
        assertNull(this.testEntity.getChild("testChild"));
    }
    
    @Test
    public void testAddComponent(){
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
        
        TestComponentImpl testComponent = new TestComponentImpl();
        assertNull(testComponent.getParent());
        
        this.testEntity.addComponent(testComponent);
        
        assertNotNull(testComponent.getParent());
        assertEquals(this.testEntity, testComponent.getParent());
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        
        TestComponentImpl retrievedComponent = this.testEntity.getComponent(TestComponentImpl.class);
        
        assertNotNull(retrievedComponent);
        
        assertEquals(testComponent, retrievedComponent);
        
    }
    
    @Test
    public void testDetachComponent(){
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
        
        TestComponentImpl testComponent = new TestComponentImpl();
        assertNull(testComponent.getParent());
        
        this.testEntity.addComponent(testComponent);
        
        assertNotNull(testComponent.getParent());
        assertEquals(this.testEntity, testComponent.getParent());
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        
        TestComponentImpl detachedComponent = this.testEntity.detachComponent(TestComponentImpl.class);
        
        assertNull(testComponent.getParent());
        assertEquals(testComponent, detachedComponent);
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
        
        assertNull(this.testEntity.getComponent(TestComponentImpl.class));
    }
    
    @Test
    public void testDestroyComponent(){
        LogicEngine.getInstance().registerComponent(TestComponentImpl.class, 1f);
        
        this.testEntity.update();
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
        
        TestComponentImpl testComponent = new TestComponentImpl();
        this.testEntity.addComponent(testComponent);
        
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        
        // Indirect destruction
        this.testEntity.getComponent(TestComponentImpl.class).destroy();
        
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        
        this.testEntity.update();
        
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
        
        // Direct destruction
        this.testEntity.addComponent(new TestComponentImpl());
        
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        
        this.testEntity.destroyComponent(TestComponentImpl.class);
        
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
    }
    
    @Test
    public void testDestroyEntity(){
        this.testEntity.addChild(new Entity("testChild", new Vector3f(), new Vector3f(), new Vector3f()));
        this.testEntity.addComponent(new TestComponentImpl());
        
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.update();
        
        assertTrue(this.testEntity.containsComponent(TestComponentImpl.class));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.destroy();
        
        assertFalse(this.testEntity.containsComponent(TestComponentImpl.class));
        assertFalse(this.testEntity.containsChild("testChild"));
        assertNull(this.testEntity.getChild("testChild"));
        assertTrue(this.testEntity.isDestroyed());
    }
}
