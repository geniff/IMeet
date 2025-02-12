package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class IMeetsBackServerRunner {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket socket = serverSocket.accept();
             DataOutputStream rsStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream rqStream = new DataInputStream(socket.getInputStream())) {

            String request;
            while (true) {
                request = rqStream.readUTF();
                if ("stop".equals(request)) {
                    break; // Выход из цикла при получении "stop"
                }
                String response = "Hi from server: " + request; // Эхо запроса
                rsStream.writeUTF(response);
            }
        }
    }
}
