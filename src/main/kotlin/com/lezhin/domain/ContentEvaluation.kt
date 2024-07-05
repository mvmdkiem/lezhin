package com.lezhin.domain

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "content_evaluation")
class ContentEvaluation(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    var userInfo: UserInfo,

    @ManyToOne
    @JoinColumn(name = "content_id", nullable = false)
    var content: ContentInfo,

    @Column(name = "evaluation_type", nullable = false, length = 10)
    var evaluationType: String,

    @Column(name = "comment", nullable = true, length = 255)
    var comment: String? = null,

    @Column(name = "reg_date", nullable = false)
    var regDate: LocalDate = LocalDate.now()
) {
    constructor() : this(
        id = null,
        userInfo = UserInfo(),
        content = ContentInfo(),
        evaluationType = "",
        comment = null,
        regDate = LocalDate.now()
    )
}
