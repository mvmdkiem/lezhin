package com.lezhin.service.impl

import com.lezhin.controller.dto.ContentInfoDto
import com.lezhin.domain.ContentInfo
import com.lezhin.repository.ContentInfoRepository
import com.lezhin.service.ContentInfoService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.*
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant
import java.util.*

@SpringBootTest
class ContentInfoServiceImplTest {

    private val contentInfoRepository: ContentInfoRepository = mock(ContentInfoRepository::class.java)
    private val contentInfoService: ContentInfoService = ContentServiceImpl(contentInfoRepository)
    private val logger = LoggerFactory.getLogger(ContentInfoServiceImplTest::class.java)

    /**
     * 유효한 가격으로 콘텐츠 가격 업데이트 테스트
     */
    @Test
    fun `유효한 가격으로 콘텐츠 가격 업데이트 테스트`() {
        val now = Instant.now()
        val contentId = 1L
        val contentInfo = ContentInfo(
            id = contentId,
            contentName = "테스트 콘텐츠",
            author = "테스트 저자",
            openDate = now,
            regDate = now,
            price = 0,
            isAdult = false
        )
        val priceDto = ContentInfoDto(price = 300)

        `when`(contentInfoRepository.findById(contentId)).thenReturn(Optional.of(contentInfo))
        `when`(contentInfoRepository.save(any(ContentInfo::class.java))).thenReturn(contentInfo)

        val updatedContent = contentInfoService.updateContentPrice(contentId, priceDto)

        assertNotNull(updatedContent)
        assertEquals(300, updatedContent?.price)
        verify(contentInfoRepository, times(1)).findById(contentId)
        verify(contentInfoRepository, times(1)).save(contentInfo)
    }

    /**
     * 유효하지 않은 가격으로 콘텐츠 가격 업데이트 테스트
     */
    @Test
    fun `유효하지 않은 가격으로 콘텐츠 가격 업데이트 테스트`() {
        val now = Instant.now()
        val contentId = 1L
        val contentInfo = ContentInfo(
            id = contentId,
            contentName = "테스트 콘텐츠",
            author = "테스트 저자",
            openDate = now,
            regDate = now,
            price = 0,
            isAdult = false
        )
        val priceDto = ContentInfoDto(price = 600)

        `when`(contentInfoRepository.findById(contentId)).thenReturn(Optional.of(contentInfo))

        val exception = assertThrows<IllegalArgumentException> {
            contentInfoService.updateContentPrice(contentId, priceDto)
        }

        assertEquals("가격은 0원 또는 100원에서 500원 사이여야 합니다.", exception.message)
        verify(contentInfoRepository, times(1)).findById(contentId)
        verify(contentInfoRepository, never()).save(any(ContentInfo::class.java))
    }

    /**
     * 존재하지 않는 콘텐츠 ID로 가격 업데이트 시도 테스트
     */
    @Test
    fun `존재하지 않는 콘텐츠 ID로 가격 업데이트 시도 테스트`() {
        val contentId = 1L
        val priceDto = ContentInfoDto(price = 300)

        `when`(contentInfoRepository.findById(contentId)).thenReturn(Optional.empty())

        val exception = assertThrows<IllegalArgumentException> {
            contentInfoService.updateContentPrice(contentId, priceDto)
        }

        assertEquals("해당 ID의 작품이 존재하지 않습니다.", exception.message)
        verify(contentInfoRepository, times(1)).findById(contentId)
        verify(contentInfoRepository, never()).save(any(ContentInfo::class.java))
    }
}
