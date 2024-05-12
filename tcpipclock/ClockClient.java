package tcpipclock;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClockClient extends JFrame {
    private JLabel timeLabel;

    public ClockClient() {
        setTitle("Clock Client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(timeLabel, BorderLayout.CENTER);

        connectToServer();
    }

    private void connectToServer() {
        String serverAddress = "localhost"; 
        int serverPort = 12345;

        Timer timer = new Timer(1000, e -> {
            try (Socket socket = new Socket(serverAddress, serverPort);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                
                out.println("time");

                String response = in.readLine();

                
                timeLabel.setText(response);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClockClient clockClient = new ClockClient();
            clockClient.setVisible(true);
        });
    }
}