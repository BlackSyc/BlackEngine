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
import blackengine.gameLogic.Entity;
import blackengine.gameLogic.Game;
import blackengine.gameLogic.Scene;
import blackengine.gameLogic.components.prefab.SimpleMeshRenderComponent;
import blackengine.openGL.texture.Texture;
import blackengine.openGL.texture.TextureLoader;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.VaoLoader;
import blackengine.rendering.DisplayManager;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.prefab.SimpleMeshComponentRenderer;
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

    private Game game;

    @Override
    public void setUp() {
        super.setDisplayManager(new DisplayManager(60));
        super.getDisplayManager().createDisplay(800, 600, "testDisplay", false);
        this.createTestRenderer();

        Entity testEntity = null;
        try {
            testEntity = this.createTestEntity();
        } catch (IOException ex) {
            Logger.getLogger(TestAppManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        this.game = new Game();
        this.game.setActiveScene(new Scene("testScene"));
        this.game.getActiveScene().addEntity(testEntity);

    }

    private void createTestRenderer() {
        SimpleMeshComponentRenderer testRenderer = new SimpleMeshComponentRenderer();
        super.getDisplayManager().getMasterRenderer().addPOVRenderer(testRenderer);
        RenderEngine.registerPOVRenderer(SimpleMeshComponentRenderer.class, 1f);
    }

    private Entity createTestEntity() throws IOException {
        Entity testEntity = new Entity("testEntity", new Vector3f(), new Vector3f());
        
        MeshDataObject meshData = MeshLoader.getInstance().loadFromFile("/testRes/", "cube.obj");
        ImageDataObject imageData = ImageLoader.getInstance().loadFromFile("/testRes/", "testTexture.png");

        Vao meshVao = VaoLoader.loadVAO(meshData);
        Texture imageTexture = TextureLoader.createTexture(imageData);

        SimpleMeshRenderComponent meshComponent = new SimpleMeshRenderComponent(meshVao, imageTexture, super.getDisplayManager().getMasterRenderer().getPOVRenderer(SimpleMeshComponentRenderer.class));
        testEntity.addComponent(meshComponent);

        return testEntity;
    }

    @Override
    public void cleanUp() {
        super.getDisplayManager().getMasterRenderer().destroy();
        super.getDisplayManager().destroyDisplay();

    }

}
