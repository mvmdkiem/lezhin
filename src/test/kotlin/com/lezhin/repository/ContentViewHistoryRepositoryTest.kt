package com.lezhin.repository

import com.lezhin.domain.ContentInfo
import com.lezhin.domain.UserInfo
import com.lezhin.domain.ContentViewHistory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import java.time.Instant
import java.time.LocalDate

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContentViewHistoryRepositoryTest @Autowired constructor(
    val contentViewHistoryRepository: ContentViewHistoryRepository,
    val userInfoRepository: UserInfoRepository,
    val contentInfoRepository: ContentInfoRepository
) {

    private val logger = LoggerFactory.getLogger(ContentViewHistoryRepositoryTest::class.java)

    /**
     * 조회 이력 저장 및 조회 테스트
     */
    @Test
    fun `조회 이력 저장 및 조회 테스트`() {
        val now = Instant.now()
        val today = LocalDate.now()

        // 사용자 생성 및 저장
        val userInfo = UserInfo(
            userName = "테스트 사용자",
            userEmail = "test@example.com",
            gender = "M",
            type = "regular",
            regDate = now
        )
        val savedUser = userInfoRepository.save(userInfo)

        // 콘텐츠 생성 및 저장
        val content = ContentInfo(
            contentName = "테스트 콘텐츠",
            author = "테스트 저자",
            openDate = now,
            regDate = now,
            price = 100,
            isAdult = true
        )
        val savedContent = contentInfoRepository.save(content)

        // 조회 이력 생성 및 저장
        val contentViewHistory = ContentViewHistory(
            content = savedContent,
            userInfo = savedUser,
            viewDate = today,
            regDate = today,
            isAdult = savedContent.isAdult
        )
        val savedViewHistory = contentViewHistoryRepository.save(contentViewHistory)
        logger.info("저장된 조회 이력: $savedViewHistory")
        assertNotNull(savedViewHistory.id)

        // 콘텐츠 ID로 조회 이력 조회
        val viewHistoriesByContentId = contentViewHistoryRepository.findByContentId(savedContent.id!!)
        assertNotNull(viewHistoriesByContentId)
        assertEquals(1, viewHistoriesByContentId.size)
        assertEquals(savedContent.id, viewHistoriesByContentId[0].content.id)

        // 사용자 ID로 조회 이력 조회
        val viewHistoriesByUserId = contentViewHistoryRepository.findByUserId(savedUser.id!!)
        assertNotNull(viewHistoriesByUserId)
        assertEquals(1, viewHistoriesByUserId.size)
        assertEquals(savedUser.id, viewHistoriesByUserId[0].userInfo.id)

        // 최근 1주일간 성인 콘텐츠를 3회 이상 조회한 사용자 조회
        val oneWeekAgo = LocalDate.now().minusWeeks(1)
        val adultContentViewers = contentViewHistoryRepository.findAdultContentViewers(oneWeekAgo)
        assertNotNull(adultContentViewers)
        assertEquals(1, adultContentViewers.size)
        assertEquals(savedUser.id, adultContentViewers[0])
    }

    /**
     * 사용자의 조회 이력 삭제 테스트
     */
    @Test
    fun `사용자의 조회 이력 삭제 테스트`() {
        val now = Instant.now()
        val today = LocalDate.now()

        // 사용자 생성 및 저장
        val userInfo = UserInfo(
            userName = "삭제 테스트 사용자",
            userEmail = "delete@example.com",
            gender = "F",
            type = "temporary",
            regDate = now
        )
        val savedUser = userInfoRepository.save(userInfo)

        // 콘텐츠 생성 및 저장
        val content = ContentInfo(
            contentName = "삭제 테스트 콘텐츠",
            author = "삭제 테스트 저자",
            openDate = now,
            regDate = now,
            price = 100,
            isAdult = true
        )
        val savedContent = contentInfoRepository.save(content)

        // 조회 이력 생성 및 저장
        val contentViewHistory = ContentViewHistory(
            content = savedContent,
            userInfo = savedUser,
            viewDate = today,
            regDate = today,
            isAdult = savedContent.isAdult
        )
        contentViewHistoryRepository.save(contentViewHistory)

        // 사용자 조회 이력 삭제
        contentViewHistoryRepository.deleteByUserId(savedUser.id!!)
        val viewHistoriesByUserId = contentViewHistoryRepository.findByUserId(savedUser.id!!)
        assertTrue(viewHistoriesByUserId.isEmpty())
    }
}
