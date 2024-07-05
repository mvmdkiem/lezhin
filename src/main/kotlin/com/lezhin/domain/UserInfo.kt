package com.lezhin.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "user_info")
class UserInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "user_name", nullable = false, length = 50)
    var userName: String,

    @Column(name = "user_email", nullable = false, length = 100)
    var userEmail: String,

    @Column(name = "gender", nullable = false, length = 10)
    var gender: String,

    @Column(name = "type", nullable = false, length = 10)
    var type: String,

    @Column(name = "reg_date", nullable = false)
    var regDate: Instant = Instant.now()
) {
    constructor() : this(
        id = null,
        userName = "",
        userEmail = "",
        gender = "",
        type = "",
        regDate = Instant.now()
    )
}
