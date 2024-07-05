package com.lezhin.repository

import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation
import com.lezhin.domain.UserInfo
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
class ContentEvaluationRepositoryTest @Autowired constructor(
    val contentEvaluationRepository: ContentEvaluationRepository,
    val userInfoRepository: UserInfoRepository,
    val contentInfoRepository: ContentInfoRepository
) {

    private val logger = LoggerFactory.getLogger(ContentEvaluationRepositoryTest::class.java)

    /**
     * 사용자와 콘텐츠에 대한 평가 찾기 테스트
     */
    @Test
    fun `사용자와 콘텐츠에 대한 평가 찾기 테스트`() {
        val userInfo = userInfoRepository.save(UserInfo(userName = "테스트 사용자", userEmail = "test@example.com", gender = "M", type = "regular", regDate = Instant.now()))
        val content = contentInfoRepository.save(ContentInfo(contentName = "테스트 콘텐츠", author = "테스트 저자", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        val contentEvaluation = contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content, evaluationType = "like", regDate = LocalDate.now()))

        val foundEvaluation = contentEvaluationRepository.findByUserIdAndContentId(userInfo.id!!, content.id!!)
        assertNotNull(foundEvaluation)
        assertEquals(contentEvaluation.id, foundEvaluation?.id)
    }

    /**
     * 좋아요가 가장 많은 콘텐츠 조회 테스트
     */
    @Test
    fun `좋아요가 가장 많은 콘텐츠 조회 테스트`() {
        val userInfo = userInfoRepository.save(UserInfo(userName = "테스트 사용자", userEmail = "test@example.com", gender = "M", type = "regular", regDate = Instant.now()))
        val content1 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 1", author = "저자 1", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        val content2 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 2", author = "저자 2", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        val content3 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 3", author = "저자 3", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))

        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content1, evaluationType = "like", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content2, evaluationType = "like", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content3, evaluationType = "like", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content3, evaluationType = "like", regDate = LocalDate.now()))

        val topLikedContents = contentEvaluationRepository.findTopLikedContents()
        assertEquals(3, topLikedContents.size)
        assertEquals(content3.id, topLikedContents[0].id)
        assertEquals(content1.id, topLikedContents[1].id)
        assertEquals(content2.id, topLikedContents[2].id)
    }

    /**
     * 싫어요가 가장 많은 콘텐츠 조회 테스트
     */
    @Test
    fun `싫어요가 가장 많은 콘텐츠 조회 테스트`() {
        val userInfo = userInfoRepository.save(UserInfo(userName = "테스트 사용자", userEmail = "test@example.com", gender = "M", type = "regular", regDate = Instant.now()))
        val content1 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 1", author = "저자 1", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        val content2 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 2", author = "저자 2", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        val content3 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 3", author = "저자 3", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))

        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content1, evaluationType = "dislike", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content2, evaluationType = "dislike", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content3, evaluationType = "dislike", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content3, evaluationType = "dislike", regDate = LocalDate.now()))

        val topDislikedContents = contentEvaluationRepository.findTopDislikedContents()
        assertEquals(3, topDislikedContents.size)
        assertEquals(content3.id, topDislikedContents[0].id)
        assertEquals(content1.id, topDislikedContents[1].id)
        assertEquals(content2.id, topDislikedContents[2].id)
    }

    /**
     * 특정 사용자의 모든 평가 삭제 테스트
     */
    @Test
    fun `특정 사용자의 모든 평가 삭제 테스트`() {
        val userInfo = userInfoRepository.save(UserInfo(userName = "테스트 사용자", userEmail = "test@example.com", gender = "M", type = "regular", regDate = Instant.now()))
        val content1 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 1", author = "저자 1", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        val content2 = contentInfoRepository.save(ContentInfo(contentName = "콘텐츠 2", author = "저자 2", openDate = Instant.now(), regDate = Instant.now(), price = 100, isAdult = false))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content1, evaluationType = "like", regDate = LocalDate.now()))
        contentEvaluationRepository.save(ContentEvaluation(userInfo = userInfo, content = content2, evaluationType = "dislike", regDate = LocalDate.now()))

        contentEvaluationRepository.deleteByUserId(userInfo.id!!)

        val evaluationsForUser = contentEvaluationRepository.findByUserIdAndContentId(userInfo.id!!, content1.id!!)
        assertNull(evaluationsForUser)

        val evaluationsForUser2 = contentEvaluationRepository.findByUserIdAndContentId(userInfo.id!!, content2.id!!)
        assertNull(evaluationsForUser2)
    }
}
