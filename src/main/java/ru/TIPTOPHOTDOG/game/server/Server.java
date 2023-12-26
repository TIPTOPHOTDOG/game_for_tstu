package ru.TIPTOPHOTDOG.game.server;

import ru.TIPTOPHOTDOG.game.Main;
import ru.TIPTOPHOTDOG.game.UI.GameField;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Server extends JFrame{
    String[] actionList;
    ServerSocket server;
    DataSocket data[] = new DataSocket[50];
    Socket client;
    String nickname;
    int num_clients;
    final int port = 3333;

    public Server(Main main)
    {

    }

    class DataSocket implements Runnable {
        DataInputStream input;
        DataOutputStream output;
        boolean work;

        DataSocket(InputStream is, OutputStream os) {
            input = new DataInputStream(is);
            output = new DataOutputStream(os);
            work = true;
            new Thread(this).start();
        }

        private synchronized void add(String s) {
            String[] newActionList = new String[actionList.length + 1];
            for (int i = 0; i < newActionList.length; i++) {
                newActionList[i] = actionList[i];
            }
            newActionList[actionList.length] = s;
            actionList = newActionList;
        }

        public void stopListen() {
            work = false;
        }

        public void sendToClient(String s, int mode) {
            try {
                if (mode == 0) s = nickname + s;
                output.writeUTF(s);
                output.flush();
            } catch (IOException e) {   }
        }

        @Override
        public void run() {
            try {
                String line = null;
                while (work) {
                    line = input.readUTF();
                    add(line);
                    for (int i = 0; i < num_clients; i++) {
                        data[i].sendToClient(line, 1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    class PortListener implements Runnable {

        public PortListener() {
            new Thread(this).start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    client = server.accept();
                    InputStream in = client.getInputStream();
                    OutputStream out = client.getOutputStream();
                    data[num_clients] = new DataSocket(in, out);
                    num_clients++;
                    if (num_clients == 1) {
                        JFrame frame = new JFrame();
                        frame.add(new GameField());
                        frame.setMinimumSize(new Dimension(1920, 1080));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
