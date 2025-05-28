前端專案 請見另個 Vue 專案倉庫
# Seat Management System

本專案為一套「人資部門用的員工座位安排系統」，後端使用 Spring Boot + JPA，資料庫為 SQL Server，前端使用 Vue.js，實作了完整的 RESTful API。

## 專案技術

- Java 17
- Spring Boot 3.2.5
- Spring Data JPA
- SQL Server（JDBC 驅動：mssql-jdbc）
- Bean Validation
- Vue.js（請見對應前端專案）
- Lombok（簡化 Java 程式碼）
- Git + GitHub（版本控制）

## 專案結構

seatmanagement/
├── src/
│ ├── main/
│ │ ├── java/com/example/seatmanagement/
│ │ │ ├── controller/ # REST API 控制器
│ │ │ ├── service/ # 商業邏輯層
│ │ │ ├── repository/ # 資料存取層（JPA Repository）
│ │ │ └── entity/ # JPA Entity 類別
│ └── resources/
│ └── application.yml # 資料庫與環境設定
├── pom.xml # Maven 專案設定檔
└── README.md # 說明文件

1. 下載專案後，先安裝 Maven 相依套件：

```bash
mvn clean install

2.設定 application.yml：
spring:
  datasource:
    url: jdbc:sqlserver://localhost:1433;databaseName=seatdb
    username: your_username
    password: your_password
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

3.啟動程式
mvn spring-boot:run

4.預設 API 路徑為：
http://localhost:8080/api/seats
