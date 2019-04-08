package main;

import engine.io.Window;
import engine.render.Model;
import engine.render.Renderer;
import engine.shaders.BasicShader;

public class Main {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    public static Renderer renderer = new Renderer();
    public static BasicShader shader = new BasicShader();

    public static void main(String[] args) {
        Window window = new Window(WIDTH, HEIGHT, FPS, "LWJGL Tutorial");
        window.create();
        window.setBackgroundColor(0f, 0f, 0f);

        shader.create();

        Model model = new Model(new float[] { // vertices
                -0.5f, 0.5f, 0.0f, // top left 0
                0.5f, 0.5f, 0.0f, // top right 1
                -0.5f, -0.5f, 0.0f, // bottom left 2
                0.5f, -0.5f, 0.0f // botom right 3
        }, new int[] { // indices
                0, 1, 2, // first triangle
                2, 3, 1 // second triangle
        });
        model.create();

        while (!window.closed()) {
            if (window.isUpdating()) {
                window.update();
                shader.bind();
                renderer.renderModel(model);
                window.swapBuffers();
            }
        }

        shader.remove();
        window.stop();
        model.remove();
    }
}
