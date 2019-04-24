package CommandExtentionClient;

import SecureSocketChipV1.Enums.SSCV1Mode;
import SecureSocketChipV1.EventClasses.SSCCommandExecuteEvent;
import SecureSocketChipV1.SSCV1;
import SecureSocketChipV1.interfaces.SSCEvent;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

public class CommandExtentionClient extends SSCEvent {

    SSCV1 socket;

    public CommandExtentionClient(String ip, int port){
        try {
            Socket sock = new Socket(ip, port);
            socket = new SSCV1(sock, SSCV1Mode.CLIENT, null, this);
            socket.getProtocolManager().exchangeKeysProtocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command){
        UUID uuid = UUID.randomUUID();
        socket.getCom().sendMessage(command + " " + uuid);
        while(!socket.ifReturnReturned(uuid.toString())){}
        return socket.getReturn(uuid.toString());
    }

    @Override
    public void onCommandExecute(SSCCommandExecuteEvent e) {
        StringBuilder out = new StringBuilder(e.getCommand());
        for(String a : e.getArgs()){
            out.append(" ").append(a);
        }
        System.out.println(out.toString());
    }
}
