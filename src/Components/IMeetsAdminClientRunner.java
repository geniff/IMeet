package Components;

import BL.Controller.AdminController;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static BL.Controller.AdminController.sendMessageToAllClients;

public class IMeetsAdminClientRunner
{
    public static final String SERVER_ADDRESS = "localhost";
    public static final int SERVER_PORT = 8080;

    public static void main(String[] args)
    {
        try(Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream()))
        {
            System.out.println("Админ-клиент подключен к серверу.");
            System.out.println("Для того, чтобы отправить конкретному клиенту сообщение введите: " +
                    "send <clientID> <message>");
            // Запускаем поток для чтения сообщений от администратора
            // лямбда выражение, которое представляет реализацию интерфейса Runnable, который содержит метод run

            AdminController adminController = new AdminController(socket);
            new Thread(adminController).start(); // Запускаем поток для обработки админ-клиента

            new Thread(() -> {
                try (Scanner scanner = new Scanner(System.in))
                {
                    while (true) {
                        String adminMessage = scanner.nextLine();
                        if (adminMessage.startsWith("send"))
                        {
                            String[] parts = adminMessage.split(" ", 3); // ожидаемый формат ввода: send <clientID>, message
                            if (parts.length == 3)
                            {
                                int clientId = Integer.parseInt(parts[1]);
                                String message = parts[2];
                                adminController.sendMessageToClient(clientId, "Server: " + message);
                            }

                            else
                            {
                                System.out.println("Неверный формат команды. Используйте: send <clientId> <message>");
                            }
                        }

                        else
                        {
                            sendMessageToAllClients("Server: " + adminMessage);
                        }
                    }
                }
            }).start();
        }

        catch (UnknownHostException e)
        {
            throw new RuntimeException(e);
        }

        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}
