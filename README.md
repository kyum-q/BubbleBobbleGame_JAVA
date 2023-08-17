# 🫧&nbsp; 소켓 통신을 이용한 2인 협동 보글보글 게임

기존에 보글보글 게임을 재현한 프로젝트로 총 6stage로 이루어져 있으며 소켓 통신을 이용한	2인 협동 게임

## ✍🏻&nbsp; 게임의 기능

- #### 캐릭터 조작 기능
  - 방향키: 본인 캐릭터 움직임 조작(좌우상하)
- #### 캐릭터 공격 기능
  - 스페이스바: 버블 공격 (몬스터와 버블이 만나면 몬스터가 버블에 갇혀 죽는다)
  - 버블 터트리기: 버블에 몬스터가 갇혀있을 경우, 버블에 다가가 터트릴 수 있다. 점수 획득과 아이템이 생성된다.
   (임의로 터트리지 않으면 일정 시간 이후에 자동으로 터진다)
- #### 아이템 기능
  - 몬스터가 갇힌 버블이 터져 나오는 과일들이 아이템
  - 아이템을 획득하면 점수가 증가한다.
- #### 점수 기능
  - 버블을 터트리거나, 아이템을 획득하면 해당 플레이어 점수가 증가한다.
  - 점수는 재미요소이며, 실제 게임 성공에 영향을 끼치진 않는다.
- #### 플레이어 목숨
  - 플레이어의 목숨은 3개씩 존재한다.
  - 목숨이 다 감소되면 해당 플레이어는 더 이상 게임을 진행할 수 없으며, 남은 플레이어 혼자 게임을 진행한다.
  - 몬스터와 닿으면 목숨이 감소되고 초기 위치에서 다시 살아나 2초간 무적 상태를 가진다.
  - 두 플레이어가 목숨을 다 잃었을 시, 게임은 실패로 종료된다.
- #### 스테이지 클리어
  - 모두 6stage로 이루어져있으며, 각 스테이지에 존재하는 몬스터를 다 없애면 다음 스테이지로 넘어간다.
  - 모든 스테이지를 클리어했을 시, 게임은 성공으로 종료된다.
  <br>
  
## 🖥&nbsp; 실행 영상

[![BubbleBobbleGame 실행 영상](https://github.com/kyum-q/BubbleBobbleGame_JAVA/assets/109158497/84b4a585-1e24-4c8d-bdcb-5b75f6496731)](https://youtu.be/QYNwbZHmh8g?t=0s) 
<br>

## 📍&nbsp; 프로토콜

서버와 클라이언트는 다음과 같이 프로토콜을 설계하였다.

<img width="800" alt="image" src="https://github.com/kyum-q/BubbleBobbleGame_JAVA/assets/109158497/206373ca-ba49-4bd2-bfec-43a8b7ff37a4">
<br>


## 🔍&nbsp; 개발 언어
<img src="https://img.shields.io/badge/JAVA-FF7800?style=for-the-badge&logo=Java&logoColor=#7F52FF">

