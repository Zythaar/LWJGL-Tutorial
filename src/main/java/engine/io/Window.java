package engine.io;

import java.nio.DoubleBuffer;

import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private int width, height;
    private String title;
    private double fps_cap, time, processedTime = 0;
    private long window;
    private Vector3f backgroundColor;
    private boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    public Window(int width, int height, int fps, String title) {
        super();
        this.width = width;
        this.height = height;
        this.title = title;
        this.fps_cap = fps;
        setBackgroundColor(0f, 0f, 0f);
    }

    public void create() {
        if (!GLFW.glfwInit()) {
            System.err.println("Error: Couldn't initalize GLFW");
            System.exit(-1);
        }

        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        if (window == 0) {
            System.err.println("Error: Window couldn't be created");
            System.exit(-1);
        }

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

        GLFW.glfwShowWindow(window);

        time = getTime();
    }

    public boolean closed() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void update() {
        for (int i = 0; i < GLFW.GLFW_KEY_LAST; i++) {
            keys[i] = isKeyDown(i);
        }
        for (int i = 0; i < GLFW.GLFW_MOUSE_BUTTON_LAST; i++) {
            mouseButtons[i] = isMouseDown(i);
        }
        GL11.glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 1f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
        GLFW.glfwPollEvents();
    }

    public void stop() {
        GLFW.glfwTerminate();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public double getTime() {
        return (double) System.nanoTime() / 1000000000;
    }

    public boolean isKeyDown(int key) {
        return GLFW.glfwGetKey(window, key) == 1;
    }

    public boolean isMouseDown(int button) {
        return GLFW.glfwGetMouseButton(window, button) == 1;
    }

    public boolean isKeyPressed(int key) {
        return isKeyDown(key) && !keys[key];
    }

    public boolean isKeyReleased(int key) {
        return !isKeyDown(key) && keys[key];
    }

    public boolean isMousePressed(int button) {
        return isMouseDown(button) && !mouseButtons[button];
    }

    public boolean isMouseReleased(int button) {
        return !isMouseDown(button) && mouseButtons[button];
    }

    public double getMouseX() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, buffer, null);
        return buffer.get(0);
    }

    public double getMouseY() {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(1);
        GLFW.glfwGetCursorPos(window, null, buffer);
        return buffer.get(0);
    }

    public boolean isUpdating() {
        double nextTime = getTime();
        double passedTime = nextTime - time;
        processedTime += passedTime;
        time = nextTime;

        while (processedTime > 1.0 / fps_cap) {
            processedTime -= 1.0 / fps_cap;
            return true;
        }
        return false;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public double getFPS() {
        return fps_cap;
    }

    public long getWindow() {
        return window;
    }

    public void setBackgroundColor(float r, float g, float b) {
        backgroundColor = new Vector3f(r, g, b);
    }
}
