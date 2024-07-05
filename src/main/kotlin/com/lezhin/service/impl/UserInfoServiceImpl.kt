package com.lezhin.service.impl

import com.lezhin.repository.ContentEvaluationRepository
import com.lezhin.repository.UserInfoRepository
import com.lezhin.repository.ContentViewHistoryRepository
import com.lezhin.service.UserInfoService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserInfoServiceImpl(
    private val userInfoRepository: UserInfoRepository,
    private val contentEvaluationRepository: ContentEvaluationRepository,
    private val contentViewHistoryRepository: ContentViewHistoryRepository
) : UserInfoService {

    @Transactional(rollbackFor = [Exception::class])
    override fun deleteUser(userId: Long) {
        try {
            // 관련된 평가 삭제
            contentEvaluationRepository.deleteByUserInfoId(userId)

            // 관련된 조회 이력 삭제
            contentViewHistoryRepository.deleteByUserInfoId(userId)

            // 사용자 정보 삭제
            userInfoRepository.deleteById(userId)
        } catch (ex: Exception) {
            throw ex
        }
    }
}
