package com.lezhin.service.impl

import com.lezhin.controller.dto.ContentEvaluationDto
import com.lezhin.domain.ContentInfo
import com.lezhin.domain.ContentEvaluation
import com.lezhin.repository.ContentInfoRepository
import com.lezhin.repository.ContentEvaluationRepository
import com.lezhin.repository.UserInfoRepository
import com.lezhin.service.ContentEvaluationService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class ContentEvaluationServiceImpl(
    private val contentEvaluationRepository: ContentEvaluationRepository,
    private val userInfoRepository: UserInfoRepository,
    private val contentInfoRepository: ContentInfoRepository
) : ContentEvaluationService {

    /**
     * 좋아요와 싫어요가 많은 콘텐츠 목록을 조회합니다.
     *
     * @return 좋아요와 싫어요가 많은 콘텐츠 목록을 포함하는 맵
     */
    @Transactional(readOnly = true)
    override fun getContentRank(): Map<String, List<ContentInfo>> {
        return try {
            val topLiked = contentEvaluationRepository.findTopLikedContents()
            val topDisliked = contentEvaluationRepository.findTopDislikedContents()
            mapOf("like" to topLiked, "dislike" to topDisliked)
        } catch (ex: Exception) {
            println("콘텐츠 랭킹 조회 중 오류 발생: ${ex.message}")
            emptyMap()
        }
    }

    /**
     * 사용자가 특정 콘텐츠에 대해 평가를 추가합니다.
     *
     * @param contentEvaluationDto 평가 정보가 담긴 DTO
     * @return 추가된 평가 정보
     */
    @Transactional
    override fun addContentEvaluate(contentEvaluationDto: ContentEvaluationDto): ContentEvaluation {
        val user = try {
            userInfoRepository.findById(contentEvaluationDto.userId)
                .orElseThrow { IllegalArgumentException("해당 ID의 사용자가 존재하지 않습니다.") }
        } catch (ex: Exception) {
            println("사용자 조회 중 오류 발생: ${ex.message}")
            throw ex
        }

        val content = try {
            contentInfoRepository.findById(contentEvaluationDto.contentId)
                .orElseThrow { IllegalArgumentException("해당 ID의 작품이 존재하지 않습니다.") }
        } catch (ex: Exception) {
            println("콘텐츠 조회 중 오류 발생: ${ex.message}")
            throw ex
        }

        // '좋아요/싫어요' 필수값 검증
        if (contentEvaluationDto.evaluationType.isEmpty()) {
            throw IllegalArgumentException("좋아요/싫어요는 필수 입력값입니다.")
        }

        // 댓글 특수문자 검증
        if (contentEvaluationDto.comment != null && contentEvaluationDto.comment.contains(Regex("[^a-zA-Z0-9가-힣\\s]"))) {
            throw IllegalArgumentException("댓글에 특수문자를 사용할 수 없습니다.")
        }

        val existingEvaluation = try {
            contentEvaluationRepository.findByUserInfoIdAndContentId(contentEvaluationDto.userId, contentEvaluationDto.contentId)
        } catch (ex: Exception) {
            println("기존 평가 조회 중 오류 발생: ${ex.message}")
            null
        }

        val contentEvaluation = existingEvaluation ?: ContentEvaluation(userInfo = user, content = content, evaluationType = contentEvaluationDto.evaluationType, regDate = LocalDate.now())

        if (existingEvaluation != null) {
            contentEvaluation.evaluationType = contentEvaluationDto.evaluationType
            contentEvaluation.comment = contentEvaluationDto.comment
        }

        return try {
            contentEvaluationRepository.save(contentEvaluation)
        } catch (ex: Exception) {
            println("콘텐츠 평가 저장 중 오류 발생: ${ex.message}")
            throw ex
        }
    }
}
