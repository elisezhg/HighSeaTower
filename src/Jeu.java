import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class Jeu {

    private Controleur controleur;

    // Origine de la fenêtre
    private double fenetreX = 0, fenetreY = 0;

    private double vitesse = 50;   // 50px/s
    private double acceleration = 2;  // 2px/s^2
    private double tempsTotal = 0;

    public static boolean debugMode = false;

    // Si le jeu a commencé ou non
    public static boolean debut = false;

    // Si la méduse se trouve sur la plateforme accélérante ou non
    public static boolean vitesseAcceleree = false;

    // Entités dans le jeu
    private Meduse meduse;
    private ArrayList<Plateforme> plateformes = new ArrayList<>(); //TODO: trouver data structure adaptée
    private ArrayList<ArrayList<Bulle>> bulles;

    public Jeu(Controleur controleur) {
        Jeu.debut = false;
        Jeu.vitesseAcceleree = false;
        this.controleur = controleur;

        meduse = new Meduse();

        //TODO: proba
        // tests
        for (int i = 0; i < 5; i++) {
            plateformes.add(new PlateformeSimple(i * 4));
            plateformes.add(new PlateformeRebondissante(i * 4 + 1));
            plateformes.add(new PlateformeAccelerante(i * 4 + 2));
            plateformes.add(new PlateformeSimple(i * 4 + 3));
        }

        genererBulles();
    }

    public void genererBulles() {
        bulles = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            bulles.add(new ArrayList<>());
            double baseX = Math.random() * HighSeaTower.WIDTH; // entre 0 et width
            for (int j = 0; j < 5; j++) {
                bulles.get(i).add(new Bulle(baseX, fenetreY));
            }
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


        if (debut) meduse.update(dt);

        // dépasse les 75%
        if (meduse.getPosY() - fenetreY < HighSeaTower.HEIGHT * 0.25) {
            fenetreY  -= HighSeaTower.HEIGHT * 0.25 - meduse.getPosY() + fenetreY;

        // perdu
        } else if (meduse.getPosY() - fenetreY > HighSeaTower.HEIGHT) {
            controleur.nvPartie();
        }

        if (fenetreY != 0) {
            meduse.setParterre(false);
        }

        for (Plateforme pf : plateformes) {
            pf.update(dt);
            meduse.testCollision(pf);
        }

        if (debut) {
            tempsTotal += dt;

            if (tempsTotal >= 3) {
                tempsTotal = 0;
                genererBulles();
            }

            // update bulles
            for (ArrayList<Bulle> grpBulles : bulles) {
                for (Bulle bulle : grpBulles) {
                    bulle.update(dt);
                }
            }
        }
    }

    public void draw(GraphicsContext context) {
        meduse.draw(context, fenetreX, fenetreY);

        for (Plateforme pf : plateformes) {
            pf.draw(context, fenetreX, fenetreY);
        }

        for (ArrayList<Bulle> grpBulles : bulles) {
            for (Bulle bulle : grpBulles) {
                bulle.draw(context, fenetreX, fenetreY);
            }
        }

        context.setFill(Color.WHITE);

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
