package voogasalad;


import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.stage.Stage;

public class Main extends Application {
    private static final int COUNT_LIMIT = 300000;

    public static void main(String[] args) {
        LauncherImpl.launchApplication(Main.class, SplashScreen.class, args);
    }
    
    @Override
    public void init() throws Exception {
        for(int i=0; i < COUNT_LIMIT; i++) {
            double progress = (100 * i) / COUNT_LIMIT;
            LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
        }
    }

    @Override
    public void start(Stage primaryStage) {
        MainScreen mainScreen = new MainScreen();
    }
}

