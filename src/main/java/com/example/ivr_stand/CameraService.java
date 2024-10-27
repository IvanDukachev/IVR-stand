package com.example.ivr_stand;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

@Service
public class CameraService {

    static {
        OpenCV.loadLocally();
    }

    public void startCamera(WebSocketController socketController, ModelApiService modelApiService) {
        VideoCapture camera = new VideoCapture(0);
        Mat frame = new Mat();

        if (!camera.isOpened()) {
            System.out.println("Error: Camera not opened.");
            return;
        }

        while (true) {
            camera.read(frame);

            if (!frame.empty()) {
                try {
                    Mat resizedFrame = new Mat();
                    Imgproc.resize(frame, resizedFrame, new org.opencv.core.Size(224, 224));

                    String base64Frame = encodeFrameToBase64(resizedFrame);
                    socketController.sendMessageToAllClients(base64Frame);

                    modelApiService.sendData(base64Frame);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Error: Frame is empty.");
            }
        }
    }

    // Преобразование изображения в Base64
    private String encodeFrameToBase64(Mat frame) {
        try {
            BufferedImage image = (BufferedImage) org.opencv.highgui.HighGui.toBufferedImage(frame);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", baos);
            byte[] imageBytes = baos.toByteArray();
            return "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}