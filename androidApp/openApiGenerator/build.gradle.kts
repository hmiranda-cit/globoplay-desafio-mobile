/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

plugins {
    alias(libs.plugins.openApi.generator)
}

openApiGenerate {
    // General options
    cleanupOutput = true
    generateApiDocumentation = false
    generateApiTests = false
    generateModelDocumentation = false
    generateModelTests = false
    generatorName = "kotlin"
    skipOperationExample = true
    validateSpec = true

    // Naming options
    val packagePrefix = "generated.openapi"
    packageName = packagePrefix
    apiPackage = "$packagePrefix.client"
    invokerPackage = "$packagePrefix.invoker"
    modelPackage = "$packagePrefix.model"

    // Generator options
    library = "jvm-okhttp4"
    additionalProperties = buildMap {
        put("nullableReturnType", false)
        put("omitGradlePluginVersions", true)
        put("omitGradleWrapper", true)
        put("parcelizeModels", true)
        put("serializableModel", true)
        put("serializationLibrary", "moshi")
        put("useSettingsGradle", false)
    }

    // IO options
    inputSpec = layout.projectDirectory.file("src/trimmed-tmdb-openapi-spec.json").asFile.path
    outputDir = layout.buildDirectory.dir("generated").get().asFile.path
}
