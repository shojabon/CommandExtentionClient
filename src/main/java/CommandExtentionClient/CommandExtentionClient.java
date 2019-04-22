package CommandExtentionClient;

import SecureSocketChipV1.Enums.SSCV1Mode;
import SecureSocketChipV1.SSCV1;

import java.io.IOException;
import java.net.Socket;

public class CommandExtentionClient {

    SSCV1 socket;

    public CommandExtentionClient(String ip, int port){
        try {
            Socket sock = new Socket(ip, port);
            socket = new SSCV1(sock, SSCV1Mode.CLIENT);
            socket.getProtocolManager().exchangeKeysProtocol();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendCommand(String command){
        socket.getCom().sendMessage(command);
        return "test";
    }
}
