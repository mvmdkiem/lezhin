package com.lezhin.service.impl

import com.lezhin.domain.ContentInfo
import com.lezhin.domain.UserInfo
import com.lezhin.domain.ContentViewHistory
import com.lezhin.repository.ContentInfoRepository
import com.lezhin.repository.UserInfoRepository
import com.lezhin.repository.ContentViewHistoryRepository
import com.lezhin.service.ContentViewHistoryService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import java.time.LocalDate
import java.time.Instant

@SpringBootTest
class ContentViewHistoryServiceImplTest @Autowired constructor(
    val contentViewHistoryService: ContentViewHistoryService
) {

    @MockBean
    private lateinit var contentViewHistoryRepository: ContentViewHistoryRepository

    @MockBean
    private lateinit var userInfoRepository: UserInfoRepository

    @MockBean
    private lateinit var contentInfoRepository: ContentInfoRepository

    @Test
    fun `조회 이력 추가 테스트`() {
        val userId: Long = 1L
        val contentId: Long = 1L
        val userInfo = UserInfo(userId, "테스트 사용자", "test@example.com", "M", "regular", Instant.now())
        val content = ContentInfo(contentId, "테스트 콘텐츠", "테스트 저자", Instant.now(), Instant.now(), 100, false)
        val contentViewHistory = ContentViewHistory(null, content, userInfo, LocalDate.now(), LocalDate.now(), false)

        `when`(userInfoRepository.findById(userId)).thenReturn(java.util.Optional.of(userInfo))
        `when`(contentInfoRepository.findById(contentId)).thenReturn(java.util.Optional.of(content))
        `when`(contentViewHistoryRepository.save(any(ContentViewHistory::class.java))).thenReturn(contentViewHistory)

        val result = contentViewHistoryService.addViewHistory(contentId, userId)

        assertNotNull(result)
        assertEquals(userInfo, result.userInfo)
        assertEquals(content, result.content)
    }

    @Test
    fun `콘텐츠 ID로 조회 이력 조회 테스트`() {
        val contentId: Long = 1L
        val userInfo = UserInfo(1L, "테스트 사용자", "test@example.com", "M", "regular", Instant.now())
        val content = ContentInfo(contentId, "테스트 콘텐츠", "테스트 저자", Instant.now(), Instant.now(), 100, false)
        val contentViewHistory = ContentViewHistory(1L, content, userInfo, LocalDate.now(), LocalDate.now(), false)

        `when`(contentViewHistoryRepository.findByContentId(contentId)).thenReturn(listOf(contentViewHistory))

        val result = contentViewHistoryService.getViewHistoryByContentId(contentId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(contentViewHistory, result[0])
    }

    @Test
    fun `성인 콘텐츠 조회 사용자 목록 조회 테스트`() {
        val userId: Long = 1L
        val userInfo = UserInfo(userId, "테스트 사용자", "test@example.com", "M", "regular", Instant.now())
        val oneWeekAgo = LocalDate.now().minusWeeks(1)

        `when`(contentViewHistoryRepository.findAdultContentViewers(oneWeekAgo)).thenReturn(listOf(userId))
        `when`(userInfoRepository.findById(userId)).thenReturn(java.util.Optional.of(userInfo))

        val result = contentViewHistoryService.getAdultContentViewers()

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(userInfo, result[0])
    }

    @Test
    fun `사용자의 조회 이력 삭제 테스트`() {
        val userId: Long = 1L
        val userInfo = UserInfo(userId, "테스트 사용자", "test@example.com", "M", "regular", Instant.now())
        val content = ContentInfo(1L, "테스트 콘텐츠", "테스트 저자", Instant.now(), Instant.now(), 100, false)
        val contentViewHistory = ContentViewHistory(1L, content, userInfo, LocalDate.now(), LocalDate.now(), false)

        `when`(contentViewHistoryRepository.findByUserId(userId)).thenReturn(listOf(contentViewHistory))
        doNothing().`when`(contentViewHistoryRepository).deleteAll(listOf(contentViewHistory))

        val result = contentViewHistoryService.delViewHistoryByUserId(userId)

        assertNotNull(result)
        assertEquals(1, result.size)
        assertEquals(contentViewHistory, result[0])
        verify(contentViewHistoryRepository, times(1)).deleteAll(listOf(contentViewHistory))
    }
}
