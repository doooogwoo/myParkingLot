# 🅿️ MyParkingLot — 模擬經營型智慧停車場系統

> 一個以 Java + Spring Boot 架構的模擬遊戲專案，從 0 到 1 打造具「玩家經營邏輯」、「即時狀態變化」與「報表統計」功能的模擬停車場管理系統。

---

## 🌟 專案亮點

- ✅ 支援**玩家創建與管理停車場**
- ✅ 模擬**車輛進出場流程與費用計算**
- ✅ 使用 **Command Pattern** 封裝操作指令
- ✅ 搭配 **WebSocket 推播**即時場地狀態
- ✅ 提供**週報表統計模組**
- ✅ 支援多種車輛與特殊車格（身障／電動）

---

## 🔧 技術棧

| 層級 | 技術 | 說明 |
|------|------|------|
| Backend | Spring Boot | 主體框架 |
| API | Spring Web | RESTful API |
| 即時推播 | WebSocket + STOMP | 推播車輛狀態與通知 |
| 定時任務 | Spring Scheduling | 模擬車輛排程進出 |
| 資料層 | Spring Data JPA | ORM 對應資料庫 |
| 架構思維 | Command Pattern | 指令佇列執行邏輯 |

---

## 📁 資料夾架構

```bash
Damo/
├── Controller/           # REST API 控制器
├── domain/Model/         # 核心實體（ParkingLot, Vehicle, Player 等）
├── Service/
│   ├── factory/          # 車輛與場地生成器
│   ├── orchestrator/     # 業務流程協調器
│   ├── Command/          # 指令模式管理車輛操作
│   ├── websocket/        # 推播服務
├── Config/               # 跨域與 WebSocket 設定
├── Exception/            # 全域例外與錯誤碼處理
```

## 🚗 常用 API

| 功能 | 方法 | 範例 |
|------|------|------|
| 新增停車場 | \`POST\` | \`/api/parkingLot/add-parkingLot?parkingLotName=A&ticketFee=50\` |
| 模擬車輛進場 | \`POST\` | \`/api/commands/enter\` |
| 執行所有進場指令 | \`POST\` | \`/api/commands/runAll\` |
| 查詢場地資訊 | \`GET\` | \`/api/parkingLot/getInfoByLocationLotId/L1\` |
| 查詢地圖資料 | \`GET\` | \`/api/parkingLot/getAllLocationInfo\` |

---

## 📈 模擬邏輯與策略

- **車輛進場流程**
  - 隨機生成車輛 → 指派車位 → 開始計時
- **離場與收費**
  - 根據車輛類型與停車時間計算費用
  - 將收入統計進停車場
- **報表系統**
  - 每週統計：總收入 / 使用率 / 特殊車格使用情況

---

## 🔍 設計模式應用（Command Pattern）

- 每個車輛進場動作皆封裝為 \`EnterVehicleCommand\`
- 由 \`CommandManager\` 管理與執行（支援佇列、批次、單次執行）
- 可延伸為 **撤銷/重播指令、事件紀錄回放**

---

## 🧠 學習與實作心得

這個專案是我為了整合後端技術而設計的模擬系統，目的在於：

- 訓練模組化思維與清晰職責劃分
- 練習設計模式在實務開發中的應用
- 練習從遊戲邏輯轉譯為後端架構的能力

---

## 📌 待辦與擴充計畫

- [ ] 整合前端（React CLI 模擬畫面）
- [ ] 加入使用者登入／玩家資金系統（Spring Security + JWT）
- [ ] 製作每日／每週自動報表 PDF 匯出
- [ ] 加入 Redis 緩存支援高頻資料查詢

---

## 🙋‍♂️ 作者資訊

由台灣全端工程師自學製作，專注於 Java / Spring Boot 架構，具備社會學背景，善於跨領域系統設計與教學轉譯。  
如果你對這個模擬系統有興趣，歡迎交流！
