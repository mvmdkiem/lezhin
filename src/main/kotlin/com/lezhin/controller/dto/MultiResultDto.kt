package com.lezhin.controller.dto

data class MultiResultDto<T>(
    val resultCode  : String,
    val resultMsg   : String,
    val resultCount : Int,
    val resultDatas : Collection<T>?
)