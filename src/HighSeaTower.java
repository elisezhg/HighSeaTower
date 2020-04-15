//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Représente la vue du jeu High Sea Tower
 */
public class HighSeaTower extends Application {

    // Variables globales : hauteur et largeur
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

        // Détecte les touches appuyées
        scene.setOnKeyPressed((e) -> {
            switch (e.getCode()) {
                case ESCAPE:
                    Platform.exit();
                case UP:
                case SPACE:
                    controleur.up();
                    break;
                case LEFT:
                    controleur.left();
                    break;
                case RIGHT:
                    controleur.right();
                    break;
                case T:
                    controleur.switchDebug();
                    break;
                case R:
                    controleur.nvPartie();
                    break;
                case ENTER:
                case M:
                    controleur.switchMenu();
                    break;
                case DOWN:
                    controleur.down();
                    break;
            }
        });

        // Détecte les touches relâchées
        scene.setOnKeyReleased((e) -> {
            if (e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.RIGHT) {
                controleur.endAx();
            }
        });

        GraphicsContext context = canvas.getGraphicsContext2D();

        // Crée l'animation du jeu
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = 0;

            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) * 1e-9;

                // Reset le canvas en dessinant le background
                context.setFill(Color.rgb(0, 8, 144));
                context.fillRect(0, 0, WIDTH, HEIGHT);

                // Met à jour le jeu et dessine
                controleur.update(dt);
                controleur.draw(context);

                lastTime = now;
            }
        };

        timer.start();

        // Stage
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("res/jellyfish1.png"));
        primaryStage.setTitle("High Sea Tower");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}