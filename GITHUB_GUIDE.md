# GitHub 협업 가이드

Git과 GitHub를 처음 쓰는 팀원을 위한 안내서입니다.
위에서부터 순서대로 따라 하면 됩니다. 명령어는 VS Code의 터미널(`` Ctrl+` ``)에 입력합니다.

- [1. 먼저 알아둘 것](#1-먼저-알아둘-것)
- [2. 처음 한 번만 하는 설정](#2-처음-한-번만-하는-설정)
- [3. 매번 반복하는 작업 순서](#3-매번-반복하는-작업-순서)
- [4. VS Code 버튼으로 하는 방법](#4-vs-code-버튼으로-하는-방법)
- [5. 우리 팀 규칙](#5-우리-팀-규칙)
- [6. 충돌이 났을 때](#6-충돌이-났을-때)
- [7. 자주 만나는 문제](#7-자주-만나는-문제)
- [8. 명령어 요약](#8-명령어-요약)

---
## 1. 먼저 알아둘 것

**Git**은 내 컴퓨터에서 코드의 변경 기록을 저장하는 프로그램이고, **GitHub**는 그 기록을 올려 두고 팀원과 공유하는 웹사이트입니다.

코드는 세 곳에 있습니다.

```
내 컴퓨터의 파일  →  내 컴퓨터의 Git 기록  →  GitHub
   (수정)       commit              push
```

파일을 고치기만 해서는 팀원에게 전달되지 않습니다. **commit**으로 기록을 남기고, **push**로 GitHub에 올려야 팀원이 받을 수 있습니다.

| 용어 | 뜻 |
| --- | --- |
| 저장소 (repository) | 프로젝트 폴더와 그 변경 기록 전체 |
| commit | 변경 내용을 하나의 기록으로 저장하는 것. 게임의 세이브 지점과 같습니다 |
| push | 내 컴퓨터의 commit을 GitHub에 올리는 것 |
| pull | GitHub에 있는 팀원의 commit을 내 컴퓨터로 받아오는 것 |
| branch | 작업용 복사본. 여기서 작업하면 `main`에는 영향이 없습니다 |
| `main` | 항상 정상 실행되는 완성본을 두는 기본 branch |
| Pull Request (PR) | "내 branch를 `main`에 합쳐 주세요"라는 요청. GitHub 웹에서 만듭니다 |
| merge | 한 branch의 변경을 다른 branch에 합치는 것 |

---

## 2. 처음 한 번만 하는 설정

### 2-1. 준비물

1. **GitHub 계정**: https://github.com 에서 가입합니다.
2. **Git 설치**: https://git-scm.com/download/win 에서 내려받아 기본 옵션 그대로 설치합니다.
3. **JDK 17 이상**과 **VS Code**: 설치 방법은 [README.md](README.md)의 "개발 환경 설정 방법"을 봅니다.

설치가 끝나면 VS Code를 껐다 켠 뒤 터미널에서 확인합니다. 버전 숫자가 나오면 정상입니다.

```
git --version
```

### 2-2. 저장소 초대 수락

저장소 주인이 GitHub에서 나를 Collaborator로 초대해야 push할 수 있습니다.
초대를 받으면 가입한 이메일로 메일이 오니 **Accept invitation**을 누릅니다.
메일이 안 보이면 https://github.com/guieuna3-lab/networkProgramming_tetris/invitations 에 접속합니다.

### 2-3. 이름과 이메일 등록

commit에 누가 작업했는지 기록하기 위한 설정입니다. 이메일은 GitHub 가입 이메일을 씁니다.

```
git config --global user.name "내 GitHub 아이디"
git config --global user.email "내 GitHub 이메일"
```

### 2-4. 프로젝트 내려받기 (clone)

프로젝트를 둘 폴더(예: 바탕 화면)에서 터미널을 열고 실행합니다.

```
git clone https://github.com/guieuna3-lab/networkProgramming_tetris.git
```

`networkProgramming_tetris` 폴더가 생깁니다. VS Code에서 **파일 > 폴더 열기**로 이 폴더를 엽니다.

clone은 처음 한 번만 합니다. 그다음부터는 `git pull`로 최신 내용만 받습니다.

### 2-5. 로그인

처음 push할 때 브라우저 로그인 창이 뜹니다. **Sign in with your browser**를 눌러 GitHub 계정으로 로그인하면 이후로는 묻지 않습니다.

---

## 3. 매번 반복하는 작업 순서

기능 하나를 만들 때마다 아래 6단계를 반복합니다.

```
① main 최신화 → ② branch 만들기 → ③ 코드 작성 → ④ commit → ⑤ push → ⑥ PR 만들고 merge
```

### ① `main`을 최신 상태로 만들기

작업을 시작하기 전에 항상 먼저 합니다. 팀원이 올린 내용을 받아오는 단계입니다.

```
git switch main
git pull
```

### ② 작업용 branch 만들기

`main`에서 직접 작업하지 않고, 기능마다 branch를 새로 만듭니다.

```
git switch -c feature/game-board
```

`feature/game-board` 자리에 작업 내용을 나타내는 이름을 넣습니다. 이름 규칙은 [5. 우리 팀 규칙](#5-우리-팀-규칙)에 있습니다.

### ③ 코드 작성

평소처럼 코드를 작성하고 실행해 봅니다. 컴파일 오류가 없는지 확인한 뒤 다음 단계로 갑니다.

### ④ commit으로 기록 남기기

먼저 무엇이 바뀌었는지 확인합니다.

```
git status
```

바뀐 파일을 모두 담고 commit합니다.

```
git add .
git commit -m "feat: 게임 보드 클래스 추가"
```

commit은 작업 중간에 여러 번 해도 됩니다. 작은 단위로 자주 하는 편이 좋습니다.

### ⑤ GitHub에 올리기 (push)

이 branch를 처음 올릴 때:

```
git push -u origin feature/game-board
```

같은 branch를 두 번째부터 올릴 때:

```
git push
```

### ⑥ Pull Request 만들고 `main`에 합치기

1. https://github.com/guieuna3-lab/networkProgramming_tetris 에 접속합니다.
2. 위쪽에 뜨는 노란 알림의 **Compare & pull request**를 누릅니다.
3. 제목과 설명(무엇을 만들었는지)을 적고 **Create pull request**를 누릅니다.
4. 팀원에게 알려서 확인을 받습니다.
5. 팀원이 확인하면 **Merge pull request** → **Confirm merge**를 누릅니다.
6. **Delete branch**를 눌러 다 쓴 branch를 지웁니다.

merge가 끝나면 두 사람 모두 `main`을 최신화합니다. 이것이 다음 작업의 ①단계입니다.

```
git switch main
git pull
```

---

## 4. VS Code 버튼으로 하는 방법

명령어 대신 VS Code 왼쪽의 **소스 제어** 아이콘(`Ctrl+Shift+G`)에서도 같은 작업을 할 수 있습니다.

| 하려는 일 | VS Code에서 |
| --- | --- |
| branch 바꾸기·만들기 | 창 왼쪽 아래의 branch 이름 클릭 |
| 바뀐 파일 보기 | 소스 제어 탭의 "변경 사항" 목록. 파일을 누르면 바뀐 부분이 보입니다 |
| `git add` | 파일 옆의 **+** 버튼 |
| `git commit` | 위쪽 입력란에 메시지를 쓰고 **커밋** 버튼 |
| `git push` / `git pull` | **변경 내용 동기화** 버튼, 또는 **…** 메뉴의 푸시 / 풀 |

---

## 5. 우리 팀 규칙

### 꼭 지킬 것

1. **`main`에 직접 push하지 않습니다.** 항상 branch에서 작업하고 PR로 합칩니다.
2. **작업을 시작하기 전에 `git pull`을 합니다.**
3. **컴파일되지 않는 코드는 `main`에 합치지 않습니다.** PR을 만들면 GitHub가 자동으로 컴파일을 확인하며(초록색 체크), 빨간 X가 뜨면 고친 뒤 merge합니다.
4. **`git push --force`는 쓰지 않습니다.** 팀원의 작업이 지워질 수 있습니다.
5. **같은 파일을 동시에 고치지 않도록 누가 무엇을 하는지 미리 이야기합니다.**

### branch 이름

`종류/작업내용` 형식으로, 영어 소문자와 `-`를 씁니다.

| 종류 | 쓰는 경우 | 예시 |
| --- | --- | --- |
| `feature/` | 새 기능 | `feature/game-board`, `feature/chat` |
| `fix/` | 버그 수정 | `fix/block-rotation` |
| `docs/` | 문서 수정 | `docs/readme` |
| `chore/` | 기능과 무관한 설정·빌드·정리 작업 | `chore/project-setup-cleanup` |

### commit 메시지

`종류: 한 일` 형식으로 씁니다.

| 종류 | 쓰는 경우 | 예시 |
| --- | --- | --- |
| `feat` | 새 기능 | `feat: 블록 회전 기능 추가` |
| `fix` | 버그 수정 | `fix: 줄 제거 후 블록이 내려오지 않는 문제 수정` |
| `docs` | 문서 | `docs: 실행 방법 설명 보완` |
| `refactor` | 동작은 그대로 두고 코드만 정리 | `refactor: Board 클래스 메서드 분리` |
| `chore` | 설정·빌드 스크립트 등 기능과 무관한 작업 | `chore: 빌드 스크립트 정리` |

"수정", "작업 중"처럼 내용을 알 수 없는 메시지는 피합니다.

---

## 6. 충돌이 났을 때

두 사람이 **같은 파일의 같은 부분**을 다르게 고치면 Git이 어느 쪽을 남길지 정하지 못해 충돌(conflict)이 납니다. 고장이 아니라 사람이 직접 골라 달라는 뜻입니다.

충돌은 보통 PR 화면에 "This branch has conflicts"라고 뜨거나, `git pull` / `git merge` 중에 `CONFLICT`라는 메시지로 나타납니다.

### 해결 순서

1. 내 작업 branch에서 최신 `main`을 가져옵니다.
   ```
   git switch feature/game-board
   git pull origin main
   ```
2. VS Code에서 충돌한 파일을 엽니다. 이런 표시가 들어 있습니다.
   ```
   <<<<<<< HEAD
   내가 작성한 코드
   =======
   팀원이 작성한 코드
   >>>>>>> main
   ```
3. 표시 위에 나오는 버튼 중 하나를 누릅니다.
   - **현재 변경 사항 수락**: 내 코드만 남김
   - **수신 변경 사항 수락**: 팀원 코드만 남김
   - **두 변경 사항 모두 수락**: 둘 다 남김

   어느 쪽이 맞는지 모르겠으면 고르기 전에 팀원에게 물어봅니다.
4. `<<<<<<<`, `=======`, `>>>>>>>` 표시가 파일에 남아 있지 않은지 확인하고, 실행해서 정상 동작하는지 봅니다.
5. commit하고 push합니다.
   ```
   git add .
   git commit -m "fix: main과의 충돌 해결"
   git push
   ```

해결하다가 꼬였다면 아래 명령으로 충돌이 나기 전 상태로 되돌린 뒤 다시 시작할 수 있습니다.

```
git merge --abort
```

---

## 7. 자주 만나는 문제

**push했더니 `rejected` 또는 `failed to push some refs`가 나옵니다**
GitHub에 내 컴퓨터에 없는 commit이 있다는 뜻입니다. 먼저 받아온 뒤 다시 올립니다.

```
git pull
git push
```

**push했더니 `Permission denied` 또는 `403`이 나옵니다**
저장소 초대를 아직 수락하지 않았거나 다른 GitHub 계정으로 로그인된 상태입니다. [2-2](#2-2-저장소-초대-수락)를 다시 확인합니다.

**실수로 `main`에서 코드를 고쳤습니다 (아직 commit 전)**
고친 내용을 그대로 가지고 새 branch로 옮길 수 있습니다.

```
git switch -c feature/작업이름
```

**commit 메시지를 잘못 썼습니다 (아직 push 전)**

```
git commit --amend -m "올바른 메시지"
```

**파일 하나를 고치기 전 상태로 되돌리고 싶습니다 (아직 commit 전)**
고친 내용이 사라지니 확인하고 실행합니다.

```
git restore 파일경로
```

**`bin` 폴더나 `.class` 파일이 `git status`에 안 보입니다**
정상입니다. 컴파일 결과물은 `.gitignore`에 등록되어 있어 GitHub에 올라가지 않습니다.

**지금 내가 어느 branch에 있는지 모르겠습니다**

```
git status
```

첫 줄의 `On branch ...`에 나옵니다. VS Code 창 왼쪽 아래에도 표시됩니다.

**무엇을 해야 할지 모르겠습니다**
아무 명령이나 시도하기 전에 `git status` 결과를 캡처해서 팀원에게 보냅니다. `--force`, `reset --hard`가 들어간 명령은 인터넷에서 찾았더라도 혼자 실행하지 않습니다.

---

## 8. 명령어 요약

| 명령어 | 하는 일 |
| --- | --- |
| `git status` | 현재 branch와 바뀐 파일 확인 |
| `git switch main` | `main` branch로 이동 |
| `git pull` | GitHub의 최신 내용 받아오기 |
| `git switch -c 이름` | 새 branch를 만들고 이동 |
| `git add .` | 바뀐 파일을 모두 commit 대상에 담기 |
| `git commit -m "메시지"` | 기록 남기기 |
| `git push -u origin 이름` | branch를 처음 GitHub에 올리기 |
| `git push` | 두 번째부터 올리기 |
| `git log --oneline` | commit 기록 보기 (`q`로 종료) |
| `git branch` | branch 목록 보기 |
| `git branch -d 이름` | merge가 끝난 내 컴퓨터의 branch 지우기 |

### 한눈에 보는 하루 작업

```
git switch main
git pull
git switch -c feature/작업이름

  (코드 작성, 실행 확인)

git add .
git commit -m "feat: 한 일"
git push -u origin feature/작업이름

  (GitHub에서 PR 생성 → 팀원 확인 → Merge)

git switch main
git pull
```
