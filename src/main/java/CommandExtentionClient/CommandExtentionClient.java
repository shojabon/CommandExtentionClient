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
        long startTime = System.currentTimeMillis()/1000;
        while(!socket.ifReturnReturned(uuid.toString())){
            if(System.currentTimeMillis()/1000 > startTime+20){
                return null;
            }
        }
        String a = socket.getReturn(uuid.toString());
        socket.removeReturnMap(uuid.toString());
        return a;
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
