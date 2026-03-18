package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeScreenShot {
    public static void main(String[] args) {
        System.out.println(createFileName());
    }

    public static void takeScreenShot(TakesScreenshot screenShot){
        String fileName = createFileName();
        File scrFile = screenShot.getScreenshotAs(OutputType.FILE);
        try {
            File dir = new File("build/screenshots");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            Files.copy(scrFile.toPath(), new File(fileName).toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String createFileName(){
        SimpleDateFormat  formater = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss");
        Date date = new Date(System.currentTimeMillis());
        System.out.println(date);
        String currentDate = formater.format(date);
        System.out.println(currentDate);
        //String fileName = "src/test/test_logs/screenshots/scr-" + currentDate + ".png";
        String fileName = "build/screenshots/scr-" + currentDate + ".png";
        return fileName;
    }
}
