package ru.TIPTOPHOTDOG.game.server;

import java.io.*;

public class DataStream implements Runnable{
    DataInputStream input;
    DataOutputStream output;
    boolean  work;
    String nickname;

    DataStream(InputStream is, OutputStream os) {
        input = new DataInputStream(is);
        output = new DataOutputStream(os);
        work = true;
        new Thread(this).start();
    }

    private synchronized void add(String s)
    {

    }

    private void SendAction(String s)
    {
        try {
            s = nickname + ": " + s;
            output.writeUTF(s);
            input.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            String line = null;
            while(work) {
                line = input.readUTF();
                add(line);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
