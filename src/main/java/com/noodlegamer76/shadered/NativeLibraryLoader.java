package com.noodlegamer76.shadered;

import org.slf4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public final class NativeLibraryLoader {

    private static final Logger LOGGER = ShaderedMod.LOGGER;
    private static final Set<String> LOADED_LIBRARIES = new HashSet<>();
    private static Path tempDir;

    private NativeLibraryLoader() {
    }

    /**
     * Loads all required native libraries.
     * Call this during client initialization.
     */
    public static void loadNatives() {
        try {
            if (tempDir == null) {
                tempDir = Files.createTempDirectory("shadered_natives");
                tempDir.toFile().deleteOnExit();
            }

            String platformPath = getPlatformPath();

            // Load required libraries
            loadLibrary(platformPath, getLibraryName("assimp"));

        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize native libraries", e);
        }
    }

    /**
     * Extracts and loads a native library from the classpath.
     */
    private static void loadLibrary(String platformPath, String libraryName) {
        if (LOADED_LIBRARIES.contains(libraryName)) {
            return;
        }

        String resourcePath = "natives/" + platformPath + "/" + libraryName;

        try (InputStream inputStream = NativeLibraryLoader.class
                .getClassLoader()
                .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {
                throw new RuntimeException("Native library not found in JAR: " + resourcePath);
            }

            Path extractedPath = tempDir.resolve(libraryName);
            Files.copy(inputStream, extractedPath, StandardCopyOption.REPLACE_EXISTING);
            extractedPath.toFile().deleteOnExit();

            System.load(extractedPath.toAbsolutePath().toString());

            LOADED_LIBRARIES.add(libraryName);
            LOGGER.info("Loaded native library: {}", resourcePath);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load native library: " + resourcePath, e);
        }
    }

    /**
     * Determines the platform-specific subdirectory.
     */
    private static String getPlatformPath() {
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch").toLowerCase();

        boolean is64Bit = arch.contains("64") || arch.equals("amd64") || arch.equals("x86_64");

        if (os.contains("win")) {
            return is64Bit ? "windows/x86_64" : "windows/x86";
        } else if (os.contains("mac")) {
            return (arch.contains("aarch64") || arch.contains("arm"))
                    ? "osx/arm64"
                    : "osx/x86_64";
        } else if (os.contains("linux")) {
            return is64Bit ? "linux/x86_64" : "linux/x86";
        }

        throw new UnsupportedOperationException(
                "Unsupported platform: OS=" + os + ", ARCH=" + arch);
    }

    /**
     * Returns the correct native library file name for the current OS.
     */
    private static String getLibraryName(String baseName) {
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            return baseName + ".dll";
        } else if (os.contains("mac")) {
            return "lib" + baseName + ".dylib";
        } else if (os.contains("linux")) {
            return "lib" + baseName + ".so";
        }

        throw new UnsupportedOperationException("Unsupported OS: " + os);
    }
}