package com.lezhin.controller

import com.lezhin.service.UserInfoService
import com.lezhin.controller.dto.SingleResultDto
import com.lezhin.util.ResponseUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserInfoController(private val userInfoService: UserInfoService) {

    private val logger: Logger = LoggerFactory.getLogger(UserInfoController::class.java)

    /**
     * 특정 사용자를 삭제하고 관련된 평가 및 조회 이력도 함께 삭제하는 메서드
     *
     * @param userId 삭제할 사용자의 ID
     * @return 성공 메시지 또는 오류 메시지를 포함한 SingleResultDto 객체
     */
    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable userId: Long): SingleResultDto<String> {
        return try {
            userInfoService.deleteUser(userId)
            ResponseUtils.success("사용자와 관련된 모든 데이터가 성공적으로 삭제되었습니다")
        } catch (ex: Exception) {
            logger.error("사용자 삭제 중 오류 발생: ", ex)
            ResponseUtils.error("사용자 삭제 중 오류 발생: ${ex.message}")
        }
    }
}
