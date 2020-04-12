import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Jeu {

    // Origine de la fenêtre
    private double fenetreX = 0, fenetreY = 0;

    private double vitesse = 50;   // 50px/s
    private double acceleration = 2;  // 2px/s^2

    public static boolean debugMode = false;

    // Si le jeu a commencé ou non
    public static boolean debut = false;

    // Si la méduse se trouve sur la plateforme accélérante ou non
    public static boolean vitesseAcceleree = false;

    // Entités dans le jeu
    private Meduse meduse;
    private ArrayList<Plateforme> plateformes = new ArrayList<>(); //TODO: trouver data structure adaptée

    public Jeu() {
        meduse = new Meduse();

        //TODO: proba
        // tests
        for (int i = 0; i < 5; i++) {
            plateformes.add(new PlateformeSimple(i * 3));
            plateformes.add(new PlateformeRebondissante(i * 3 + 1));
            plateformes.add(new PlateformeAccelerante(i * 3 + 2));
        }
    }

    public void jump() {
        if (!debut) debut = true;
        meduse.jump();
    }

    public void left() {
        meduse.left();
    }

    public void right() {
        meduse.right();
    }

    public void endAcc() {
        meduse.endAcc();
    }

    public void endVit() {
        meduse.endVit();
    }

    public void switchDebug() {
        debugMode = !debugMode;
    }


    public void update(double dt) {

        if (!debugMode && debut) {
            vitesse += dt * acceleration;

            // si la méduse se trouve sur une plateforme accélérante
            if (vitesseAcceleree & meduse.getParterre()) {
                fenetreY -= vitesse * dt * 3;

            // sinon, c'est qu'elle a quitté ou n'est pas sur la plateforme
            } else {
                vitesseAcceleree = false;
                fenetreY -= vitesse * dt;
            }
        }

        meduse.update(dt);

        if (meduse.getPosY() - fenetreY < HighSeaTower.HEIGHT * 0.25) {
            //fenetreY = ;
            System.out.println("dépasse les 75%!");
        } else if (meduse.getPosY() - fenetreY > HighSeaTower.HEIGHT) {
            System.out.println("perdu :(");
        }

        if (fenetreY != 0) {
            meduse.setParterre(false);
        }

        for (Plateforme pf : plateformes) {
            pf.update(dt);
            meduse.testCollision(pf);
        }
    }

    public void draw(GraphicsContext context) {
        meduse.draw(context, fenetreX, fenetreY);

        for (Plateforme pf : plateformes) {
            pf.draw(context, fenetreX, fenetreY);
        }

        context.setFill(Color.WHITE);
        // context.fillText("Origine de la fenêtre: (" + fenetreX + ", " + fenetreY + ")", 5, 50);
        // context.fillText("Vitesse: (" + vitesse + ")", 5, 20);

        if (debugMode) {
            context.fillText("Position = (" + (int) meduse.getPosX() + ", " + (int) (-meduse.getPosY() + HighSeaTower.HEIGHT) + ")", 5, 20);
            context.fillText("v = (" + (int) meduse.getVx() + ", " + (int) meduse.getVy() + ")", 5, 30);
            context.fillText("a = (" + (int) meduse.getAx() + ", " + (int) meduse.getAy() + ")", 5, 40);
        }

        // TODO: afficher le score en gros
        //context.setFont(new Font(context.getFont().getName(), 100));
        context.fillText((int) -fenetreY + "m", (double) HighSeaTower.WIDTH / 2, 50);
    }
}
