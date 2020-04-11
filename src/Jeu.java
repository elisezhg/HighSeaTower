import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Jeu {

    // Origine de la fenêtre
    private double fenetreX = 0, fenetreY = 0;

    private double vitesse = 50;   // 50px/s
    private double acceleration = 2;  // 2px/s^2

    // Entités dans le jeu
    private Meduse meduse;

    public Jeu() {
        meduse = new Meduse();
    }


    public void update(double dt) {
        vitesse += dt * acceleration;
        fenetreY -= vitesse * dt;
        meduse.update(dt);
    }

    public void draw(GraphicsContext context) {
        meduse.draw(context, fenetreX, fenetreY);

        context.setFill(Color.BLACK);
        context.fillText("Origine de la fenêtre: (" + fenetreX + ", " + fenetreY + ")", 5, 30);
    }
}
