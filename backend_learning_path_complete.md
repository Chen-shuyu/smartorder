# 五年後端工程師學習路徑規劃

## 一、總體策略（原則）
1. 「**做中學**」：每個技術都要配合小專案或公司任務實戰。  
2. 「**可驗證的里程碑**」：每 3 個月、6 個月、1 年設可量化成果。  
3. 「**先打基礎再深究**」：先確實掌握 Spring Boot & JPA + 測試，再往 CI/CD、分散式、架構。  
4. 「**以問題為導向學習**」：挑公司真實痛點（效能、維護性、部署耗時）作為學習任務。  

---

## 二、五年時間軸（總覽）
- **0–6 個月**：Spring Boot、JPA、測試；完成小型 API 專案。  
- **6–12 個月**：Stored Procedure 改寫、Spring Batch、Docker、Jenkins。  
- **12–24 個月**：ORM 調優、完整 CI/CD pipeline。  
- **24–36 個月**：分散式系統（Kafka、Redis）、監控（Prometheus、Grafana）。  
- **36–60 個月**：系統架構設計、帶領團隊、跨服務設計。  

---

## 三、細項技能（每項先後順序、重點、練習、檢核）

### A. 後端 API 開發（Spring Boot & JPA）
#### 先後順序
1. Spring Boot 基礎（注入、配置、Profiles）  
2. RESTful 設計、Controller → DTO → Service → Repository  
3. JPA 基礎 mapping（Entity、關聯）、Transaction 管理  
4. 進階：Lazy/Eager、N+1、fetch join、批量操作  
5. 動態查詢：Criteria API / QueryDSL / Specification  
6. 測試：JUnit + Spring Test / MockMvc / Testcontainers  
7. 安全：Spring Security + JWT  

#### 重點
- 分層清楚（Entity ≠ DTO）  
- 查詢成本意識（index、join、欄位選擇）  
- Transaction 邊界應在 Service 層  
- 測試覆蓋率關鍵路徑 >70%  

#### 練習
- 建 CRUD microservice → 增加關聯與查詢 → 解 N+1 → 複雜查詢 + 測試  

#### 檢核
- 能看懂 EXPLAIN  
- 測試能正確驗證 DB 行為  

---

### B. Stored Procedure 改寫成 Java
#### 先後順序
1. 分析 SP（輸入、輸出、邏輯）  
2. 拆成查詢 vs 商業邏輯  
3. 用 View / Native Query 過渡  
4. 改寫為 Service 或 Spring Batch  
5. 驗證正確性與效能  

#### 重點
- **逐步遷移**：不要一次砍光  
- **可測試**：寫成純 Java method  
- **效能**：必要時保留 SQL or 批次  
- **Rollback**：DB migration 管控  

#### 練習
- 找一支報表 SP → 寫 baseline 測試 → Java 改寫 → 比對輸出  
- 大量資料 → 用 Spring Batch  

#### 檢核
- 結果一致  
- 性能不低於原 SP  

---

### C. CI/CD（Jenkins + Docker + Kubernetes）
#### 先後順序
1. Dockerfile（multi-stage build）  
2. Jenkinsfile（CI build, test, artifact）  
3. CI build → Docker image → Push registry  
4. k8s 基礎（Pod/Deployment/Service/ConfigMap/Secret）  
5. 部署到 Minikube/Kind → 再上雲  
6. Helm, rolling update, autoscaling  
7. Prometheus/Grafana 監控  

#### 重點
- Jenkinsfile 可重現  
- 部署使用版本化 image  
- liveness/readiness probe 正確  
- Rollback & Canary 有演練  

#### 練習
- CRUD 專案 → Docker 化  
- Jenkinsfile → CI build + test + push  
- Minikube 部署 → rolling update + rollback  

#### 檢核
- Commit → Staging 自動化  
- Dashboard 可顯示延遲與錯誤率  

---

### D. 職涯成長（Junior → Senior → Architect）
#### Junior → Senior
- 技術深度：熟練 Java 生態（Spring/JPA/測試/自動化）  
- 軟技能：Code Review、提出改善方案  
- 系統設計：熟悉分層架構、微服務模式  
- 效能優化：DB index、cache、JVM tuning  

#### Senior → Architect
- 系統思維：跨服務、資料一致性策略  
- 系統設計：高可用、水平擴充、分散式快取、API Gateway  
- 領導：Mentor、Tech Sharing  
- 協作：PM / 前端 / 運維整合  

#### 檢核
- Senior：能主導 feature，解決 P1 bug，影響團隊  
- Architect：領導跨團隊架構專案，交付可量化成效  

---

## 四、第一個月學習課表（試行版）

### 每日 Checklist（30 天）

#### 第 1 週（專案初始化 + User CRUD）
- **Day 1**：Spring Initializr 建立專案，設定依賴（Web, JPA, Lombok, H2/Postgres）  
- **Day 2**：User Entity + Repository  
- **Day 3**：User DTO + Controller (Create)  
- **Day 4**：完成 Read API  
- **Day 5**：完成 Update/Delete API  
- **Day 6 (假日)**：寫 UserService 測試  
- **Day 7 (假日)**：寫 UserController Integration Test  

#### 第 2 週（Order + 關聯 + N+1）
- **Day 8**：Order Entity + Repository  
- **Day 9**：Order CRUD API  
- **Day 10**：User-Order OneToMany 關聯  
- **Day 11**：新增「查詢 User 的所有 Order」API  
- **Day 12**：Lazy vs Eager，觀察 N+1  
- **Day 13 (假日)**：fetch join / @EntityGraph 解決 N+1  
- **Day 14 (假日)**：Order API Integration Test  

#### 第 3 週（複雜查詢 + 測試 + 錯誤處理）
- **Day 15**：設計複雜查詢（例：某用戶區間內訂單）  
- **Day 16**：用 Criteria API / Specification 完成查詢  
- **Day 17**：Testcontainers 驗證查詢  
- **Day 18**：寫 Repository 測試  
- **Day 19**：全域例外處理（@RestControllerAdvice）  
- **Day 20 (假日)**：Service 層測試  
- **Day 21 (假日)**：補充 Controller 測試  

#### 第 4 週（整合與檢核）
- **Day 22**：重構專案結構  
- **Day 23**：檢查並提升測試覆蓋率（>70%）  
- **Day 24**：撰寫 README 說明專案與學習重點  
- **Day 25**：用 Postman/curl 測試 API  
- **Day 26**：小流量壓測（JMeter/ab）  
- **Day 27 (假日)**：Code Review & Refactor  
- **Day 28 (假日)**：寫學習筆記  
- **Day 29**：整理成果，確認 milestone  
- **Day 30**：休息 / buffer / 下月規劃  

---

## 五、實戰專案（建議，有可測量目標）

### 專案 A：User/Order Microservice（0–3 個月）
- 技術：Spring Boot, JPA, Postgres, JUnit, Testcontainers  
- 目標：10 個 REST API、測試覆蓋率 >70%、無 N+1  

### 專案 B：SP 改寫報表（3–6 個月）
- 技術：Spring Batch, Repository/Native Query  
- 目標：改寫 1 支每日報表 SP，測試驗證一致性，部署成定時 Job  

### 專案 C：CI/CD Pipeline + K8s（6–12 個月）
- 技術：Jenkinsfile, Docker, Kubernetes, Prometheus  
- 目標：Commit → Staging 自動化部署，能做 rolling update & rollback  

### 專案 D：分散式實驗（12–36 個月）
- 技術：Kafka/Redis, Service Mesh 概念, Tracing (OpenTelemetry)  
- 目標：建小型事件流服務，能在高併發下測試可觀察性  

---

## 六、第一個月檢核
- 能正確建立 Spring Boot REST API 專案  
- Entity 關聯正確（避免 N+1）  
- 測試可重現 DB 行為（Testcontainers/H2）  
- README 說明成果  
- 測試覆蓋率 >70%  

---

## 七、後續計畫（第 2 個月開始）
- 開始挑 Stored Procedure 改寫為 Java Service  
- 引入 Docker 化服務並嘗試在本地部署  
