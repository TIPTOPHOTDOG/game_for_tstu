package ru.TIPTOPHOTDOG.game;

import ru.TIPTOPHOTDOG.game.server.Client;
import ru.TIPTOPHOTDOG.game.UI.GameField;
import ru.TIPTOPHOTDOG.game.server.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;


public class Main extends JFrame {
    private ServerSocket _server;
    private Socket _client;

    private String[] Actions;
    private int _xSize;
    private int _ySize;

    private JButton _startServer;
    private JButton _startClient;
    private JButton _openCardLibrary;
    private JButton _infoButton;
    private JButton _exitButton;

    private Main() {
        _xSize = 200;
        _ySize = 250;
        Container container = getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.setBackground(Color.black);
        container.setPreferredSize(new Dimension(_xSize, _ySize));
        Dimension sSize = Toolkit.getDefaultToolkit().getScreenSize();
        setTitle("GameForTSTU");
        setBackground(Color.black);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWidgets(container);
        setMinimumSize(new Dimension(_xSize, _ySize));
        setLocation((sSize.width - _xSize) / 2, (sSize.height - _ySize) / 2);
        setVisible(true);
    }

    private void addWidgets(Container container) {
        _startServer = createButton("Запустить сервер");
        _startClient = createButton("Запустить клиент");
        _openCardLibrary = createButton("Открыть карты");
        _infoButton = createButton("Информация о игре");
        _exitButton = createButton("Выйти из игры");
        _startServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startServer();
            }
        });
        _startClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startClient();
            }
        });
        container.add(_startServer);
        container.add(_startClient);
        container.add(_openCardLibrary);
        container.add(_infoButton);
        container.add(_exitButton);
    }

    private JButton createButton(String text) {
        JButton button;
        button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(_xSize, _ySize / 5));
        button.setFocusable(false);
        return button;
    }

    private void startServer()
    {
        JFrame frame = new JFrame();
        frame.setMinimumSize(new Dimension(200, 150));
        frame.setVisible(true);
    }

    private void startClient()
    {
        JFrame frame = new Client(this);
        frame.setVisible(true);
    }

    public void SetServer(ServerSocket server)
    {
        _server = server;
    }

    public static void main(String[] args) {
        new Main();
    }
}
