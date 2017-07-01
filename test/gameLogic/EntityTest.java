package gameLogic;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import gameLogic.components.prefab.HealthComponent;
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
        this.testEntity = new Entity("testEntity", new Vector3f(1,1,1), new Vector3f(0,0,0));
    }
    
    @After
    public void tearDown() {
        this.testEntity.destroy();
        this.testEntity = null;
    }
    
    @Test
    public void testAddComponent(){
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
        
        HealthComponent testComponent = new HealthComponent();
        this.testEntity.addComponent(testComponent);
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        
        HealthComponent retrievedComponent = this.testEntity.getComponent(HealthComponent.class);
        
        assertNotNull(retrievedComponent);
        
        assertEquals(testComponent, retrievedComponent);
        
    }
    
    @Test
    public void testRemoveComponent(){
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
        
        HealthComponent testComponent = new HealthComponent();
        this.testEntity.addComponent(testComponent);
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        
        this.testEntity.removeComponent(HealthComponent.class);
        
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
        
        assertNull(this.testEntity.getComponent(HealthComponent.class));
    }
    
    @Test
    public void testAddChild(){
        assertNull(this.testEntity.getChild("testChild"));
        
        Vector3f position = new Vector3f(1,1,1);
        Entity testChild = new Entity("testChild", position, new Vector3f());
        
        assertEquals(position, testChild.getAbsolutePosition());
        assertEquals(position, testChild.getRelativePosition());
        assertNull(testChild.getParent());
        
        this.testEntity.addChild(testChild);
        
        assertNotNull(testChild.getParent());
        assertEquals(testChild.getParent(), this.testEntity);
        assertEquals(position, testChild.getRelativePosition());
        assertNotEquals(position, testChild.getAbsolutePosition());
        assertEquals(new Vector3f(2,2,2), testChild.getAbsolutePosition());
        
        Entity retrievedChild = this.testEntity.getChild("testChild");
        
        assertEquals(testChild, retrievedChild);
    }
    
    @Test
    public void testRemoveChild(){
        assertNull(this.testEntity.getChild("testChild"));
        
        Vector3f position = new Vector3f(1,1,1);
        Entity testChild = new Entity("testChild", position, new Vector3f());
        
        this.testEntity.addChild(testChild);
        
        assertNotNull(this.testEntity.getChild("testChild"));
        assertEquals(position, testChild.getRelativePosition());
        assertNotEquals(position, testChild.getAbsolutePosition());
        assertEquals(new Vector3f(2,2,2), testChild.getAbsolutePosition());
        
        Entity removedChild = this.testEntity.removeChild("testChild");
        
        assertEquals(testChild, removedChild);
        assertEquals(position, testChild.getRelativePosition());
        assertEquals(position, testChild.getAbsolutePosition());
        
        assertNull(this.testEntity.getChild("testChild"));
    }
    
    @Test
    public void testChildDestruction(){
        assertNull(this.testEntity.getChild("testChild"));
        
        Entity testChild = new Entity("testChild", new Vector3f(), new Vector3f());
        
        this.testEntity.addChild(testChild);
        
        assertNotNull(this.testEntity.getChild("testChild"));
        
        // Indirect destruction
        this.testEntity.getChild("testChild").destroy();
        
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.update();
        
        assertNull(this.testEntity.getChild("testChild"));
        
        // Direct destruction
        this.testEntity.addChild(new Entity("testChild", new Vector3f(), new Vector3f()));
        
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.destroyChild("testChild");
        
        assertNull(this.testEntity.getChild("testChild"));
    }
    
    @Test
    public void testComponentDestruction(){
        this.testEntity.update();
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
        
        HealthComponent testComponent = new HealthComponent();
        this.testEntity.addComponent(testComponent);
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        
        // Indirect destruction
        this.testEntity.getComponent(HealthComponent.class).destroy();
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        
        this.testEntity.update();
        
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
        
        // Direct destruction
        this.testEntity.addComponent(new HealthComponent());
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        
        this.testEntity.destroyComponent(HealthComponent.class);
        
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
    }
    
    @Test
    public void testEntityDestruction(){
        this.testEntity.addChild(new Entity("testChild", new Vector3f(), new Vector3f()));
        this.testEntity.addComponent(new HealthComponent());
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.update();
        
        assertTrue(this.testEntity.containsComponent(HealthComponent.class));
        assertNotNull(this.testEntity.getChild("testChild"));
        
        this.testEntity.destroy();
        
        assertFalse(this.testEntity.containsComponent(HealthComponent.class));
        assertNull(this.testEntity.getChild("testChild"));
        assertTrue(this.testEntity.isDestroyed());
    }
}
