/* Copyright © 2024 CI&T. All Rights Reserved.
 * Unauthorized copying of this file's contents, via any medium, is strictly prohibited
 * Proprietary
 * Written by Henrique Miranda <hmiranda@ciandt.com>
 */

package com.ciandt.globo.tmdb.app.home

data class HomeScreenData(
    val movies: List<SectionContent>,
    val series: List<SectionContent>,
    val soapOperas: List<SectionContent>,
)

data class SectionContent(
    val accessibilityDescription: String,
    val imageModel: Any,
)
