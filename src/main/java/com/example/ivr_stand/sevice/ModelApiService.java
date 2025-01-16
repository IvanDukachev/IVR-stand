package com.example.ivr_stand.sevice;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class ModelApiService {

    private Socket socket;

    public ModelApiService() {
        try {
            socket = IO.socket("http://rsl-api:5000");

            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Connected to the model API server");
                }
            }).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    System.out.println("Disconnected from the model API server");
                }
            }).on("send_not_normalize_text", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    String message = (String) args[0];
                    String decodedMessage = decodeUnicode(message);
                    if ("нет жеста".equals(decodedMessage)) {
                        System.out.println("Gesture not recognized.");
                    } else {
                        System.out.println("Received message from model: " + decodedMessage);
                    }
                }
            });
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        socket.connect();
    }

    public void disconnect() {
        socket.disconnect();
    }

    public void sendData(String data) {
        socket.emit("data", data); // Отправка данных на сервер API
    }

    // Декодирование Unicode-сообщений
    private String decodeUnicode(String unicodeString) {
        Pattern unicodePattern = Pattern.compile("\\\\u([0-9A-Fa-f]{4})");
        Matcher matcher = unicodePattern.matcher(unicodeString);
        StringBuffer decodedString = new StringBuffer();

        while (matcher.find()) {
            String unicodeChar = matcher.group(1);
            int charCode = Integer.parseInt(unicodeChar, 16);
            matcher.appendReplacement(decodedString, Character.toString((char) charCode));
        }
        matcher.appendTail(decodedString);
        return decodedString.toString();
    }
}