## Overview
이 프로젝트는 Lezhin 회사에서 개발한 웹 애플리케이션의 백엔드 부분입니다. 주로 웹툰 및 콘텐츠 관리를 위한 서비스를 제공합니다. 주요 기능은 다음과 같습니다:

- **콘텐츠 관리**: 콘텐츠의 가격을 변경하거나 콘텐츠 정보를 조회하고 관리합니다.

- **평가 관리**: 사용자가 콘텐츠에 대해 좋아요 또는 싫어요 평가를 추가하고, 인기 있는 콘텐츠를 조회할 수 있습니다.

- **사용자 관리**: 사용자를 등록하고, 삭제하며, 사용자의 관련 데이터를 함께 관리합니다.

- **조회 이력 관리**: 콘텐츠의 조회 이력을 기록하고, 특정 콘텐츠의 조회 이력을 조회하거나 성인 콘텐츠 조회 이력을 관리합니다.

- **응답 유틸리티**: API 응답을 표준화하여 클라이언트에 일관된 형태의 데이터를 제공합니다.

## 개발 언어

- **Kotlin**

## RDBMS

- **MySQL**

## 주요 라이브러리 및 도구

- **Spring Boot**

   - **Spring Web**: RESTful API를 개발하기 위해 사용되었습니다.

   - **Spring Data JPA**: 데이터베이스 접근을 위해 사용되었습니다.

   - **Spring Test**: 단위 테스트와 통합 테스트를 위해 사용되었습니다.

- **Mockito**: 단위 테스트에서 목 객체(mock object)를 생성하기 위해 사용되었습니다.

- **JUnit 5**: 테스트 프레임워크로 사용되었습니다.

- **SLF4J 및 Logback**: 로깅을 위해 사용되었습니다.

## 주요 패키지 및 클래스
| 패키지 | 클래스명                              | 설명                                                                                            |
| ------ |-----------------------------------|-----------------------------------------------------------------------------------------------|
| com.lezhin.controller.dto | ContentInfoDto                    | 콘텐츠의 가격 정보를 담은 데이터 클래스                                                                        |
|  | ContentEvaluationDto              | 평가 정보를 담은 데이터 클래스 (사용자 ID, 콘텐츠 ID, 평가 유형, 댓글 포함)                                              |
|  | MultiResultDto                    | 여러 결과 데이터를 담는 데이터 클래스 (결과 코드, 메시지, 결과 개수, 데이터 컬렉션 포함)                                         |
|  | SingleResultDto                   | 단일 결과 데이터를 담는 데이터 클래스 (결과 코드, 메시지, 데이터 포함)                                                    |
| com.lezhin.controller | ContentInfoController             | 콘텐츠 관련 API를 제공하는 컨트롤러 (콘텐츠 가격 변경)                                                             |
|  | ContentEvaluationController       | 평가 관련 API를 제공하는 컨트롤러 (평가 추가, 인기 콘텐츠 조회)                                                       |
|  | UserInfoController                | 사용자 관련 API를 제공하는 컨트롤러 (사용자 삭제)                                                                |
|  | ContentViewHistoryController      | 조회 이력 관련 API를 제공하는 컨트롤러 (조회 이력 조회, 성인 콘텐츠 조회 사용자 목록 조회)                                       |
| com.lezhin.domain | ContentInfo                       | 콘텐츠 정보를 담은 엔티티 클래스 (콘텐츠 이름, 작가, 개봉일, 등록일, 가격, 성인 여부 포함)                                       |
|  | ContentEvaluation                 | 평가 정보를 담은 엔티티 클래스 (사용자, 콘텐츠, 평가 유형, 댓글, 등록일 포함)                                               |
|  | UserInfo                          | 사용자 정보를 담은 엔티티 클래스 (사용자 이름, 이메일, 성별, 유형, 등록일 포함)                                              |
|  | ContentViewHistory                | 조회 이력 정보를 담은 엔티티 클래스 (콘텐츠, 사용자, 조회일, 등록일, 성인 여부 포함)                                           |
| com.lezhin.repository | ContentInfoRepository             | 콘텐츠 정보를 관리하는 JPA 리포지토리 인터페이스                                                                  |
|  | ContentEvaluationRepository       | 평가 정보를 관리하는 JPA 리포지토리 인터페이스 (특정 사용자 및 콘텐츠에 대한 평가 조회, 좋아요/싫어요 많은 콘텐츠 조회, 사용자 평가 삭제)            |
|  | UserInfoRepository                | 사용자 정보를 관리하는 JPA 리포지토리 인터페이스                                                                  |
|  | ContentViewHistoryRepository      | 조회 이력 정보를 관리하는 JPA 리포지토리 인터페이스 (특정 콘텐츠 및 사용자의 조회 이력 조회, 성인 콘텐츠 조회 사용자 ID 목록 조회, 사용자 조회 이력 삭제) |
| com.lezhin.service.impl | ContentInfoServiceImpl            | 콘텐츠 서비스 구현 클래스 (콘텐츠 가격 업데이트)                                                                  |
|  | ContentEvaluationServiceImpl      | 평가 서비스 구현 클래스 (콘텐츠 랭킹 조회, 평가 추가)                                                              |
|  | UserInfoServiceImpl               | 사용자 서비스 구현 클래스 (사용자 삭제)                                                                       |
|  | ContentViewHistoryServiceImpl     | 조회 이력 서비스 구현 클래스 (조회 이력 추가, 조회 이력 목록 조회, 성인 콘텐츠 조회 사용자 목록 조회, 사용자 조회 이력 삭제)                   |
| com.lezhin.service | ContentInfoService                | 콘텐츠 서비스 인터페이스 (콘텐츠 가격 업데이트)                                                                   |
|  | ContentEvaluationService          | 평가 서비스 인터페이스 (콘텐츠 랭킹 조회, 평가 추가)                                                               |
|  | UserInfoService                   | 사용자 서비스 인터페이스 (사용자 삭제)                                                                        |
|  | ContentViewHistoryService         | 조회 이력 서비스 인터페이스 (조회 이력 추가, 조회 이력 목록 조회, 성인 콘텐츠 조회 사용자 목록 조회, 사용자 조회 이력 삭제)                    |
| com.lezhin.util | ResponseUtils                     | 응답 유틸리티 클래스 (성공 및 오류 응답 생성)                                                                   |
| com.lezhin.controller | ContentInfoControllerTest         | ContentInfoController의 테스트 케이스                                                                |
|  | ContentEvaluationControllerTest   | ContentEvaluationController의 테스트 케이스                                                          |
|  | UserInfoControllerTest            | UserInfoController의 테스트 케이스                                                                   |
|  | ContentViewHistoryControllerTest  | ContentViewHistoryController의 테스트 케이스                                                         |
| com.lezhin.repository | ContentInfoRepositoryTest         | ContentInfoRepository의 테스트 케이스                                                                |
|  | ContentEvaluationRepositoryTest   | ContentEvaluationRepository의 테스트 케이스                                                          |
|  | UserInfoRepositoryTest            | UserInfoRepository의 테스트 케이스                                                                   |
|  | ContentViewHistoryRepositoryTest  | ContentViewHistoryRepository의 테스트 케이스                                                         |
| com.lezhin.service.impl | ContentInfoServiceImplTest        | ContentInfoServiceImpl의 테스트 케이스                                                               |
|  | ContentEvaluationServiceImplTest  | ContentEvaluationServiceImpl의 테스트 케이스                                                         |
|  | UserInfoServiceImplTest           | UserInfoServiceImpl의 테스트 케이스                                                                  |
|  | ContentViewHistoryServiceImplTest | ContentViewHistoryServiceImpl의 테스트 케이스                                                        |

## API 정의서

### 가) 특정 사용자가 해당 작품에 대해 평가('좋아요/싫어요', '댓글') API
- **URL**: `/api/content/evaluation/{contentId}`
- **Method**: `POST`
- **설명**: 특정 사용자가 작품에 대해 평가를 추가합니다.
<pre>
<code>
curl -X POST http://localhost:8080/api/content/evaluation/{contentId} \
-H "Content-Type: application/json" \
-d '{
    "userId": 1,
    "contentId": 1,
    "evaluationType": "like",
    "comment": "좋은 작품입니다."
}'
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "평가가 성공적으로 저장되었습니다",
    "resultData": {
        "id": 1,
        "user": {
            "id": 1,
            "userName": "사용자 1",
            "userEmail": "user1@example.com",
            "gender": "M",
            "type": "regular",
            "regDate": "2023-07-01T00:00:00Z"
        },
        "content": {
            "id": 1,
            "contentName": "테스트 콘텐츠",
            "author": "작가",
            "openDate": "2023-07-01T00:00:00Z",
            "regDate": "2023-07-01T00:00:00Z",
            "price": 100,
            "isAdult": false
        },
        "evaluationType": "like",
        "comment": "좋은 작품입니다.",
        "regDate": "2023-07-01"
    }
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode" : "500",
    "resultMsg"  : "평가 저장 중 오류 발생: 댓글에 특수문자를 사용할 수 없습니다.",
    "resultData" : null
}
</code>
</pre>

### 나) '좋아요'가 가장 많은 작품 3개와 '싫어요'가 가장 많은 작품 3개씨 조회 API
- **URL**: `/api/content/rank`
- **Method**: `GET`
- **설명**: 좋아요와 싫어요가 많은 작품을 조회합니다.
<pre>
<code>
curl -X GET http://localhost:8080/api/content/rank \
-H "Content-Type: application/json"
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "좋아요와 싫어요가 많은 작품을 성공적으로 조회했습니다",
    "resultData": {
        "like": [
            {
                "id": 1,
                "contentName": "테스트 콘텐츠 1",
                "author": "작가 1",
                "openDate": "2023-07-01T00:00:00Z",
                "regDate": "2023-07-01T00:00:00Z",
                "price": 100,
                "isAdult": false
            }
        ],
        "dislike": [
            {
                "id": 2,
                "contentName": "테스트 콘텐츠 2",
                "author": "작가 2",
                "openDate": "2023-07-01T00:00:00Z",
                "regDate": "2023-07-01T00:00:00Z",
                "price": 200,
                "isAdult": false
            }
        ]
    }
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

### 다) 작품 별로 언제, 어떤 사용자가 조회했는지 이력을 조회 API
- **URL**: `/api/view/{contentId}`
- **Method**: `GET`
- **설명**: 특정 작품의 조회 이력을 조회합니다.
<pre>
<code>
curl -X GET http://localhost:8080/api/view/{contentId} \
-H "Content-Type: application/json"
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "작품 조회 이력을 성공적으로 조회했습니다",
    "resultData": [
        {
            "id": 1,
            "content": {
                "id": 1,
                "contentName": "테스트 콘텐츠",
                "author": "테스트 저자",
                "openDate": "2023-07-01T00:00:00Z",
                "regDate": "2023-07-01T00:00:00Z",
                "price": 100,
                "isAdult": false
            },
            "user": {
                "id": 1,
                "userName": "테스트 사용자",
                "userEmail": "test@example.com",
                "gender": "M",
                "type": "regular",
                "regDate": "2023-07-01T00:00:00Z"
            },
            "viewDate": "2023-07-01",
            "regDate": "2023-07-01",
            "isAdult": false
        }
    ]
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

### 라) 최근 1주일간 등록 사용자 중 성인 작품을 3개 이상 조회한 사용자 목록을 조회 API
- **URL**: `/api/view/adult`
- **Method**: `GET`
- **설명**: 최근 1주일간 성인 작품을 3개 이상 조회한 사용자 목록을 조회합니다.
<pre>
<code>
curl -X GET http://localhost:8080/api/view/adult \
-H "Content-Type: application/json"
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "성인 작품을 3개 이상 조회한 사용자 목록을 성공적으로 조회했습니다",
    "resultData": [
        {
            "id": 1,
            "userName": "테스트 사용자",
            "userEmail": "test@example.com",
            "gender": "M",
            "type": "regular",
            "regDate": "2023-07-01T00:00:00Z"
        }
    ]
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

### 마) 특정 작품의 가격 변경 API
- **URL**: `/api/content/price/{contentId}`
- **Method**: `PUT`
- **설명**: 특정 콘텐츠의 가격을 유료(100~500), 무료 (0) 로 변경 합니다.
<pre>
<code>
curl -X PUT http://localhost:8080/api/content/price/{contentId} \
-H "Content-Type: application/json" \
-d '{
    "price": 100
}'
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "작품 가격이 성공적으로 변경되었습니다",
    "resultData": {
        "id": 1,
        "contentName": "테스트 콘텐츠",
        "author": "테스트 작가",
        "openDate": "2023-07-01T00:00:00Z",
        "regDate": "2023-07-01T00:00:00Z",
        "price": 100,
        "isAdult": false
    }
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

### 바) 특정 사용자 삭제  API

- **URL**: `/api/users/{userId}`
- **Method**: `DELETE`
- **설명**: 특정 사용자를 삭제하고 관련된 평가 및 조회 이력도 함께 삭제합니다.
<pre>
<code>
curl -X DELETE http://localhost:8080/api/users/{userId} \
-H "Content-Type: application/json"
</code>
</pre>
Response: Success
<pre>
<code>
{
    "resultCode": "200",
    "resultMsg": "Success",
    "resultData": "사용자와 관련된 모든 데이터가 성공적으로 삭제되었습니다"
}
</code>
</pre>
Response: Error
<pre>
<code>
{
    "resultCode": "400",
    "resultMsg": "잘못된 인수입니다: [Error message]"
}
</code>
</pre>

## DB 설정

### 유저 정보 테이블 생성
<pre>
<code>
CREATE TABLE `user_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(50) NOT NULL,
  `user_email` VARCHAR(100) NOT NULL,
  `gender` VARCHAR(10) NOT NULL,
  `type` VARCHAR(10) NOT NULL,
  `reg_date` DATETIME(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

</code>
</pre>
### 작품 정보 테이블 생성
<pre>
<code>
CREATE TABLE `content_info` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content_name` VARCHAR(100) NOT NULL,
  `author` VARCHAR(50) NOT NULL,
  `open_date` DATETIME DEFAULT NULL,
  `reg_date` DATETIME NOT NULL,
  `price` INT NOT NULL,
  `is_adult` TINYINT(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

</code>
</pre>
### 작품 평가 테이블 생성
<pre>
<code>
CREATE TABLE `content_evaluation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `evaluation_type` VARCHAR(10) NOT NULL,
  `reg_date` DATE NOT NULL,
  `comment` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_content_evaluation_content_id` (`content_id`),
  KEY `FK_content_evaluation_user_id` (`user_id`),
  CONSTRAINT `FK_content_evaluation_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FK_content_evaluation_content_id` FOREIGN KEY (`content_id`) REFERENCES `content_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

</code>
</pre>

### 작품 조회 이력 테이블 생성
<pre>
<code>
CREATE TABLE `content_evaluation` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `content_id` BIGINT NOT NULL,
  `user_id` BIGINT NOT NULL,
  `evaluation_type` VARCHAR(10) NOT NULL,
  `reg_date` DATE NOT NULL,
  `comment` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_content_evaluation_content_id` (`content_id`),
  KEY `FK_content_evaluation_user_id` (`user_id`),
  CONSTRAINT `FK_content_evaluation_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_info` (`id`),
  CONSTRAINT `FK_content_evaluation_content_id` FOREIGN KEY (`content_id`) REFERENCES `content_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
</code>
</pre>

### 유저 정보 샘플값 추가
<pre>
<code>
INSERT INTO `user_info` (`id`, `user_name`, `user_email`, `gender`, `type`, `reg_date`) VALUES
(1, '사용자 1', 'user1@example.com', 'M', 'regular', '2024-01-01'),
(2, '사용자 2', 'user2@example.com', 'F', 'premium', '2024-01-02'),
(3, '사용자 3', 'user3@example.com', 'M', 'regular', '2024-01-03'),
(4, '사용자 4', 'user4@example.com', 'F', 'premium', '2024-01-04'),
(5, '사용자 5', 'user5@example.com', 'M', 'regular', '2024-01-05');

</code>
</pre>

### 작품 정보 샘플값 추가
<pre>
<code>
INSERT INTO `content_info` (`id`, `content_name`, `author`, `open_date`, `reg_date`, `price`, `is_adult`) VALUES
(1, '콘텐츠 1', '작가 1', '2024-01-01', '2024-01-01', 100, 0),
(2, '콘텐츠 2', '작가 2', '2024-01-02', '2024-01-02', 200, 0),
(3, '콘텐츠 3', '작가 3', '2024-01-03', '2024-01-03', 300, 1),
(4, '콘텐츠 4', '작가 4', '2024-01-04', '2024-01-04', 400, 0),
(5, '콘텐츠 5', '작가 5', '2024-01-05', '2024-01-05', 500, 1),
(6, '콘텐츠 6', '작가 6', '2024-01-06', '2024-01-06', 100, 0),
(7, '콘텐츠 7', '작가 7', '2024-01-07', '2024-01-07', 200, 0),
(8, '콘텐츠 8', '작가 8', '2024-01-08', '2024-01-08', 300, 1),
(9, '콘텐츠 9', '작가 9', '2024-01-09', '2024-01-09', 400, 0),
(10, '콘텐츠 10', '작가 10', '2024-01-10', '2024-01-10', 500, 1);

</code>
</pre>

### 작품 평가 샘플값 추가
<pre>
<code>
INSERT INTO `content_evaluation` (`content_id`, `user_id`, `evaluation_type`, `reg_date`, `comment`) VALUES
(1, 1, 'like', '2024-02-01', '좋아요'),
(2, 1, 'like', '2024-02-02', '좋아요'),
(3, 1, 'dislike', '2024-02-03', '별로에요'),
(4, 1, 'like', '2024-02-04', '좋아요'),
(5, 1, 'dislike', '2024-02-05', '별로에요'),
(6, 1, 'like', '2024-02-06', '좋아요'),
(7, 1, 'dislike', '2024-02-07', '별로에요'),
(8, 1, 'like', '2024-02-08', '좋아요'),
(9, 1, 'dislike', '2024-02-09', '별로에요'),
(10, 1, 'like', '2024-02-10', '좋아요');
</code>
</pre>

### 작품 조회 이력 샘플값 추가
<pre>
<code>
INSERT INTO `content_view_history` (`content_id`, `user_id`, `view_date`, `reg_date`, `is_adult`) VALUES
(3, 1, '2024-07-06', '2024-07-06', 1),
(5, 1, '2024-07-06', '2024-07-06', 1),
(8, 1, '2024-07-06', '2024-07-06', 1),
(10, 1, '2024-07-06', '2024-07-06', 1),

(3, 2, '2024-07-06', '2024-07-06', 1),
(5, 2, '2024-07-06', '2024-07-06', 1),
(8, 2, '2024-07-06', '2024-07-06', 1),
(10, 2, '2024-07-06', '2024-07-06', 1),

(3, 3, '2024-07-06', '2024-07-06', 1),
(5, 3, '2024-07-06', '2024-07-06', 1),
(8, 3, '2024-07-06', '2024-07-06', 1),
(10, 3, '2024-07-06', '2024-07-06', 1),

(3, 4, '2024-07-06', '2024-07-06', 1),
(5, 4, '2024-07-06', '2024-07-06', 1),
(8, 4, '2024-07-06', '2024-07-06', 1),
(10, 4, '2024-07-06', '2024-07-06', 1),

(3, 5, '2024-07-06', '2024-07-06', 1),
(5, 5, '2024-07-06', '2024-07-06', 1),
(8, 5, '2024-07-06', '2024-07-06', 1),
(10, 5, '2024-07-06', '2024-07-06', 1);


</code>
</pre>