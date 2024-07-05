package com.lezhin.repository

import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface ContentEvaluationRepository : JpaRepository<ContentEvaluation, Long> {
    /**
     * 특정 사용자와 특정 콘텐츠에 대한 평가를 찾습니다.
     *
     * @param userId 사용자의 ID
     * @param contentId 콘텐츠의 ID
     * @return 해당 사용자와 콘텐츠에 대한 평가 정보, 없을 경우 null
     */
    fun findByUserInfoIdAndContentId(userId: Long, contentId: Long): ContentEvaluation?

    /**
     * 좋아요가 가장 많은 콘텐츠 목록을 조회합니다.
     * 상위 3개의 콘텐츠만 반환합니다.
     *
     * @return 좋아요가 많은 순서대로 정렬된 상위 3개의 콘텐츠 목록
     */
    @Query("SELECT e.content FROM ContentEvaluation e WHERE e.evaluationType = 'like' GROUP BY e.content.id ORDER BY COUNT(e.id) DESC")
    fun findTopLikedContents(): List<ContentInfo>

    /**
     * 싫어요가 가장 많은 콘텐츠 목록을 조회합니다.
     * 상위 3개의 콘텐츠만 반환합니다.
     *
     * @return 싫어요가 많은 순서대로 정렬된 상위 3개의 콘텐츠 목록
     */
    @Query("SELECT e.content FROM ContentEvaluation e WHERE e.evaluationType = 'dislike' GROUP BY e.content.id ORDER BY COUNT(e.id) DESC")
    fun findTopDislikedContents(): List<ContentInfo>

    /**
     * 특정 사용자의 모든 평가를 삭제합니다.
     *
     * @param userId 삭제할 사용자의 ID
     */
    @Transactional
    fun deleteByUserInfoId(userId: Long)
}
