package com.lezhin.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "content_view_history")
class ContentViewHistory(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    var content: ContentInfo,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var userInfo: UserInfo,

    @Column(name = "view_date", nullable = false)
    var viewDate: LocalDate = LocalDate.now(),

    @Column(name = "reg_date", nullable = false)
    var regDate: LocalDate = LocalDate.now(),

    @Column(name = "is_adult", nullable = false)
    var isAdult: Boolean
) {
    constructor() : this(
        id = null,
        content = ContentInfo(),
        userInfo = UserInfo(),
        viewDate = LocalDate.now(),
        regDate = LocalDate.now(),
        isAdult = false
    )
}
