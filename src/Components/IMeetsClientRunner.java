package Components;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class IMeetsClientRunner {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080);
             DataOutputStream rqStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream rsStream = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Клиент запущен. Вы можете вводить сообщения:");

            // Запускаем поток для чтения сообщений от сервера
            new Thread(() -> {
                try {
                    while (true) {
                        String response = rsStream.readUTF();
                        System.out.println(response); // Выводим ответ сервера
                    }
                } catch (IOException e) {
                    System.out.println("Ошибка при получении сообщения от сервера: " + e.getMessage());
                }
            }).start();

            // Основной поток для отправки сообщений
            while (true) {
                String request = scanner.nextLine(); // Ожидаем ввода сообщения от пользователя
                rqStream.writeUTF(request); // Отправляем сообщение серверу

                // Проверка на команду "stop" для завершения клиента
                if ("stop".equals(request)) {
                    break; // Выход из цикла при получении "stop"
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Обработка исключений
        }
    }
}
