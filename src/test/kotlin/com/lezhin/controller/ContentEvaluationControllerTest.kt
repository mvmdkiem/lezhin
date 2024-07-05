package com.lezhin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lezhin.controller.dto.ContentEvaluationDto
import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation
import com.lezhin.domain.UserInfo
import com.lezhin.service.ContentEvaluationService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ContentEvaluationControllerTest {

    @Mock
    private lateinit var evaluationService: ContentEvaluationService

    @InjectMocks
    private lateinit var contentEvaluationController: ContentEvaluationController

    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    private val logger: Logger = LoggerFactory.getLogger(ContentEvaluationControllerTest::class.java)

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contentEvaluationController).build()
    }

    @Test
    fun `좋아요와 싫어요가 많은 작품 조회 - 성공`() {
        // Given
        val topContentsMap = evaluationService.getContentRank()
        `when`(evaluationService.getContentRank()).thenReturn(topContentsMap)

        // When/Then
        mockMvc.perform(
            get("/api/content/rank")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `좋아요와 싫어요가 많은 작품 조회 - 실패`() {
        // Given
        `when`(evaluationService.getContentRank()).thenThrow(RuntimeException("좋아요와 싫어요가 많은 작품 조회 실패"))

        // When/Then
        mockMvc.perform(
            get("/api/content/rank")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
    }

    @Test
    fun `작품 평가 추가 - 성공`() {
        // Given
        val contentEvaluationDto = ContentEvaluationDto(userId = 1L, contentId = 1L, evaluationType = "like", comment = "좋은 작품입니다.")
        val contentEvaluation = ContentEvaluation(id = 1L, userInfo = UserInfo(), content = ContentInfo(), evaluationType = "", regDate = LocalDate.now())
        `when`(evaluationService.addContentEvaluate(contentEvaluationDto)).thenReturn(contentEvaluation)

        // When/Then
        mockMvc.perform(
            post("/api/content/evaluation/{contentId}", contentEvaluationDto.contentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentEvaluationDto))
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `작품 평가 추가 - 실패`() {
        // Given
        val contentEvaluationDto = ContentEvaluationDto(userId = 1L, contentId = 1L, evaluationType = "like", comment = "좋은 작품입니다.")
        `when`(evaluationService.addContentEvaluate(contentEvaluationDto)).thenThrow(RuntimeException("작품 평가 추가 실패"))

        // When/Then
        mockMvc.perform(
            post("/api/content/evaluation/{contentId}", contentEvaluationDto.contentId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contentEvaluationDto))
        )
            .andExpect(status().isInternalServerError)
    }
}
