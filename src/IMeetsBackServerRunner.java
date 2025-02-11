import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class IMeetsBackServerRunner {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(8080);
             Socket socket = serverSocket.accept();
             DataOutputStream rsStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream rqStream = new DataInputStream(socket.getInputStream()))
        {
            String request = rqStream.readUTF();

            while ("stop".equals(request))
            {
                String response = "Hi from server";
                rsStream.writeUTF(response);
                request = rqStream.readUTF();
            }
        }

    }
}
