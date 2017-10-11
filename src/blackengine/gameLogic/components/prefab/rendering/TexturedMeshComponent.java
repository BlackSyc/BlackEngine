///*
// * The MIT License
// *
// * Copyright 2017 Blackened.
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//package blackengine.gameLogic.components.prefab.rendering;
//
//import blackengine.gameLogic.components.base.RenderedComponent;
//import blackengine.openGL.texture.Texture;
//import blackengine.openGL.vao.Vao;
//import blackengine.rendering.prefab.texturedRendering.TexturedMeshRenderer;
//
///**
// *
// * @author Blackened
// */
//public class TexturedMeshComponent extends RenderedComponent<TexturedMeshRenderer>{
//    
//    private Vao vao;
//    
//    private Texture texture;
//    
//    public Vao getVao() {
//        return vao;
//    }
//
//    public Texture getTexture() {
//        return texture;
//    }
//
//    public TexturedMeshComponent(TexturedMeshRenderer renderer, Vao vao, Texture texture) {
//        super(renderer);
//        this.vao = vao;
//        this.texture = texture;
//    }
//
//    @Override
//    public void onActivate() {
//        super.getRenderer().addRenderTarget(this);
//    }
//
//    @Override
//    public void onDeactivate() {
//        super.getRenderer().removeRenderTarget(this);
//    }
//
//    @Override
//    public boolean isRendered() {
//        return super.getRenderer().containsRenderTarget(this);
//    }
//    
//    @Override
//    public TexturedMeshComponent clone(){
//        return new TexturedMeshComponent(this.getRenderer(), this.getVao(), this.getTexture());
//    }
//    
//}
