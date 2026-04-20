package com.noodlegamer76.shadered.client.util.shader;

import com.noodlegamer76.shadered.ShaderedMod;

import javax.annotation.Nullable;

public class ShaderKeywords {

    public static final String BLUE = "#moj_import <shadered:blue.glsl>";

    @Nullable
    public static String findFragColorName(String source) {
        source = source
                .replaceAll("//.*", "")
                .replaceAll("/\\*.*?\\*/", "");

        var pattern = java.util.regex.Pattern.compile(
                "(?:layout\\s*\\([^)]*\\)\\s*)?out\\s+vec4\\s+(\\w+)",
                java.util.regex.Pattern.MULTILINE
        );

        var matcher = pattern.matcher(source);
        return matcher.find() ? matcher.group(1) : null;
    }

    public static String injectBeforeFragWrite(String source, String fragName, String code) {
        return source.replace(
                fragName + " =",
                code + "\n" + fragName + " ="
        );
    }

    public static String injectAfterVersion(String source, String injection) {
        int idx = source.indexOf("\n");
        return source.substring(0, idx + 1) + injection + "\n" + source.substring(idx + 1);
    }

    public static String injectBeforeMainEnd(String source, String injection) {
        int mainIndex = source.indexOf("void main");
        if (mainIndex == -1) return source;

        int braceStart = source.indexOf("{", mainIndex);
        if (braceStart == -1) return source;

        int depth = 0;
        for (int i = braceStart; i < source.length(); i++) {
            char c = source.charAt(i);

            if (c == '{') {
                depth++;
            } else if (c == '}') {
                depth--;

                if (depth == 0) {
                    return source.substring(0, i)
                            + "\n" + injection + "\n"
                            + source.substring(i);
                }
            }
        }

        ShaderedMod.LOGGER.error("Failed to find closing brace of main");
        return source;
    }
}