package com.lezhin.service.impl

import com.lezhin.repository.ContentEvaluationRepository
import com.lezhin.repository.UserInfoRepository
import com.lezhin.repository.ContentViewHistoryRepository
import com.lezhin.service.UserInfoService
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired

@SpringBootTest
class UserInfoServiceImplTest @Autowired constructor(
    val userInfoService: UserInfoService
) {

    @MockBean
    private lateinit var userInfoRepository: UserInfoRepository

    @MockBean
    private lateinit var contentEvaluationRepository: ContentEvaluationRepository

    @MockBean
    private lateinit var contentViewHistoryRepository: ContentViewHistoryRepository

    @Test
    fun `사용자 삭제 테스트`() {
        // 사용자 ID
        val userId: Long = 1L

        // Mock 설정
        doNothing().`when`(contentEvaluationRepository).deleteByUserId(userId)
        doNothing().`when`(contentViewHistoryRepository).deleteByUserId(userId)
        doNothing().`when`(userInfoRepository).deleteById(userId)

        // 서비스 호출
        userInfoService.deleteUser(userId)

        // 검증
        verify(contentEvaluationRepository, times(1)).deleteByUserId(userId)
        verify(contentViewHistoryRepository, times(1)).deleteByUserId(userId)
        verify(userInfoRepository, times(1)).deleteById(userId)

        // Mock을 이용한 검증
        assertFalse(userInfoRepository.existsById(userId))
    }
}
