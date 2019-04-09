package engine.shaders;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader {
    private int vertexShaderID, fragmentShaderID, programID;
    private String framentFile, vertexFile;

    public Shader(String vertexFile, String framentFile) {
        this.vertexFile = vertexFile;
        this.framentFile = framentFile;
    }

    public void create() {
        programID = GL20.glCreateProgram();

        // create vertexShader
        vertexShaderID = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShaderID, readFile(vertexFile));
        GL20.glCompileShader(vertexShaderID);

        if (GL20.glGetShaderi(vertexShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: Vertex Shader - " + GL20.glGetShaderInfoLog(vertexShaderID));
        }

        // create fragmentShader
        fragmentShaderID = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShaderID, readFile(framentFile));
        GL20.glCompileShader(fragmentShaderID);

        if (GL20.glGetShaderi(fragmentShaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: Fragment Shader - " + GL20.glGetShaderInfoLog(fragmentShaderID));
        }

        // attach shaders to program
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);

        GL20.glLinkProgram(programID);

        if (GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: Program Linkging - " + GL20.glGetShaderInfoLog(programID));
        }

        GL20.glValidateProgram(programID);

        if (GL20.glGetProgrami(programID, GL20.GL_VALIDATE_STATUS) == GL11.GL_FALSE) {
            System.err.println("Error: Program Validating - " + GL20.glGetShaderInfoLog(programID));
        }
    }

    public abstract void bindAttributes();

    public void bindAttribute(int index, String location) {
        GL20.glBindAttribLocation(programID, index, location);
    }

    public void bind() {
        GL20.glUseProgram(programID);
    }

    public void remove() {
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    private String readFile(String file) {
        BufferedReader reader = null;
        StringBuilder string = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                string.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Error: Couldn't find file");
        }
        return string.toString();
    }

}
