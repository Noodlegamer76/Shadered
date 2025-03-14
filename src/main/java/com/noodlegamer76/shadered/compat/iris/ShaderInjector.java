package com.noodlegamer76.shadered.compat.iris;

import com.noodlegamer76.shadered.event.RenderEventsForFbos;
import com.noodlegamer76.shadered.event.RenderEventsForRenderTargets;
import com.noodlegamer76.shadered.mixin.ProgramSourceAccessor;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.api.v0.IrisApi;
import net.irisshaders.iris.shaderpack.IdMap;
import net.irisshaders.iris.shaderpack.ShaderPack;
import net.irisshaders.iris.shaderpack.materialmap.BlockEntry;
import net.irisshaders.iris.shaderpack.materialmap.NamespacedId;
import net.irisshaders.iris.shaderpack.programs.ProgramSource;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShaderInjector {
    public static int sampler;

    public static String injectAfterMain(String shaderSource, String codeToInject) {
        // Regex to find "void main() {" or similar variations
        Pattern pattern = Pattern.compile("void\\s+main\\s*\\(\\s*\\)\\s*\\{");
        Matcher matcher = pattern.matcher(shaderSource);

        if (matcher.find()) {
            int insertPosition = matcher.end(); // Get position after '{'
            return shaderSource.substring(0, insertPosition) + "\n" + codeToInject + shaderSource.substring(insertPosition);
        }

        return shaderSource;
    }

    public static String getOutVec4Variable(String shaderSource) {
        // Regex pattern to match "out vec4 <name>;"
        Pattern pattern = Pattern.compile("out\\s+vec4\\s+(\\w+)\\s*;");
        Matcher matcher = pattern.matcher(shaderSource);

        if (matcher.find()) {
            return matcher.group(1); // Return the captured variable name
        }

        return null; // Return null if no match is found
    }

    public static String injectBeforeLastBrace(String shaderSource, String codeToInject) {
        // Regex to find the LAST closing brace "}"
        int lastBraceIndex = shaderSource.lastIndexOf("}");
        if (lastBraceIndex != -1) {
            shaderSource = shaderSource.substring(0, lastBraceIndex) + "\n" + codeToInject + "\n" + shaderSource.substring(lastBraceIndex);
        }

        return shaderSource; // Return original if no closing brace is found
    }

    public static String injectAfterVersion(String shaderSource, String codeToInject) {
        // Regex to match the version declaration
        Pattern pattern = Pattern.compile("#version\\s+\\d+");
        Matcher matcher = pattern.matcher(shaderSource);

        if (matcher.find()) {
            int insertPosition = shaderSource.indexOf("\n", matcher.end()) + 1; // Get position at the beginning of the next line

            // If the newline after #version isn't found, insert it at the end of the version declaration
            if (insertPosition == 0) {
                insertPosition = matcher.end();
            }

            return shaderSource.substring(0, insertPosition) + codeToInject + shaderSource.substring(insertPosition);
        }

        return shaderSource; // Return original if no #version is found
    }

    public static void modifyShaderPack(ShaderPack pack) throws IOException {
        RenderEventsForFbos.pack = pack;



        if (pack.getProgramSet(Iris.getCurrentDimension()).getGbuffersBlock().isEmpty()) {
            return;
        }
        ProgramSource source = pack.getProgramSet(Iris.getCurrentDimension()).getGbuffersBlock().get();

        if (source.getFragmentSource().isEmpty()) {
            return;
        }

        String shaderCode = source.getFragmentSource().get();

        if (!shaderCode.contains("uniform int blockEntityId;")) {
            shaderCode = ShaderInjector.injectAfterVersion(shaderCode, "uniform int blockEntityId;");
        }

        //Variables
        String out = getOutVec4Variable(shaderCode);

        String colorSet = " if (blockEntityId == " + RenderEventsForRenderTargets.skyblockId + ") {" +
                " ivec2 coord1212 = ivec2(gl_FragCoord.xy); " + out +
                " = texelFetch(gtexture, coord1212, 0); " +
                " } ";

        String colorOutTest = out + " = vec4(1.0, 1.0, 1.0, 1.0);";
        shaderCode = ShaderInjector.injectBeforeLastBrace(shaderCode, colorOutTest);

        shaderCode = ShaderInjector.injectBeforeLastBrace(shaderCode, colorSet);

        ((ProgramSourceAccessor) source).setFragmentSource(shaderCode);

        System.out.println("Modified Shader Code:\n" + shaderCode);
    }

    public static void cantModifyShaderPack(String error) {
        System.err.println("Can't modify shader pack for Shadered: " + error);
    }

    public static byte[] getTextureData(int textureId, int width, int height) {
        // Bind the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        // Allocate a buffer to store pixel data
        int bytesPerPixel = 4; // RGBA format
        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * bytesPerPixel);

        // Read the pixel data from OpenGL
        GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL11.GL_UNSIGNED_BYTE, buffer);

        // Convert ByteBuffer to byte array
        byte[] pixelData = new byte[buffer.remaining()];
        buffer.get(pixelData);

        return pixelData;
    }
}
