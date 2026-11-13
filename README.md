# Network Programming Tetris

## 1. 프로젝트 개요

네트워크를 통해 두 명의 사용자가 테트리스 대전을 진행하는 프로그램입니다.
하나의 서버에 두 클라이언트가 접속하여 대전하며, 줄을 제거하면 상대방을 공격할 수 있습니다.

> 현재는 초기 개발 환경만 구성된 상태입니다. 서버는 포트를 열고 접속 로그만 출력하며, 클라이언트는 빈 창에 서버 접속 가능 여부만 표시합니다.

## 2. 사용 기술

| 구분 | 내용 |
| --- | --- |
| 언어 | Java (JDK 17 이상, 개발 기준 JDK 22) |
| GUI | Java Swing |
| 네트워크 | Java Socket (TCP) |
| 빌드 | 별도 빌드 도구 없이 `javac` / VS Code Java 확장 |
| 개발 환경 | Windows, VS Code |

외부 라이브러리를 사용하지 않으므로 Maven/Gradle 없이 JDK만으로 빌드합니다.

## 3. 프로젝트 폴더 구조

```
.
├── src/tetris/
│   ├── client/   클라이언트 실행 및 서버 연결        (ClientMain)
│   ├── server/   서버 실행 및 클라이언트 관리        (ServerMain)
│   ├── game/     테트리스 게임 로직 (UI·소켓과 무관한 순수 로직)
│   ├── ui/       Swing 기반 GUI                      (MainFrame)
│   └── common/   서버·클라이언트 공용 코드, 프로토콜 (Protocol)
├── .vscode/      팀 공용 VS Code 설정 (소스 경로, 실행 구성, 추천 확장)
├── build.bat         전체 컴파일 (결과물: bin/)
├── run-server.bat    빌드 후 서버 실행
├── run-client.bat    빌드 후 클라이언트 실행
└── README.md
```

`bin/` 폴더는 컴파일 결과물이므로 Git에 올라가지 않습니다.

## 4. 개발 환경 설정 방법

1. **JDK 설치** (17 이상). 설치 후 터미널에서 확인합니다.
   ```
   java -version
   javac -version
   ```
2. **저장소 Clone**
   ```
   git clone https://github.com/guieuna3-lab/networkProgramming_tetris.git
   ```
3. **VS Code로 폴더 열기** 후, 추천 확장 설치 알림이 뜨면 **Extension Pack for Java**를 설치합니다.
   (알림이 없으면 확장 탭에서 `Extension Pack for Java`를 검색해 설치)

소스 경로(`src`)와 출력 경로(`bin`)는 `.vscode/settings.json`에 이미 설정되어 있습니다.

## 5. 서버 및 클라이언트 실행 방법

서버를 먼저 실행한 뒤 클라이언트를 실행합니다. 기본 포트는 `5000`입니다.

### 방법 A. VS Code

`실행 및 디버그`(Ctrl+Shift+D)에서 **Server** 또는 **Client**를 선택하고 F5를 누릅니다.

### 방법 B. 배치 파일

```
.\run-server.bat              서버 실행 (포트 5000)
.\run-server.bat 6000         포트를 지정하여 실행

.\run-client.bat              localhost:5000 에 접속
.\run-client.bat 192.168.0.10 5000    다른 PC의 서버에 접속
```

VS Code 기본 터미널(PowerShell)에서는 앞의 `.\`를 반드시 붙여야 합니다. 탐색기에서 더블클릭해도 실행됩니다.

2인 대전 테스트 시에는 터미널을 3개 열어 서버 1개와 클라이언트 2개를 실행합니다.

### 방법 C. 직접 명령어

```
javac -encoding UTF-8 -d bin -sourcepath src src\tetris\server\ServerMain.java src\tetris\client\ClientMain.java
java -cp bin tetris.server.ServerMain
java -cp bin tetris.client.ClientMain
```

## 6. 향후 구현할 주요 기능

1. Java Swing 기반 테트리스 GUI
2. 서버-클라이언트 소켓 통신
3. 2인 멀티플레이 대전
4. 줄 제거에 따른 상대방 공격 시스템
5. 간단한 실시간 채팅
6. 게임 시작, 종료 및 승패 처리
