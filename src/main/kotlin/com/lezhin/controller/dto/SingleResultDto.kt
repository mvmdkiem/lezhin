package com.lezhin.controller.dto

data class SingleResultDto<T>(
    val resultCode : String,
    val resultMsg  : String,
    val resultData : T? = null
)