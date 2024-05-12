package tcpipclock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeServer {
    public static void main(String[] args) {
        int port = 12345; // Cổng lắng nghe của server

        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server đã khởi động " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client đã kết nối từ địa chỉ: " + clientSocket.getInetAddress());

                // Đọc dữ liệu từ client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = in.readLine();

                // Xử lý thông điệp từ client
                if (message != null && message.equals("time")) {
                    String response = getCurrentTime();
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                    out.println(response);
                }

                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        return dateFormat.format(new Date());
    }
}