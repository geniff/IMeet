package Components;

import BL.Controller.ClientController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class IMeetsBackServerRunner
{
    private static int clientCounter = 0;;
    public static final Map<Integer, ClientController> clients = new HashMap<>();
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    clientCounter += 1;
                    System.out.println("Клиент " + clientCounter + " подключен: " + socket.getInetAddress());
                    ClientController clientHandler = new ClientController(socket, clientCounter);
                    clients.put(clientCounter, clientHandler);
                    clientHandler.start();
                } catch (IOException e) {
                    System.out.println("Ошибка при подключении клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}