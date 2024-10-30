package com.example.ivr_stand;

import com.example.ivr_stand.controller.WebSocketController;
import com.example.ivr_stand.sevice.CameraService;
import com.example.ivr_stand.sevice.ModelApiService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class IvrStandApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(IvrStandApplication.class, args);
        CameraService cameraService = context.getBean(CameraService.class);
        WebSocketController socketController = context.getBean(WebSocketController.class);
        ModelApiService modelApiService = context.getBean(ModelApiService.class);

        modelApiService.connect();

        new Thread(() -> cameraService.startCamera(socketController, modelApiService)).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            modelApiService.disconnect();
            System.out.println("Disconnected from model API server");
        }));
    }
}
