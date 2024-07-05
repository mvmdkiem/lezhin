package com.lezhin.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.lezhin.service.UserInfoService
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@ExtendWith(MockitoExtension::class)
class UserInfoControllerTest {

    @Mock
    private lateinit var userInfoService: UserInfoService

    @InjectMocks
    private lateinit var userController: UserInfoController

    private lateinit var mockMvc: MockMvc

    private val objectMapper = ObjectMapper()

    private val logger: Logger = LoggerFactory.getLogger(UserInfoControllerTest::class.java)

    @BeforeEach
    fun setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build()
    }

    @Test
    fun `사용자 삭제 - 성공`() {
        // Given
        val userId = 1L

        // When/Then
        mockMvc.perform(
            delete("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun `사용자 삭제 - 실패`() {
        // Given
        val userId = 1L
        `when`(userInfoService.deleteUser(userId)).thenThrow(RuntimeException("사용자 삭제 실패"))

        // When/Then
        mockMvc.perform(
            delete("/api/users/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isInternalServerError)
    }
}
