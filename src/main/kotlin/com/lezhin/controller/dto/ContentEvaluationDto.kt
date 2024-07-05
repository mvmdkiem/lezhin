package com.lezhin.controller.dto

data class ContentEvaluationDto(
    val userId: Long,
    val contentId: Long,
    val evaluationType: String,
    val comment: String? = null
)
