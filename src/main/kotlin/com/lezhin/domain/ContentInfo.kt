package com.lezhin.domain

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "content_info")
class ContentInfo(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "content_name", nullable = false, length = 100)
    var contentName: String,

    @Column(name = "author", nullable = false, length = 50)
    var author: String,

    @Column(name = "open_date")
    var openDate: Instant? = null,

    @Column(name = "reg_date", nullable = false)
    var regDate: Instant,

    @Column(name = "price", nullable = false)
    var price: Int,

    @Column(name = "is_adult", nullable = false)
    var isAdult: Boolean
) {
    constructor() : this(
        id = null,
        contentName = "",
        author = "",
        openDate = null,
        regDate = Instant.now(),
        price = 0,
        isAdult = false
    )
}
