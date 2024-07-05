package com.lezhin.service.impl

import com.lezhin.controller.dto.ContentInfoDto
import com.lezhin.service.ContentInfoService
import com.lezhin.repository.ContentInfoRepository
import com.lezhin.domain.ContentInfo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ContentServiceImpl(private val rep: ContentInfoRepository) : ContentInfoService {

    /**
     * 특정 콘텐츠의 가격을 업데이트합니다.
     *
     * @param id 업데이트할 콘텐츠의 ID
     * @param priceDto 업데이트할 가격 정보가 담긴 DTO
     * @return 업데이트된 콘텐츠 정보, 없을 경우 null
     */
    @Transactional
    override fun updateContentPrice(id: Long, priceDto: ContentInfoDto): ContentInfo? {
        return try {
            val contentInfo = rep.findById(id).orElseThrow { IllegalArgumentException("해당 ID의 작품이 존재하지 않습니다.") }

            // 가격 유효성 검사
            val price = priceDto.price
            if (price == 0 || (price in 100..500)) {
                contentInfo.price = price
                rep.save(contentInfo)
                contentInfo
            } else {
                throw IllegalArgumentException("가격은 0원 또는 100원에서 500원 사이여야 합니다.")
            }
        } catch (ex: Exception) {
            println("콘텐츠 가격 업데이트 중 오류 발생: ${ex.message}")
            null
        }
    }
}
