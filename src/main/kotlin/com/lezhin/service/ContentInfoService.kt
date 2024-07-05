package com.lezhin.service

import com.lezhin.controller.dto.ContentInfoDto
import com.lezhin.domain.ContentInfo

interface ContentInfoService {
    /**
     * 특정 콘텐츠의 가격을 업데이트합니다.
     *
     * @param id 업데이트할 콘텐츠의 ID
     * @param priceDto 업데이트할 가격 정보가 담긴 DTO
     * @return 업데이트된 콘텐츠 정보, 없을 경우 null
     */
    fun updateContentPrice(id: Long, priceDto: ContentInfoDto): ContentInfo?
}
