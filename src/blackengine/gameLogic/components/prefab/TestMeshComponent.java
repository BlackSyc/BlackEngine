/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.components.base.POVComponentRendererBase;
import blackengine.openGL.vao.Vao;
import blackengine.rendering.prefab.TestMeshComponentRenderer;

/**
 *
 * @author Blackened
 */
public class TestMeshComponent extends POVComponentRendererBase<TestMeshComponentRenderer>{
    
    private Vao vao;
    

    public Vao getVao() {
        return vao;
    }
    
    public TestMeshComponent(Vao vao, TestMeshComponentRenderer renderer) {
        super(renderer);
        
        this.vao = vao;
    }
        

    @Override
    public boolean isRendered() {
        if(super.getRenderer() != null){
            super.getRenderer().containsRenderTarget(this);
        }
        return false;
    }

    @Override
    public void activate() {
        super.getRenderer().addRenderTarget(this);
        super.activate();
    }

    @Override
    public void deactivate() {
        super.getRenderer().removeRenderTarget(this);
        super.activate();
    }

    
}
