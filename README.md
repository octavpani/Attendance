# 勤怠管理アプリ

## 概要
Spring Boot及びjavaの基本的な使い方について学習しました。
そのアウトプットとして、勤怠管理のアプリを作成しました。

## 基本的な内容
 - Attendance(勤怠のデータ)
SiteUserが登録する事により、一覧表示をする事できます。  
 Adminロールの場合は、すべてのAttendanceを見る事ができますが、修正、削除ができません。  
 Userロールでログインした場合、一覧表示されてみる事ができるのは、自分の勤怠のみです。  
 - SiteUser
Roleで、AdminとUserに分かれています。Roleによって、表示内容が異なります。  
 Adminロールは、すべてのSiteUserを一覧表示できます。Attendanceと同様のCRUDをSiteUserに対して行えるイメージです。  
 Userロールは、新規登録画面を利用して、自分のデータを登録する事ができます。
## ポイント
 - WEBアプリの基本として、CRUDの実装
 - Thymeleafを利用した、Entityの一括の登録(Attendance及びSiteUser)
 - Spring Boot特有の支援を利用した機能の実装(勤怠情報のCSV形式での出力、SpringSecurityを利用した認証等)
## 使い方
 - Eclispeのワークスペースフォルダの下でgit clone
 - EclipseのメニューからFile→Import→Existing Maven Projects
 - EclipseのPackage Explorerでプロジェクトを右クリックしてMaven→Update project
 - EclipseのPackage Explorerでプロジェクトを右クリックしてRun As(またはDebug As)→Spring Boot App
 - ブラウザでhttp://localhost:8080/
## Attendance
| HTTPメソッド | URL | Controllerメソッド | テンプレート |
| ------------- | ------------- | ------------- | ------------- |
| GET | /attendance/list | showAttendanceList() | attendanceList.html |
| GET | /admin/attendance/list | showAttendanceListForAdmin() | attendanceList.html |
| GET | /attendance | createAttendance() | attendanceForm.html |
| POST | /attendance | createAttendance() | 成功時は /attendance/listへリダイレクト。失敗時は、attendanceForm.html|
| GET | /form/pre/attendances | prepareAttendancesForm() | preAttendancesForm.html |
| GET | /form/attendances | createAttendances() | attendancesForm.html |
| POST | /form/attendances | createAttendances() | 成功時は/attendance/listへリダイレクト。失敗時は、attendancesForm.html |
| POST | /form/attendacnes/edit | editAttendances() | attendancesForm.html |
| POST | /attendances/update | updateAttendances() | 成功時は、/attendance/listへリダイレクト。失敗時は、attendancesForm.html
| POST | /attendances/delete | deleteAttendances() | /attendance/listへリダイレクト |
| POST | /export | exportToCSV() | /attendance/listへリダイレクト |
## SiteUser
| HTTPメソッド | URL | Controllerメソッド | テンプレート |
| ------------- | ------------- | ------------- | ------------- |
| GET | /admin/siteuser/list | showAttendanceList() | siteuserList.html |
| POST | /admin/pre/siteuser/create | prepareSiteUser() | preSiteUsersForm.html |
| GET | /admin/siteuser/create | createSiteUser() | siteUsersForm.html |
| POST | /admin/siteuser/create | createSiteUser() | 成功時、/siteuser/listへリダイレクト。失敗時は、siteUsersForm.html |
| POST | /admin/siteuser/edit | editSiteUsers() | siteUsersForm.html |
| POST | /admin/siteuser/update | updateSiteUser() | 成功時、/siteuser/listへリダイレクト。失敗時は、siteUsersForm.html|
| POST | /admin/siteuser/delete | deleteSiteUser() | /admin/siteuser/listへリダイレクト |
## Home
| HTTPメソッド | URL | Controllerメソッド | テンプレート |
| ------------- | ------------- | ------------- | ------------- |
| GET | /login | login() | login.html |
| GET | / | showTop() | top.html |
| GET | /register | register() | register.html |
| POST | /register | register() | 成功時、/loginへリダイレクト。失敗時は、register.html 
## 認可
 - User  Top → AttendanceList → 自分の勤怠の作成、変更、削除。
 - Admin Top → SiteUserList（全ユーザーの一覧） or AttendanceList（全てのユーザーの勤怠） → ユーザーの情報の作成、変更、削除。もしくは、自分自身の勤怠の作成。
 - SpringSecurityの設定により、ユーザーが他の人の勤怠を見たり、登録情報を見る事はできない。Adminも、他の人の勤怠の修正ができない。
## テスト
 - Junit5でテストを行った。基本的には、サービス層のテストがメイン。サービス層は、Admin, SiteUser, PracticeCalcがある。それぞれ、勤怠、ユーザー情報、勤怠時間の計算を担当している。
 サービス層については、各メソッドについて、最低でも一個のテストを作成している。
 - Attendancetest1ApplicationTests... 全レイヤを結合を結合したもの。基本のテスト。AttendanceRepositoryの動作を確認。モックは利用していない。
 - HttpRequestsTests... httpリクエスト•レスポンスについてのテスト
 - HttpRequestEmulatedTest... httpサーバーなし。エミュレートされたリクエストでテスト
 - WebLayerTests... WebLayerについてのテスト
 - ここからサービス層のテスト
 - SiteUserTests... 全レイヤーを結合したもの。Attendancetest1ApplicationTestsでのテスト内容を、SiteUserで行ったもの。
 - ServiceTestsForAttendance... AttendanceServiceについて、AttendanceRepositoryをモック化せずにテストを行ったもの。
 - ServiceTestsForSiteUser... SiteUserServiceについて、SiteUserRepositoryをモック化せずにテストを行ったもの。
 - ServiceMockTestForAttendance... AttendanceRepositoryをモック化してテストを行ったもの。
 - ServiceMockTestForSiteUser... SiteUserRepositoryをモック化してテストを行ったもの。
 - CalcurationTestForAttendance... PracticeCalcServiceについてのテスト。
