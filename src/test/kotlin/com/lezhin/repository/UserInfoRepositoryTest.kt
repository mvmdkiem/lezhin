package com.lezhin.repository

import com.lezhin.domain.UserInfo
import org.junit.jupiter.api.Assertions.*
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
class UserInfoRepositoryTest @Autowired constructor(
    val userInfoRepository: UserInfoRepository
) {

    private val logger = LoggerFactory.getLogger(UserInfoRepositoryTest::class.java)

    /**
     * 사용자 저장 및 조회 테스트
     */
    @Test
    fun `사용자 저장 및 조회 테스트`() {
        val now = Instant.now()
        val userInfo = UserInfo(
            userName = "테스트 사용자",
            userEmail = "test@example.com",
            gender = "M",
            type = "regular",
            regDate = now
        )

        // 사용자 저장
        val savedUser = userInfoRepository.save(userInfo)
        logger.info("저장된 사용자: $savedUser")
        assertNotNull(savedUser.id)

        // 사용자 조회
        val foundUser = userInfoRepository.findById(savedUser.id!!)
        logger.info("조회된 사용자: $foundUser")
        assertNotNull(foundUser)

        // 검증
        assertEquals("테스트 사용자", foundUser.get().userName)
        assertEquals("test@example.com", foundUser.get().userEmail)
        assertEquals("M", foundUser.get().gender)
        assertEquals("regular", foundUser.get().type)
        assertEquals(now, foundUser.get().regDate)
    }

    /**
     * 사용자 삭제 테스트
     */
    @Test
    fun `사용자 삭제 테스트`() {
        val now = Instant.now()
        val userInfo = UserInfo(
            userName = "삭제 테스트 사용자",
            userEmail = "delete@example.com",
            gender = "F",
            type = "temporary",
            regDate = now
        )

        // 사용자 저장
        val savedUser = userInfoRepository.save(userInfo)
        logger.info("저장된 사용자: $savedUser")
        assertNotNull(savedUser.id)

        // 사용자 삭제
        userInfoRepository.deleteById(savedUser.id!!)
        logger.info("사용자 삭제 완료: ID = ${savedUser.id}")

        // 사용자 조회
        val foundUser = userInfoRepository.findById(savedUser.id!!)
        logger.info("조회된 사용자: $foundUser")
        assertTrue(foundUser.isEmpty)
    }

    /**
     * 모든 사용자 조회 테스트
     */
    @Test
    fun `모든 사용자 조회 테스트`() {
        val now = Instant.now()
        val userInfo1 = UserInfo(
            userName = "사용자 1",
            userEmail = "user1@example.com",
            gender = "M",
            type = "regular",
            regDate = now
        )
        val userInfo2 = UserInfo(
            userName = "사용자 2",
            userEmail = "user2@example.com",
            gender = "F",
            type = "regular",
            regDate = now
        )

        // 사용자 저장
        userInfoRepository.save(userInfo1)
        userInfoRepository.save(userInfo2)

        // 모든 사용자 조회
        val users = userInfoRepository.findAll()
        logger.info("모든 사용자: $users")
        assertEquals(2, users.size)
    }
}
