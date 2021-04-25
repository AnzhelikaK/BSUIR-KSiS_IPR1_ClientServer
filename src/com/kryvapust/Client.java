package com.kryvapust;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        int port = 8081;
        String host = "127.0.0.1";

        // сокет клиента
        Socket socket = new Socket(host, port);

        BufferedWriter writer = getBufferedWriter(socket);
        System.out.println("<Client> BufferedWriter is created - поток для записи.");

        BufferedReader reader = getBufferedReader(socket);
        System.out.println("<Client> BufferReader is created - поток для чтения.");

        // воод данных
        String request = inputArray();
        System.out.println("Input array for sending: " + request);

        // запись введенного массива в поток вывода /
        writer.write(request + "\n");

        // отправка запроса
        writer.flush();
        System.out.println("Request is sent.");

        // чтение ответа Сервера
        String result = reader.readLine();
        System.out.println("Received result from Server: " + result);

        // закрытие потоков чтение и записи и самого сокета
        reader.close();
        writer.close();
        socket.close();
    }

    private static BufferedReader getBufferedReader(Socket socket) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        BufferedReader reader = new BufferedReader(inputStreamReader);
        return reader;
    }

    private static BufferedWriter getBufferedWriter(Socket socket) throws IOException {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        BufferedWriter writer = new BufferedWriter(outputStreamWriter);
        return writer;
    }

    private static String inputArray() {
        System.out.println("Input array use Enter after each number. Ctrl+D - Submit: ");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> array = new ArrayList<>();
        while (scanner.hasNext()) {
            double v = scanner.nextDouble();
            array.add(v);
        }
        // преобразование введенного массива в текстовый вид
        return array.toString();
    }
}
