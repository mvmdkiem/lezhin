package com.lezhin.controller

import com.lezhin.controller.dto.ContentEvaluationDto
import com.lezhin.controller.dto.SingleResultDto
import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation
import com.lezhin.service.ContentEvaluationService
import com.lezhin.util.ResponseUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/content")
class ContentEvaluationController(private val evaluationService: ContentEvaluationService) {

    private val logger: Logger = LoggerFactory.getLogger(ContentEvaluationController::class.java)

    /**
     * 좋아요와 싫어요가 많은 작품을 조회하는 API
     * @return 좋아요와 싫어요가 많은 작품 리스트
     */
    @GetMapping("/rank")
    fun getTopEvaluatedContents(): SingleResultDto<Map<String, List<ContentInfo>>> {
        return try {
            val result = evaluationService.getContentRank()
            ResponseUtils.success(result, "좋아요와 싫어요가 많은 작품을 성공적으로 조회했습니다")
        } catch (ex: Exception) {
            logger.error("좋아요와 싫어요가 많은 작품 조회 중 오류 발생: ", ex)
            ResponseUtils.error("좋아요와 싫어요가 많은 작품 조회 중 오류 발생: ${ex.message}")
        }
    }

    /**
     * 특정 사용자가 해당 작품에 대해 평가를 할 수 있는 API
     * @param contentEvaluationDto 평가 정보 (userId, contentId, evaluationType)
     * @return 성공 메시지 또는 오류 메시지
     */
    @PostMapping("/evaluation/{contentId}")
    fun addContentEvaluate(@RequestBody contentEvaluationDto: ContentEvaluationDto): SingleResultDto<ContentEvaluation> {
        logger.info("평가 요청을 받았습니다: 사용자 ID = {}, 작품 ID = {}, 평가 유형 = {}", contentEvaluationDto.userId, contentEvaluationDto.contentId, contentEvaluationDto.evaluationType)
        return try {
            val evaluation = evaluationService.addContentEvaluate(contentEvaluationDto)
            ResponseUtils.success(evaluation, "평가가 성공적으로 저장되었습니다")
        } catch (ex: Exception) {
            logger.error("평가 저장 중 오류 발생: ", ex)
            ResponseUtils.error("평가 저장 중 오류 발생: ${ex.message}")
        }
    }
}
