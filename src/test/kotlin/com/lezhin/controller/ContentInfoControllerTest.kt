package com.lezhin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lezhin.controller.dto.ContentInfoDto
import com.lezhin.domain.ContentInfo
import com.lezhin.service.ContentInfoService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.Instant
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(MockitoExtension::class)
class ContentInfoControllerTest {

    @Mock
    private lateinit var contentInfoService: ContentInfoService

    @InjectMocks
    private lateinit var contentInfoController: ContentInfoController

    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contentInfoController).build()
    }

    @Test
    fun `작품 가격 변경 성공`() {
        // Given
        val contentId = 1L
        val contentInfoDto = ContentInfoDto(price = 10000)

        val updatedContentInfo = ContentInfo(
            id = contentId,
            contentName = "테스트 작품",
            author = "테스트 작가",
            price = contentInfoDto.price,
            regDate = Instant.now(),
            isAdult = false
        )

        `when`(contentInfoService.updateContentPrice(contentId, contentInfoDto)).thenReturn(updatedContentInfo)

        // When/Then
        mockMvc.perform(
            put("/api/content/price/{contentId}", contentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentInfoDto))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `작품 가격 변경 실패`() {
        // Given
        val contentId = 1L
        val contentInfoDto = ContentInfoDto(price = 10000)

        `when`(contentInfoService.updateContentPrice(contentId, contentInfoDto)).thenThrow(RuntimeException("작품 가격 변경 실패"))

        // When/Then
        mockMvc.perform(
            put("/api/content/price/{contentId}", contentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentInfoDto))
        )
            .andExpect(status().isOk)
    }
}
