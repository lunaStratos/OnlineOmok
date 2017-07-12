package client;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageUtils {

    public static final String IMAGES_FOLDER_NAME = "images";
    
    static public Image[] loadImages() {
        try {
            return new Image[]{
            	loadImage(IMAGES_FOLDER_NAME + "/badookpan.jpg"), // 0
            	loadImage(IMAGES_FOLDER_NAME + "/whiteStone.png"), // 1
                loadImage(IMAGES_FOLDER_NAME + "/blackStone.png"), //2
                loadImage(IMAGES_FOLDER_NAME + "/backgroundLogin.jpg"), //3 �α���â 
                loadImage(IMAGES_FOLDER_NAME + "/wait_room.jpg"), // 4 ���GUI
                loadImage(IMAGES_FOLDER_NAME + "/battle_room.jpg"), // 5 ��ƲGUI
                loadImage(IMAGES_FOLDER_NAME + "/account_room.jpg") // 6 ����GUI
            };
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedImage loadImage(String path) throws IOException {
        return ImageIO.read(ImageUtils.class.getClassLoader().getResource(path));
    }
}