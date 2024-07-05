package com.lezhin.repository

import com.lezhin.domain.ContentInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ContentInfoRepository : JpaRepository<ContentInfo, Long>
