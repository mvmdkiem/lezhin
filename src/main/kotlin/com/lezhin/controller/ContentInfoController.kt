package com.lezhin.controller

import com.lezhin.controller.dto.ContentInfoDto
import com.lezhin.controller.dto.SingleResultDto
import com.lezhin.service.ContentInfoService
import com.lezhin.domain.ContentInfo
import com.lezhin.util.ResponseUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/content")
class ContentInfoController(private val contentInfoService: ContentInfoService) {

    private val logger: Logger = LoggerFactory.getLogger(ContentInfoController::class.java)

    /**
     * 특정 작품을 유료, 무료로 변경할 수 있는 API
     * @param contentId 변경할 작품의 ID
     * @param priceDto 유료로 변경할 경우 입력하는 금액 (0이면 무료로 변경)
     * @return 변경된 작품 정보와 성공 메시지 또는 오류 메시지
     */
    @PutMapping("/price/{contentId}")

    fun updateContentPrice(@PathVariable contentId: Long, @RequestBody priceDto: ContentInfoDto): SingleResultDto<ContentInfo?> {
        logger.info("작품 가격 변경 요청을 받았습니다: 작품 ID = {}, 새로운 가격 = {}", contentId, priceDto.price)
        return try {
            val updatedContent = contentInfoService.updateContentPrice(contentId, priceDto)
            ResponseUtils.success(updatedContent, "작품 가격이 성공적으로 변경되었습니다")
        } catch (ex: Exception) {
            logger.error("작품 가격 변경 중 오류 발생: ", ex)
            ResponseUtils.error("작품 가격 변경 중 오류 발생: ${ex.message}")
        }
    }
}
