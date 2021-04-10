# 勤怠管理アプリ

## 概要
Spring Boot及びjavaの基本的な使い方について学習しました。  
そのアウトプットとして、勤怠管理のアプリを作成しました。  
Wikiに主要な画面の変遷をスクショで取っております。
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
## 機能一覧
 - ユーザー登録 • ログイン機能(Spring Security)
 - 投稿機能
   - 勤怠の投稿、ユーザーの作成
   - 勤怠のCSV出力（Super CSV）
   - 画像（アバター）の登録
 - 検索（Springの支援メソッド及びSQLの手書き）
 - 一覧表示 • ソート
 - ページネーション（SpringのPageable利用）
## 環境
 - 開発環境 IDE(Eclipse)
 - 言語 java openjdk version 15.0.1
 - フレームワーク Spring Boot 2.4.0
 - データーベース 開発 H2 本番 MySQL 8.0.23
## テーブルの作成と初期データの作成
 - 本番環境が、MySQLの為、ローカルでプロジェクトを起動する為に準備が必要になります。
 - データベースの作成 : MySQLを起動→CREATE DATABASE attendance_db CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
 - テーブルの作成及び初期データの差し込み : パスに、Resourceを指定して、source attendance_setup.sql を実行。
## 使い方
 - Eclispeのワークスペースフォルダの下でgit clone
 - EclipseのメニューからFile→Import→Existing Maven Projects
 - EclipseのPackage Explorerでプロジェクトを右クリックしてMaven→Update project
 - EclipseのPackage Explorerでプロジェクトを右クリックしてRun As(またはDebug As)→Spring Boot App
 - ブラウザでhttp://localhost:80/

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
 - Admin Top → SiteUserList（全ユーザーの一覧） or AttendanceList（全てのユーザーの勤怠） → ユーザーに関するCRUDもしくは、自分自身の勤怠に関するCRUD
 - SpringSecurityの設定により、ユーザーが他の人の勤怠を見たり、登録情報を見る事はできない。Adminも、他の人の勤怠の修正ができない。
## テスト
 - Junit5を利用。  
   基本的には、サービス層のテストがメイン。  
   サービス層は、Admin, SiteUser, PracticeCalcがある。それぞれ、勤怠、ユーザー情報、勤怠時間の計算を担当している。  
   サービス層については、各メソッドについて、最低でも一個のテストを作成している。
 - AttendanceApplicationTests... 全レイヤを結合を結合したもの。基本のテスト。  
   AttendanceRepositoryの動作を確認。モックは利用していない。
 - HttpRequestsTests... httpリクエスト • レスポンスについてのテスト
 - HttpRequestEmulatedTest... httpサーバーなし。エミュレートされたリクエストでテスト
 - WebLayerTests... WebLayerについてのテスト
 - ここからサービス層のテスト
 - SiteUserTests... 全レイヤーを結合したもの。  
   AttendanceApplicationTestsでのテスト内容を、SiteUserで行ったもの。
 - ServiceTestsForAttendance... AttendanceServiceについて、AttendanceRepositoryをモック化せずにテストを行ったもの。
 - ServiceTestsForSiteUser... SiteUserServiceについて、SiteUserRepositoryをモック化せずにテストを行ったもの。
 - ServiceMockTestForAttendance... AttendanceRepositoryをモック化してテストを行ったもの。
 - ServiceMockTestForSiteUser... SiteUserRepositoryをモック化してテストを行ったもの。
 - CalcurationTestForAttendance... PracticeCalcServiceについてのテスト。
## パッケージング
 - ビルド $ mvnw package Attendance:repackage
 - 起動 $ java -jar Attendance-0.0.1-SNAPSHOT.jar
