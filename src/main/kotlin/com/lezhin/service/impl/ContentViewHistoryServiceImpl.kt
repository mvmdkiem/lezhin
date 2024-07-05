package com.lezhin.service.impl

import com.lezhin.domain.UserInfo
import com.lezhin.domain.ContentViewHistory
import com.lezhin.repository.ContentInfoRepository
import com.lezhin.repository.UserInfoRepository
import com.lezhin.repository.ContentViewHistoryRepository
import com.lezhin.service.ContentViewHistoryService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ContentViewHistoryServiceImpl(
    private val contentViewHistoryRepository: ContentViewHistoryRepository,
    private val userInfoRepository: UserInfoRepository,
    private val contentInfoRepository: ContentInfoRepository
) : ContentViewHistoryService {

    /**
     * 사용자가 특정 콘텐츠를 조회한 이력을 추가합니다.
     *
     * @param contentId 조회한 콘텐츠의 ID
     * @param userId 조회한 사용자의 ID
     * @return 추가된 조회 이력 정보
     */
    @Transactional
    override fun addViewHistory(contentId: Long, userId: Long): ContentViewHistory {
        return try {
            val user = userInfoRepository.findById(userId)
                .orElseThrow { IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다.") }
            val content = contentInfoRepository.findById(contentId)
                .orElseThrow { IllegalArgumentException("해당 ID의 작품이 존재하지 않습니다.") }

            val contentViewHistory = ContentViewHistory(
                content = content,
                userInfo = user,
                viewDate = LocalDate.now(),
                regDate = LocalDate.now(),
                isAdult = content.isAdult
            )

            contentViewHistoryRepository.save(contentViewHistory)
        } catch (ex: Exception) {
            println("조회 이력 추가 중 오류 발생: ${ex.message}")
            throw ex
        }
    }

    /**
     * 특정 콘텐츠의 조회 이력 목록을 조회합니다.
     *
     * @param contentId 조회할 콘텐츠의 ID
     * @return 해당 콘텐츠의 조회 이력 목록
     */
    @Transactional(readOnly = true)
    override fun getViewHistoryByContentId(contentId: Long): List<ContentViewHistory> {
        return try {
            contentViewHistoryRepository.findByContentId(contentId)
        } catch (ex: Exception) {
            println("조회 이력 조회 중 오류 발생: ${ex.message}")
            emptyList()
        }
    }

    /**
     * 최근 1주일간 성인 콘텐츠를 3회 이상 조회한 사용자 목록을 조회합니다.
     *
     * @return 성인 콘텐츠를 3회 이상 조회한 사용자 목록
     */
    @Transactional(readOnly = true)
    override fun getAdultContentViewers(): List<UserInfo?> {
        return try {
            val oneWeekAgo = LocalDate.now().minusWeeks(1)
            val userIds = contentViewHistoryRepository.findAdultContentViewers(oneWeekAgo)
            userIds.map { userInfoRepository.findById(it).orElse(null) }
        } catch (ex: Exception) {
            println("성인 콘텐츠 조회 사용자 목록 조회 중 오류 발생: ${ex.message}")
            emptyList()
        }
    }

    /**
     * 특정 사용자의 조회 이력을 삭제합니다.
     *
     * @param userId 삭제할 사용자의 ID
     * @return 삭제된 조회 이력 목록
     */
    @Transactional
    override fun delViewHistoryByUserId(userId: Long): List<ContentViewHistory?> {
        return try {
            val viewHistories = contentViewHistoryRepository.findByUserInfoId(userId)
            contentViewHistoryRepository.deleteAll(viewHistories)
            viewHistories
        } catch (ex: Exception) {
            println("조회 이력 삭제 중 오류 발생: ${ex.message}")
            emptyList()
        }
    }
}
