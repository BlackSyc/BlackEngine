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
import blackengine.gameLogic.components.prefab.MouseListenerComponent;
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
import blackengine.userInput.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.function.BooleanSupplier;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.input.Keyboard;
import static org.lwjgl.input.Keyboard.*;
import org.lwjgl.util.vector.Vector3f;

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

        CameraComponent cameraComponent = new CameraComponent();
        cameraComponent.activate();
        testPlayer.addComponent(cameraComponent);

        MovementComponent movementComponent = new MovementComponent(30f, 2f, 2f);
        testPlayer.addComponent(movementComponent);

        ActionListenerComponent<InputAction> actionComponent = new ActionListenerComponent<>(InputEngine.getInstance().getActionObservable(),
                x -> true,
                (x, y) -> {
                    if (y.containsComponent(MovementComponent.class)) {
                        MovementComponent movComp = y.getComponent(MovementComponent.class);
                        switch (x) {
                            case MOVE_BACKWARD:
                                movComp.move(MoveDirection.BACKWARD);
                                return;
                            case MOVE_FORWARD:
                                movComp.move(MoveDirection.FORWARD);
                                return;
                            case TURN_LEFT:
                                movComp.move(MoveDirection.LEFT);
                                return;
                            case TURN_RIGHT:
                                movComp.move(MoveDirection.RIGHT);
                        };
                    }
                });

        testPlayer.addComponent(actionComponent);

        MouseListenerComponent mlc = new MouseListenerComponent(InputEngine.getInstance().getMouseObservable(), x -> x == MouseEvent.DRAG_RMB, (x, y) -> {
            if (y.containsComponent(MovementComponent.class)) {
                y.getComponent(MovementComponent.class).turnAbsolute(new Vector3f(0, 0.5f * (float) Math.toRadians(-x.getDx()),0));
            }
            if(y.containsComponent(CameraComponent.class)){
                double originalPitch = y.getComponent(CameraComponent.class).getPitch();
                y.getComponent(CameraComponent.class).setPitch(originalPitch + 0.5 * Math.toRadians(-x.getDy()));
            }
        });

        testPlayer.addComponent(mlc);

        return testPlayer;
    }

    private Entity createTestEntity() throws IOException {
        Entity testEntity = new Entity("testEntity", new Vector3f(5, 0, -12), new Vector3f());

        MeshDataObject meshData = MeshLoader.getInstance().loadFromFile("/testRes/cube.obj");

        Vao meshVao = VaoLoader.loadVAO(meshData);

        TestMeshComponent meshComponent = new TestMeshComponent(meshVao, RenderEngine.getInstance().getMasterRenderer().getPOVRenderer(TestMeshComponentRenderer.class));
        testEntity.addComponent(meshComponent);

        return testEntity;
    }

    private KeyActionMapper<InputAction> createKeyActionMapper() {
        HashMap<BooleanSupplier, InputAction> inputMap = new HashMap<>();
        BooleanSupplier bs = () -> {
            return false; //To change body of generated lambdas, choose Tools | Templates.
        };
        inputMap.put(() -> Keyboard.isKeyDown(KEY_SPACE), JUMP);
        inputMap.put(() -> Keyboard.isKeyDown(KEY_W), MOVE_FORWARD);
        inputMap.put(() -> Keyboard.isKeyDown(KEY_W) && Keyboard.isKeyDown(KEY_LSHIFT), MOVE_FORWARD);
        inputMap.put(() -> Keyboard.isKeyDown(KEY_S), MOVE_BACKWARD);
        inputMap.put(() -> Keyboard.isKeyDown(KEY_A), TURN_LEFT);
        inputMap.put(() -> Keyboard.isKeyDown(KEY_D), TURN_RIGHT);
        KeyActionMapper<InputAction> keyActionMapper = new KeyActionMapper<>(inputMap);
        return keyActionMapper;
    }

    @Override
    public void cleanUp() {
        
        super.getGameManager().destroyGameElements();
        super.getGameManager().destroyEngine();
        super.getInputManager().destroySubjects();
        super.getInputManager().destroyEngine();
        

        super.getDisplayManager().destroyEngine();
        super.getDisplayManager().destroyDisplay();

    }

}
