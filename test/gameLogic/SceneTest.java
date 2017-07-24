/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameLogic;

import blackengine.gameLogic.Scene;
import blackengine.gameLogic.Entity;
import blackengine.gameLogic.components.prefab.HealthComponent;
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
public class SceneTest {

    Scene testScene;

    public SceneTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        this.testScene = new Scene("testScene");
    }

    @After
    public void tearDown() {
        this.testScene.destroy();
        this.testScene = null;
    }

    @Test
    public void testAddEntity() {
        assertFalse(this.testScene.containsEntity("testEntity"));
        assertNull(this.testScene.getEntity("testEntity"));

        Entity testEntity = new Entity("testEntity", new Vector3f(), new Vector3f());
        this.testScene.addEntity(testEntity);

        assertTrue(this.testScene.containsEntity("testEntity"));
        assertEquals(testEntity, this.testScene.getEntity("testEntity"));
    }

    @Test
    public void testDetachEntity() {
        assertFalse(this.testScene.containsEntity("testEntity"));
        assertNull(this.testScene.getEntity("testEntity"));

        Entity testEntity = new Entity("testEntity", new Vector3f(), new Vector3f());
        this.testScene.addEntity(testEntity);

        assertTrue(this.testScene.containsEntity("testEntity"));
        assertEquals(testEntity, this.testScene.getEntity("testEntity"));

        Entity detachedEntity = this.testScene.detachEntity("testEntity");

        assertEquals(testEntity, detachedEntity);

        assertFalse(this.testScene.containsEntity("testEntity"));
        assertNull(this.testScene.getEntity("testEntity"));
    }

    @Test
    public void testDestroyEntity() {
        assertNull(this.testScene.getEntity("testEntity"));

        Entity testEntity = new Entity("testEntity", new Vector3f(), new Vector3f());

        this.testScene.addEntity(testEntity);

        assertTrue(this.testScene.containsEntity("testEntity"));
        assertNotNull(this.testScene.getEntity("testEntity"));
        assertEquals(testEntity, this.testScene.getEntity("testEntity"));

        // Indirect destruction
        this.testScene.getEntity("testEntity").destroy();

        assertTrue(this.testScene.containsEntity("testEntity"));
        assertNotNull(this.testScene.getEntity("testEntity"));

        this.testScene.update();

        assertFalse(this.testScene.containsEntity("testEntity"));
        assertNull(this.testScene.getEntity("testEntity"));

        // Direct destruction
        this.testScene.addEntity(new Entity("testEntity", new Vector3f(), new Vector3f()));

        assertTrue(this.testScene.containsEntity("testEntity"));
        assertNotNull(this.testScene.getEntity("testEntity"));

        this.testScene.destroyEntity("testEntity");

        assertFalse(this.testScene.containsEntity("testEntity"));
        assertNull(this.testScene.getEntity("testEntity"));
    }

    @Test
    public void testDestroyScene() {
        this.testScene.addEntity(new Entity("testEntity", new Vector3f(), new Vector3f()));

        assertTrue(this.testScene.containsEntity("testEntity"));
        assertNotNull(this.testScene.getEntity("testEntity"));

        this.testScene.update();

        assertNotNull(this.testScene.getEntity("testEntity"));

        this.testScene.destroy();

        assertFalse(this.testScene.containsEntity("testEntity"));
        assertNull(this.testScene.getEntity("testEntity"));
        assertTrue(this.testScene.isDestroyed());
    }
}
