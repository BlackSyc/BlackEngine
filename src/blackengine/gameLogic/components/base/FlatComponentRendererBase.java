/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package blackengine.gameLogic.components.base;

import blackengine.rendering.TargetFlatRenderer;

/**
 *
 * @author Blackened
 * @param <T>
 */
public abstract class FlatComponentRendererBase<T extends TargetFlatRenderer> extends ComponentBase {

    private T renderer;

    public T getRenderer() {
        return renderer;
    }

    public void setRenderer(T renderer) {
        this.renderer = renderer;
    }

    public FlatComponentRendererBase(T renderer) {
        this.renderer = renderer;
    }

    public abstract boolean isRendered();

    @Override
    public void update() {

    }

}
