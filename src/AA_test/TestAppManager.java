/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AA_test;

import blackengine.base.ApplicationManager;
import blackengine.dataAccess.dataObjects.ImageDataObject;
import blackengine.dataAccess.dataObjects.MeshDataObject;
import blackengine.dataAccess.fileLoaders.ImageLoader;
import blackengine.dataAccess.fileLoaders.MeshLoader;
import blackengine.gameLogic.ComponentEngine;
import blackengine.gameLogic.Entity;
import blackengine.gameLogic.Game;
import blackengine.gameLogic.Scene;
import blackengine.gameLogic.components.prefab.CameraComponent;
import blackengine.gameLogic.components.prefab.TestMeshComponent;
import blackengine.openGL.texture.Texture;
import blackengine.openGL.texture.TextureLoader;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.VaoLoader;
import blackengine.rendering.DisplayManager;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.prefab.TestMeshComponentRenderer;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Blackened
 */
public class TestAppManager extends ApplicationManager {

    @Override
    public void setUp() {
        super.setDisplayManager(new DisplayManager(60));
        super.getDisplayManager().createDisplay(800, 600, "testDisplay", false);
        this.createTestRenderer();

        Entity testPlayer = null;
        Entity testEntity = null;
        try {
            testPlayer = this.createTestPlayer();
            testEntity = this.createTestEntity();
            
        } catch (IOException ex) {
            Logger.getLogger(TestAppManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        Game game = new Game();
        game.setActiveScene(new Scene("testScene"));
        game.getActiveScene().addEntity(testPlayer);
        game.getActiveScene().addEntity(testEntity);

        super.setGame(game);

    }

    private void createTestRenderer() {
        TestMeshComponentRenderer testRenderer = new TestMeshComponentRenderer();
        super.getDisplayManager().getMasterRenderer().addPOVRenderer(testRenderer);
        RenderEngine.registerPOVRenderer(TestMeshComponentRenderer.class, 1f);
    }

    private Entity createTestPlayer(){
        ComponentEngine.registerComponent(CameraComponent.class, 1f);
        Entity testPlayer = new Entity("testPlayer", new Vector3f(), new Vector3f());

        CameraComponent cameraComponent = new CameraComponent(super.getDisplayManager().getMasterRenderer());
        cameraComponent.activate();
        testPlayer.addComponent(cameraComponent);

        return testPlayer;
    }

    private Entity createTestEntity() throws IOException {
        Entity testEntity = new Entity("testEntity", new Vector3f(5, -3, -12), new Vector3f());
        
        MeshDataObject meshData = MeshLoader.getInstance().loadFromFile("/testRes/", "cube.obj");

        Vao meshVao = VaoLoader.loadVAO(meshData);

        TestMeshComponent meshComponent = new TestMeshComponent(meshVao, super.getDisplayManager().getMasterRenderer().getPOVRenderer(TestMeshComponentRenderer.class));
        testEntity.addComponent(meshComponent);
        
        return testEntity;
    }

    @Override
    public void cleanUp() {
        super.getDisplayManager().getMasterRenderer().destroy();
        super.getDisplayManager().destroyDisplay();

    }

}
