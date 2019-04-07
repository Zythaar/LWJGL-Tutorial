package main;

import engine.io.Window;
import engine.render.Model;
import engine.render.Renderer;

public class Main {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final int FPS = 60;
    public static Renderer renderer = new Renderer();

    public static void main(String[] args) {
        Window window = new Window(WIDTH, HEIGHT, FPS, "LWJGL Tutorial");
        window.create();
        window.setBackgroundColor(0f, 1f, 1f);

        Model model = new Model(new float[] { -0.5f, 0.5f, 0f, 0.5f, 0.5f, 0f, 0.5f, -0.5f, 0f,

                -0.5f, -0.5f, 0f, 0.5f, -0.5f, 0f, -0.5f, 0.5f, 0f, });
        model.create();

        while (!window.closed()) {
            if (window.isUpdating()) {
                window.update();

                renderer.renderModel(model);

                window.swapBuffers();
            }
        }
        window.stop();
    }
}
