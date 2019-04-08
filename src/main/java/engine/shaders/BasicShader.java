package engine.shaders;

public class BasicShader extends Shader {

    private static final String VERTEX_FILE = "./src/main/java/engine/shaders/basicVertexShader.vs",
            FRAGMENT_FILE = "./src/main/java/engine/shaders/basicFragmentShader.fs";

    public BasicShader() {
        super(VERTEX_FILE, FRAGMENT_FILE);

    }

    @Override
    public void bindAllAttributes() {
        super.bindAttribute(0, "vertices");
    }

}
