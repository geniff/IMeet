package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class    IMeetsClientRunner {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             DataOutputStream rqStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream rsStream = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                String request = scanner.nextLine();
                rqStream.writeUTF(request);

                // Проверка на команду "stop" для завершения клиента
                if ("stop".equals(request)) {
                    break;
                }

                // Чтение ответа от сервера
                String response = rsStream.readUTF();
                System.out.println(response);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }
}
