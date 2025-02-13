package Components;

import BL.Controller.ClientController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IMeetsBackServerRunner {
    private static final List<ClientController> clients = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static int clientCounter = 0;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            // Запускаем поток для чтения сообщений от администратора
            // лямбда выражение, которое представляет реализацию интерфейса Runnable, который содержит метод run()
            new Thread(() -> {
                try (Scanner scanner = new Scanner(System.in)) {
                    while (true) {
                        String adminMessage = scanner.nextLine();
                        sendMessageToAllClients("Server: " + adminMessage);
                    }
                }
            }).start();

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    clientCounter += 1;
                    System.out.println("Клиент " + clientCounter + " подключен: " + socket.getInetAddress());
                    ClientController clientHandler = new ClientController(socket, clientCounter);
                    clients.add(clientHandler);
                    clientHandler.start();
                } catch (IOException e) {
                    System.out.println("Ошибка при подключении клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод для отправки сообщения всем клиентам
    private static void sendMessageToAllClients(String message) {
        for (ClientController client : clients) {
            client.sendMessage(message);
        }
    }
}