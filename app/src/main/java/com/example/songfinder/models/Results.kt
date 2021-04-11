package com.example.songfinder.models

data class Results(
    val attr: Attr,
    val opensearchQuery: OpensearchQuery,
    val opensearchItemsPerPage: String,
    val opensearchStartIndex: String,
    val opensearchTotalResults: String,
    val trackmatches: Trackmatches
)