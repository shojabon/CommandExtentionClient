import CommandExtentionClient.CommandExtentionClient;

import java.util.concurrent.ConcurrentHashMap;

import static java.lang.Thread.sleep;

public class main {

    public static void main(String[] args){
        CommandExtentionClient com = new CommandExtentionClient("127.0.0.1", 10000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String a = com.sendCommand("test");
        System.out.println(a);
    }
}
