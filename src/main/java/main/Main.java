package main;

import engine.io.Window;
import engine.rendering.Renderer;
import engine.rendering.models.TexturedModel;
import engine.shaders.BasicShader;

public class Main {
    public static final int WIDTH = 512;
    public static final int HEIGHT = 512;
    public static final int FPS = 60;
    public static Renderer renderer = new Renderer();
    public static BasicShader shader = new BasicShader();

    public static void main(String[] args) {
        Window window = new Window(WIDTH, HEIGHT, FPS, "LWJGL Tutorial");
        window.create();
        window.setBackgroundColor(0f, 0f, 0f);
        shader.create();

        TexturedModel model = new TexturedModel(new float[] { // vertices
                -0.5f, 0.5f, 0.0f, // top left V0
                0.5f, 0.5f, 0.0f, // top right V1
                0.5f, -0.5f, 0.0f, // botom right V2
                -0.5f, -0.5f, 0.0f // bottom left V3
        }, new float[] { // textureCoords
                0, 0, // top left
                1, 0, // bottom left
                1, 1, // bottom right
                0, 1 // top right
        }, new int[] { // indices
                0, 1, 2, // triangle 1
                2, 3, 0 // triangle 2
        }, "pms_icon_256x256.png");

        while (!window.closed()) {
            if (window.isUpdating()) {
                window.update();
                shader.bind();
                renderer.renderTexturedModel(model);
                window.swapBuffers();
            }
        }

        model.remove();
        shader.remove();
        window.stop();
    }
}
