package com.lezhin.repository

import com.lezhin.domain.ContentInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import java.time.Instant

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ContentInfoRepositoryTest @Autowired constructor(
    val contentInfoRepository: ContentInfoRepository
) {

    private val logger = LoggerFactory.getLogger(ContentInfoRepositoryTest::class.java)

    /**
     * 콘텐츠 정보 저장 및 조회 테스트
     */
    @Test
    fun `콘텐츠 정보 저장 및 조회 테스트`() {
        val now = Instant.now()
        val contentInfo = ContentInfo(
            contentName = "테스트 콘텐츠",
            author = "테스트 저자",
            openDate = now,
            regDate = now,
            price = 100,
            isAdult = false
        )

        // 콘텐츠 정보 저장
        val savedContentInfo = contentInfoRepository.save(contentInfo)
        logger.info("저장된 콘텐츠 정보: $savedContentInfo")
        assertNotNull(savedContentInfo.id)

        // 콘텐츠 정보 조회
        val foundContentInfo = contentInfoRepository.findById(savedContentInfo.id!!)
        logger.info("조회된 콘텐츠 정보: $foundContentInfo")
        assertNotNull(foundContentInfo)

        // 검증
        logger.info("조회된 콘텐츠 세부 정보:")
        logger.info("콘텐츠 이름: ${foundContentInfo.get().contentName}")
        logger.info("저자: ${foundContentInfo.get().author}")
        logger.info("공개 날짜: ${foundContentInfo.get().openDate}")
        logger.info("등록 날짜: ${foundContentInfo.get().regDate}")
        logger.info("가격: ${foundContentInfo.get().price}")
        logger.info("성인용 여부: ${foundContentInfo.get().isAdult}")

        assertEquals("테스트 콘텐츠", foundContentInfo.get().contentName)
        assertEquals("테스트 저자", foundContentInfo.get().author)
        assertEquals(now, foundContentInfo.get().openDate)
        assertEquals(now, foundContentInfo.get().regDate)
        assertEquals(100, foundContentInfo.get().price)
        assertEquals(false, foundContentInfo.get().isAdult)
    }
}
