package ru.TIPTOPHOTDOG.game.server;

import ru.TIPTOPHOTDOG.game.Main;
import ru.TIPTOPHOTDOG.game.UI.GameField;

import javax.swing.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class Client extends JFrame {
    String[] actionList;
    Socket client;
    DataSocket data;

    public Client(Main main)
    {
        setMinimumSize(new Dimension(1920, 1080));
        add(new GameField());
    }

    class DataSocket implements Runnable {
        DataInputStream in;
        DataOutputStream out;
        boolean work;
        String nickname;

        DataSocket(InputStream is, OutputStream os, String nickname) {
            in = new DataInputStream(is);
            out = new DataOutputStream(os);
            work = true;
            this.nickname = nickname;
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

        public void sendToServer(String s) {
            try {
                s = nickname + ":" + s;
                out.writeUTF(s);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ;
        }

        @Override
        public void run() {
            try {
                String line = null;
                while (work) {
                    line = in.readUTF();
                    add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}