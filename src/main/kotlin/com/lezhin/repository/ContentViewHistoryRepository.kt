package com.lezhin.repository

import com.lezhin.domain.ContentViewHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

interface ContentViewHistoryRepository : JpaRepository<ContentViewHistory, Long> {
    /**
     * 특정 콘텐츠의 조회 이력을 찾습니다.
     *
     * @param contentId 조회 이력을 찾고자 하는 콘텐츠의 ID
     * @return 해당 콘텐츠의 조회 이력 목록
     */
    fun findByContentId(contentId: Long): List<ContentViewHistory>

    /**
     * 특정 사용자의 조회 이력을 찾습니다.
     *
     * @param userId 조회 이력을 찾고자 하는 사용자의 ID
     * @return 해당 사용자의 조회 이력 목록
     */
    fun findByUserInfoId(userId: Long): List<ContentViewHistory>

    /**
     * 최근 1주일간 성인 콘텐츠를 3회 이상 조회한 사용자 ID 목록을 찾습니다.
     *
     * @param oneWeekAgo 1주일 전의 날짜
     * @return 성인 콘텐츠를 3회 이상 조회한 사용자 ID 목록
     */
    @Query("SELECT vh.userInfo.id FROM ContentViewHistory vh WHERE vh.isAdult = true AND vh.viewDate > :oneWeekAgo GROUP BY vh.userInfo.id HAVING COUNT(vh.id) >= 3")
    fun findAdultContentViewers(oneWeekAgo: LocalDate): List<Long>

    /**
     * 특정 사용자의 모든 조회 이력을 삭제합니다.
     *
     * @param userId 삭제할 사용자의 ID
     */
    @Transactional
    fun deleteByUserInfoId(userId: Long)
}
