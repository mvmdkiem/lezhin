package com.lezhin.service

import com.lezhin.controller.dto.ContentEvaluationDto
import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation

interface ContentEvaluationService {
    /**
     * 좋아요와 싫어요가 많은 콘텐츠 목록을 조회합니다.
     *
     * @return 좋아요와 싫어요가 많은 콘텐츠 목록을 포함하는 맵
     */
    fun getContentRank(): Map<String, List<ContentInfo>>

    /**
     * 사용자가 특정 콘텐츠에 대해 평가를 추가합니다.
     *
     * @param contentEvaluationDto 평가 정보가 담긴 DTO
     * @return 추가된 평가 정보
     */
    fun addContentEvaluate(contentEvaluationDto: ContentEvaluationDto): ContentEvaluation
}
