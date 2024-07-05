package com.lezhin.service

import com.lezhin.domain.UserInfo
import com.lezhin.domain.ContentViewHistory

interface ContentViewHistoryService {
    /**
     * 사용자가 특정 콘텐츠를 조회한 이력을 추가합니다.
     *
     * @param contentId 조회한 콘텐츠의 ID
     * @param userId 조회한 사용자의 ID
     * @return 추가된 조회 이력 정보
     */
    fun addViewHistory(contentId: Long, userId: Long): ContentViewHistory

    /**
     * 특정 콘텐츠의 조회 이력 목록을 조회합니다.
     *
     * @param contentId 조회할 콘텐츠의 ID
     * @return 해당 콘텐츠의 조회 이력 목록
     */
    fun getViewHistoryByContentId(contentId: Long): List<ContentViewHistory>

    /**
     * 최근 1주일간 성인 콘텐츠를 3회 이상 조회한 사용자 목록을 조회합니다.
     *
     * @return 성인 콘텐츠를 3회 이상 조회한 사용자 목록
     */
    fun getAdultContentViewers(): List<UserInfo?>

    /**
     * 특정 사용자의 조회 이력을 삭제합니다.
     *
     * @param userId 삭제할 사용자의 ID
     * @return 삭제된 조회 이력 목록
     */
    fun delViewHistoryByUserId(userId: Long): List<ContentViewHistory?>
}
