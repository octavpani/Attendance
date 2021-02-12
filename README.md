## 使い方
 - Eclispeのワークスペースフォルダの下でgit clone
 - EclipseのメニューからFile→Import→Existing Maven Projects
 - EclipseのPackage Explorerでプロジェクトを右クリックしてMaven→Update project
 - EclipseのPackage Explorerでプロジェクトを右クリックしてRun As(またはDebug As)→Spring Boot App
 - ブラウザでhttp://localhost:8080/　を開いてください。
## 内容
 - Attendance　勤怠のデータになります。西暦、月、日、ユーザー名をフィールドとして持っています。
 - SiteUser ユーザーの情報になります。AdminとSiteUserが役割としてあり、役割により表示画面が異なります（後述）。
 名前、パスワード、アバターとしての画像をフィールドとして持っています。
## Attendance
| HTTPメソッド | URL | Controllerメソッド | テンプレート |
| ------------- | ------------- | ------------- | ------------- |
| 内容セル | /attendance/list | 内容セル | 内容セル |
| 内容セル | /admin/attendance/list | 内容セル | 内容セル |
| 内容セル | /attendance | 内容セル | 内容セル |
| 内容セル | /attendance | 内容セル | 内容セル |
| 内容セル | /attendance/{id} | 内容セル | 内容セル |
| 内容セル | /form/attendances | 内容セル | 内容セル |
| 内容セル | /form/attendacnes/edit | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
## SiteUser
| 内容セル | 内容セル | 内容セル | 内容セル |
| ------------- | ------------- | ------------- | ------------- |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
| 内容セル | 内容セル | 内容セル | 内容セル |
## 認証
## テスト
