package engine.shaders;

public class BasicShader extends Shader {

    private static final String VERTEX_FILE = "./src/main/java/engine/shaders/basicVertexShader.glsl",
            FRAGMENT_FILE = "./src/main/java/engine/shaders/basicFragmentShader.glsl";

    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);

    }

    @Override
    public void bindAttributes() {
        super.bindAttribute(0, "position");
        super.bindAttribute(1, "textCoords");
    }

}
