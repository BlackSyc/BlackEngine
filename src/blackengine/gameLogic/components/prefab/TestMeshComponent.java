/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.prefab;

import blackengine.gameLogic.components.base.RenderComponentBase;
import blackengine.openGL.texture.Texture;
import blackengine.openGL.vao.Vao;
import blackengine.rendering.prefab.TestMeshComponentRenderer;

/**
 *
 * @author Blackened
 */
public class TestMeshComponent extends RenderComponentBase<TestMeshComponentRenderer>{
    
    private Vao vao;
    

    public Vao getVao() {
        return vao;
    }
    
    public TestMeshComponent(Vao vao, TestMeshComponentRenderer renderer) {
        super(renderer);
        
        this.vao = vao;
        
        this.enableRendering();
    }
        

    @Override
    public void enableRendering() {
        super.getRenderer().addRenderTarget(this);
    }

    @Override
    public void disableRendering() {
        super.getRenderer().removeRenderTarget(this);
    }

    @Override
    public boolean isRendered() {
        if(super.getRenderer() != null){
            super.getRenderer().containsRenderTarget(this);
        }
        return false;
    }

    
}
