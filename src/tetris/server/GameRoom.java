package tetris.server;

import java.util.ArrayList;
import java.util.List;

import tetris.common.Protocol;

/**
 * 2인 게임방. 두 명이 모이면 게임을 시작한다.
 */
public class GameRoom {

    private final int id;
    private final List<ClientHandler> players = new ArrayList<>();

    public GameRoom(int id) {
        this.id = id;
    }

    /** 플레이어를 방에 넣는다. 2명이 되면 바로 게임을 시작한다. */
    public synchronized void add(ClientHandler c) {
        players.add(c);
        c.setRoom(this);
        if (isFull()) {
            startGame();
        }
    }

    /** 플레이어를 목록에서만 뺀다. 남은 사람에게 알리는 것은 14주차에 추가한다. */
    public synchronized void leave(ClientHandler c) {
        players.remove(c);
        GameServer.log("게임방 " + id + ": " + c.getPlayerName() + " 퇴장, 남은 인원 " + players.size());
    }

    /** 두 사람에게 각각 상대 닉네임을 담아 "/START 상대닉네임"을 보낸다. */
    private void startGame() {
        ClientHandler a = players.get(0);
        ClientHandler b = players.get(1);
        a.send("/START " + b.getPlayerName());
        b.send("/START " + a.getPlayerName());
        GameServer.log("게임방 " + id + " 게임 시작: " + a.getPlayerName() + " vs " + b.getPlayerName());
    }

    public synchronized boolean isFull() {
        return players.size() >= Protocol.MAX_PLAYERS;
    }

    public synchronized int size() {
        return players.size();
    }

    public int getId() {
        return id;
    }
}
