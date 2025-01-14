/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
}

configurations.all {
    // Prevent unintentional transitive configuration
    isTransitive = false

    // Prevent unintentional version resolution
    resolutionStrategy {
        failOnChangingVersions()
        failOnDynamicVersions()
        failOnNonReproducibleResolution()
        failOnVersionConflict()
    }
}
