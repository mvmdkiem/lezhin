package com.lezhin.repository

import com.lezhin.domain.UserInfo
import org.springframework.data.jpa.repository.JpaRepository

interface UserInfoRepository : JpaRepository<UserInfo, Long>
