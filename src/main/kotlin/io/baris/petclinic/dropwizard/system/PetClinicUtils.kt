package io.baris.petclinic.dropwizard.system

import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths

/**
 * Utilities for Pet Clinic application
 */
object PetClinicUtils {
    fun readFileToString(path: String): String {
        return if (isClasspathResource(path)) {
            readClasspathFileToString(extractPath(path))
        } else {
            Files.readString(Paths.get(path))
        }
    }

    fun resourceFilePath(path: String): String {
        return if (isClasspathResource(path)) {
            File(
                contextClassLoader.getResource(extractPath(path)).toURI()
            ).absolutePath
        } else {
            File(path).absolutePath
        }
    }

    private fun extractPath(path: String): String {
        return path.substring(10)
    }

    private fun isClasspathResource(path: String): Boolean {
        return path.startsWith("classpath:")
    }

    fun readClasspathFileToString(path: String?): String {
        contextClassLoader.getResourceAsStream(path).use { inputStream ->
            return String(
                inputStream.readAllBytes(),
                StandardCharsets.UTF_8
            )
        }
    }

    private val contextClassLoader: ClassLoader
        get() = Thread.currentThread().contextClassLoader
}
