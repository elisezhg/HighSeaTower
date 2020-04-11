import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class HighSeaTower extends Application {

    public static int WIDTH = 350, HEIGHT = 480;

    public static void main(String[] args){
        HighSeaTower.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        Pane root = new Pane();
        Scene scene = new Scene(root);
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);

        Controleur controleur = new Controleur();

        scene.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case ESCAPE:
                    Platform.exit();
                case SPACE:
                case UP:
                    controleur.jump();
                    System.out.println("jump");
                    break;
                case LEFT:
                    controleur.left();
                    System.out.println("left");
                    break;
                case RIGHT:
                    controleur.right();
                    System.out.println("right");
                    break;
                case T:
                    System.out.println("debug mode");
            }
        });

        GraphicsContext context = canvas.getGraphicsContext2D();

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) * 1e-9;

                context.clearRect(0, 0, WIDTH, HEIGHT);
                controleur.update(dt);
                controleur.draw(context);

                lastTime = now;
            }
        };

        timer.start();


        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("res/jellyfish1.png"));
        primaryStage.setTitle("High Sea Tower");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}