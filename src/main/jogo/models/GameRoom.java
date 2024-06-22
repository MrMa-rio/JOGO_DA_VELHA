package src.main.jogo.models;

import java.io.Serializable;

public class GameRoom implements Serializable {
    private String codeRoom;
    private String hostId;
    private boolean isClosed;
    public void setCodeRoom(String codeRoom) {
        this.codeRoom = codeRoom;
    }
    public String getCodeRoom() {
        return codeRoom;
    }

    public boolean getIsClosed(){
        return isClosed;
    }
    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
    public String getHostId() {
        return hostId;
    }
}
