package com.lezhin.util

import com.lezhin.controller.dto.SingleResultDto
import com.lezhin.controller.dto.MultiResultDto

object ResponseUtils {
    fun <T> success(data: T, message: String = "Success"): SingleResultDto<T> {
        return SingleResultDto(
            resultCode = "200",
            resultMsg  = message,
            resultData = data
        )
    }

    fun <T> error(message: String): SingleResultDto<T> {
        return SingleResultDto(
            resultCode = "500",
            resultMsg  = message,
            resultData = null
        )
    }

    fun <T> multiSuccess(data: Collection<T>, message: String = "Success"): MultiResultDto<T> {
        return MultiResultDto(
            resultCode = "200",
            resultMsg  = message,
            resultCount= data.size,
            resultDatas= data
        )
    }

    fun <T> multiError(message: String): MultiResultDto<T> {
        return MultiResultDto(
            resultCode = "500",
            resultMsg = message,
            resultCount = 0,
            resultDatas = emptyList()
        )
    }
}
