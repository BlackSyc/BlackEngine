/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AA_test;

import blackengine.gameLogic.components.prefab.ActionListenerComponent;
import static AA_test.InputAction.*;
import blackengine.base.ApplicationManager;
import blackengine.dataAccess.dataObjects.MeshDataObject;
import blackengine.dataAccess.fileLoaders.MeshLoader;
import blackengine.gameLogic.LogicEngine;
import blackengine.gameLogic.Entity;
import blackengine.gameLogic.GameManager;
import blackengine.gameLogic.Scene;
import blackengine.gameLogic.components.prefab.CameraComponent;
import blackengine.gameLogic.components.prefab.MovementComponent;
import blackengine.gameLogic.components.prefab.TestMeshComponent;
import blackengine.gameLogic.movement.MoveDirection;
import blackengine.gameLogic.movement.TurnDirection;
import blackengine.openGL.vao.Vao;
import blackengine.openGL.vao.VaoLoader;
import blackengine.rendering.DisplayManager;
import blackengine.rendering.RenderEngine;
import blackengine.rendering.prefab.TestMeshComponentRenderer;
import blackengine.userInput.InputEngine;
import blackengine.userInput.InputManager;
import blackengine.userInput.KeyActionMapper;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.lwjgl.input.Keyboard.*;
import org.lwjgl.util.vector.Vector3f;
import rx.Observable;

/**
 *
 * @author Blackened
 */
public class TestAppManager extends ApplicationManager {

    @Override
    public void setUp() {
        DisplayManager displayManager = new DisplayManager(120);
        displayManager.createEngine();
        displayManager.createDisplay(1920, 1080, "testDisplay", false);
        super.setDisplayManager(displayManager);

        GameManager gameManager = new GameManager();
        gameManager.createEngine();
        super.setGameManager(gameManager);

        InputManager<InputAction> inputManager = new InputManager<>(this.createKeyActionMapper());
        inputManager.createEngine();
        super.setInputManager(inputManager);
        
        this.createGame();

    }

    private void createGame() {
        this.createTestRenderer();


        Scene testScene = this.createScene();
        super.getGameManager().setActiveScene(testScene);
    }

    private void createTestRenderer() {
        TestMeshComponentRenderer testRenderer = new TestMeshComponentRenderer();
        RenderEngine.getInstance().getMasterRenderer().addPOVRenderer(testRenderer);
        RenderEngine.getInstance().registerPOVRenderer(TestMeshComponentRenderer.class, 1f);
    }

    private Scene createScene() {
        Entity testPlayer = null;
        Entity testEntity = null;
        try {
            testPlayer = this.createTestPlayer();
            testEntity = this.createTestEntity();

        } catch (IOException ex) {
            Logger.getLogger(TestAppManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        Scene testScene = new Scene("testScene");
        testScene.addEntity(testPlayer);
        testScene.addEntity(testEntity);

        return testScene;
    }

    private Entity createTestPlayer() {
        LogicEngine.getInstance().registerComponent(CameraComponent.class, 1f);
        Entity testPlayer = new Entity("testPlayer", new Vector3f(), new Vector3f());

        CameraComponent cameraComponent = new CameraComponent(RenderEngine.getInstance().getMasterRenderer());
        cameraComponent.activate();
        testPlayer.addComponent(cameraComponent);

        MovementComponent movementComponent = new MovementComponent(30f, 2f, 2f, true);
        testPlayer.addComponent(movementComponent);
        

        ActionListenerComponent<InputAction> actionComponent = new ActionListenerComponent<>(InputEngine.getInstance().getActionObservable(),
                x -> true,
                (x, y) -> {
                    if (y.containsComponent(MovementComponent.class)) {
                        MovementComponent movComp = y.getComponent(MovementComponent.class);
                        switch (x) {
                            case MOVE_BACKWARD:
                                movComp.move(MoveDirection.FORWARD);
                                return;
                            case MOVE_FORWARD:
                                movComp.move(MoveDirection.BACKWARD);
                                return;
                            case TURN_LEFT:
                                movComp.turn(TurnDirection.LEFT);
                                return;
                            case TURN_RIGHT:
                                movComp.turn(TurnDirection.RIGHT);
                        };
                    }
                });

        testPlayer.addComponent(actionComponent);
        return testPlayer;
    }

    private Entity createTestEntity() throws IOException {
        Entity testEntity = new Entity("testEntity", new Vector3f(5, 0, -12), new Vector3f());

        MeshDataObject meshData = MeshLoader.getInstance().loadFromFile("/testRes/", "cube.obj");

        Vao meshVao = VaoLoader.loadVAO(meshData);

        TestMeshComponent meshComponent = new TestMeshComponent(meshVao, RenderEngine.getInstance().getMasterRenderer().getPOVRenderer(TestMeshComponentRenderer.class));
        testEntity.addComponent(meshComponent);

        return testEntity;
    }

    private KeyActionMapper<InputAction> createKeyActionMapper() {
        HashMap<Integer, InputAction> inputMap = new HashMap<>();
        inputMap.put(KEY_SPACE, JUMP);
        inputMap.put(KEY_W, MOVE_FORWARD);
        inputMap.put(KEY_S, MOVE_BACKWARD);
        inputMap.put(KEY_A, TURN_LEFT);
        inputMap.put(KEY_D, TURN_RIGHT);
        inputMap.put(KEY_Q, STRAFE_LEFT);
        inputMap.put(KEY_E, STRAFE_RIGHT);
        inputMap.put(KEY_LSHIFT, CAMERA_FOCUS);
        inputMap.put(KEY_1, CAST_SPELL1);
        inputMap.put(KEY_2, CAST_SPELL2);
        inputMap.put(KEY_3, CAST_SPELL3);
        inputMap.put(KEY_ESCAPE, UI_QUIT);
        inputMap.put(KEY_RETURN, UI_RETURN);
        KeyActionMapper<InputAction> keyActionMapper = new KeyActionMapper<>(inputMap);
        return keyActionMapper;
    }

    @Override
    public void cleanUp() {
        super.getGameManager().getActiveScene().destroy();
        RenderEngine.getInstance().getMasterRenderer().destroy();
        super.getDisplayManager().destroyDisplay();

    }

}
