package com.lezhin.service.impl

import com.lezhin.controller.dto.ContentEvaluationDto
import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation
import com.lezhin.domain.UserInfo
import com.lezhin.repository.ContentInfoRepository
import com.lezhin.repository.ContentEvaluationRepository
import com.lezhin.repository.UserInfoRepository
import com.lezhin.service.ContentEvaluationService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.beans.factory.annotation.Autowired
import java.time.Instant
import org.slf4j.LoggerFactory

@SpringBootTest
class ContentEvaluationServiceImplTest @Autowired constructor(
    val evaluationService: ContentEvaluationService
) {

    @MockBean
    private lateinit var contentEvaluationRepository: ContentEvaluationRepository

    @MockBean
    private lateinit var userInfoRepository: UserInfoRepository

    @MockBean
    private lateinit var contentInfoRepository: ContentInfoRepository

    private val logger = LoggerFactory.getLogger(ContentEvaluationServiceImplTest::class.java)

    @Test
    fun `좋아요와 싫어요가 많은 콘텐츠 목록 조회 테스트`() {
        // 테스트 데이터 생성
        val content1 = ContentInfo(contentName = "테스트 컨텐츠 1", author = "저자 1", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false)
        val content2 = ContentInfo(contentName = "테스트 컨텐츠 2", author = "저자 2", openDate = Instant.now(), regDate = Instant.now(), price = 200, isAdult = false)

        // Mock 설정
        `when`(contentEvaluationRepository.findTopLikedContents()).thenReturn(listOf(content1))
        `when`(contentEvaluationRepository.findTopDislikedContents()).thenReturn(listOf(content2))

        // 서비스 호출 및 결과 검증
        val contentRank = evaluationService.getContentRank()
        assertNotNull(contentRank)
        assertEquals(1, contentRank["like"]?.size)
        assertEquals(1, contentRank["dislike"]?.size)
        assertEquals("테스트 컨텐츠 1", contentRank["like"]?.get(0)?.contentName)
        assertEquals("테스트 컨텐츠 2", contentRank["dislike"]?.get(0)?.contentName)
    }

    @Test
    fun `사용자가 특정 콘텐츠에 대해 평가 추가 테스트`() {
        // 테스트 데이터 생성
        val userInfo = UserInfo(userName = "테스트 사용자", userEmail = "test@example.com", gender = "M", type = "regular", regDate = Instant.now())
        val content = ContentInfo(contentName = "테스트 컨텐츠", author = "저자", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false)
        val contentEvaluationDto = ContentEvaluationDto(userId = 1L, contentId = 1L, evaluationType = "like", comment = "좋아요!")

        // Mock 설정
        `when`(userInfoRepository.findById(1L)).thenReturn(java.util.Optional.of(userInfo))
        `when`(contentInfoRepository.findById(1L)).thenReturn(java.util.Optional.of(content))
        `when`(contentEvaluationRepository.findByUserIdAndContentId(1L, 1L)).thenReturn(null)
        `when`(contentEvaluationRepository.save(any(ContentEvaluation::class.java))).thenAnswer { it.getArgument(0) }

        // 서비스 호출 및 결과 검증
        val evaluation = evaluationService.addContentEvaluate(contentEvaluationDto)
        assertNotNull(evaluation)
        assertEquals("like", evaluation.evaluationType)
        assertEquals("좋아요!", evaluation.comment)
    }

    @Test
    fun `특수문자 댓글 검증 테스트`() {
        // 테스트 데이터 생성
        val contentEvaluationDto = ContentEvaluationDto(userId = 1L, contentId = 1L, evaluationType = "like", comment = "좋아요!@#")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            evaluationService.addContentEvaluate(contentEvaluationDto)
        }

        assertEquals("댓글에 특수문자를 사용할 수 없습니다.", exception.message)
    }

    @Test
    fun `평가 필수값 검증 테스트`() {
        // 테스트 데이터 생성
        val contentEvaluationDto = ContentEvaluationDto(userId = 1L, contentId = 1L, evaluationType = "", comment = "좋아요")

        val exception = assertThrows(IllegalArgumentException::class.java) {
            evaluationService.addContentEvaluate(contentEvaluationDto)
        }

        assertEquals("좋아요/싫어요는 필수 입력값입니다.", exception.message)
    }
}
