package src.main.jogo.models;

import java.io.Serializable;

public class GameRoom implements Serializable {
    private String codeRoom;
    private String hostId;

    public void setCodeRoom(String codeRoom) {
        this.codeRoom = codeRoom;
    }
    public String getCodeRoom() {
        return codeRoom;
    }
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
    public String getHostId() {
        return hostId;
    }
}
