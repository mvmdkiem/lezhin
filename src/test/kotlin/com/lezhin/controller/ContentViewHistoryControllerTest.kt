package com.lezhin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lezhin.domain.ContentInfo
import com.lezhin.domain.UserInfo
import com.lezhin.domain.ContentViewHistory
import com.lezhin.service.ContentViewHistoryService
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.Instant
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class ContentViewHistoryControllerTest {

    @Mock
    private lateinit var contentViewHistoryService: ContentViewHistoryService

    @InjectMocks
    private lateinit var contentViewHistoryController: ContentViewHistoryController

    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    private val logger: Logger = LoggerFactory.getLogger(ContentViewHistoryControllerTest::class.java)

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(contentViewHistoryController).build()
    }

    @Test
    fun `특정 작품의 조회 이력 조회 - 성공`() {
        // Given (주어진 상황)
        val contentInfo = ContentInfo(
            id = 1L,
            contentName = "테스트 작품",
            author = "테스트 저자",
            regDate = Instant.now(),
            price = 10000,
            isAdult = false
        )
        val userInfo = UserInfo(
            id = 1L,
            userName = "테스트 사용자",
            userEmail = "test@example.com",
            gender = "남성",
            type = "일반",
            regDate = Instant.now()
        )
        val viewHistories = listOf(
            ContentViewHistory(
                id = 1L,
                content = contentInfo,
                userInfo = userInfo,
                viewDate = LocalDate.now(),
                regDate = LocalDate.now(),
                isAdult = false
            ),
            ContentViewHistory(
                id = 2L,
                content = contentInfo,
                userInfo = userInfo,
                viewDate = LocalDate.now(),
                regDate = LocalDate.now(),
                isAdult = true
            )
        )
        `when`(contentViewHistoryService.getViewHistoryByContentId(1L)).thenReturn(viewHistories)

        // Perform GET request (GET 요청 수행)
        mockMvc.perform(
            get("/api/view/{contentId}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk) // 검증: HTTP 상태 코드가 OK(200)인지 확인
    }

    @Test
    fun `최근 1주일간 성인 작품 조회 사용자 목록 조회 - 성공`() {
        // Given (주어진 상황)
        val userInfo = UserInfo(
            id = 1L,
            userName = "테스트 사용자",
            userEmail = "test@example.com",
            gender = "남성",
            type = "일반",
            regDate = Instant.now()
        )
        val contentInfo = ContentInfo(
            id = 1L,
            contentName = "테스트 성인 작품",
            author = "테스트 저자",
            regDate = Instant.now(),
            price = 10000,
            isAdult = true
        )
        val contentViewHistory = ContentViewHistory(
            id = 1L,
            content = contentInfo,
            userInfo = userInfo,
            viewDate = LocalDate.now(),
            regDate = LocalDate.now(),
            isAdult = true
        )
        `when`(contentViewHistoryService.getAdultContentViewers()).thenReturn(listOf(userInfo))

        // Perform GET request (GET 요청 수행)
        mockMvc.perform(
            get("/api/view/adult")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk) // 검증: HTTP 상태 코드가 OK(200)인지 확인
    }
}
