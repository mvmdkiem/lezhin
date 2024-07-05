package com.lezhin.controller

import com.lezhin.controller.dto.SingleResultDto
import com.lezhin.domain.UserInfo
import com.lezhin.domain.ContentViewHistory
import com.lezhin.service.ContentViewHistoryService
import com.lezhin.util.ResponseUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/view")
class ContentViewHistoryController(private val contentViewHistoryService: ContentViewHistoryService) {

    private val logger: Logger = LoggerFactory.getLogger(ContentViewHistoryController::class.java)

    /**
     * 특정 작품의 조회 이력 조회
     * @param contentId 조회할 작품의 ID
     * @return 작품의 조회 이력 리스트
     */
    @GetMapping("/{contentId}")
    fun getViewHistoryByContentId(@PathVariable contentId: Long): SingleResultDto<List<ContentViewHistory>> {
        return try {
            val result = contentViewHistoryService.getViewHistoryByContentId(contentId)
            ResponseUtils.success(result, "작품 조회 이력을 성공적으로 조회했습니다")
        } catch (ex: Exception) {
            logger.error("작품 조회 이력 조회 중 오류 발생: ", ex)
            ResponseUtils.error("작품 조회 이력 조회 중 오류 발생: ${ex.message}")
        }
    }

    /**
     * 최근 1주일간 성인 작품을 3개 이상 조회한 사용자 목록 조회
     * @return 성인 작품을 3개 이상 조회한 사용자 ID 리스트
     */
    @GetMapping("/adult")
    fun getAdultContentViewers(): SingleResultDto<List<UserInfo?>> {
        return try {
            val result = contentViewHistoryService.getAdultContentViewers()
            ResponseUtils.success(result, "성인 작품을 3개 이상 조회한 사용자 목록을 성공적으로 조회했습니다")
        } catch (ex: Exception) {
            logger.error("성인 작품 조회 사용자 목록 조회 중 오류 발생: ", ex)
            ResponseUtils.error("성인 작품 조회 사용자 목록 조회 중 오류 발생: ${ex.message}")
        }
    }
}
