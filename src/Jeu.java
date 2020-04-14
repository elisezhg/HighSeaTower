import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Control;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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
    private ArrayList<Plateforme> plateformes = new ArrayList<>();
    private ArrayList<ArrayList<Bulle>> bulles;
    private int num = 2;

    public Jeu(Controleur controleur) {
        Jeu.debut = false;
        Jeu.vitesseAcceleree = false;
        this.controleur = controleur;

        meduse = new Meduse();

        plateformes.add(new Plateforme(-1)); // plateforme de début
        plateformes.add(new Plateforme(0));
        plateformes.add(new PlateformeSolide(1));
        // génère 5 plateformes qui apparaissent sur l'écran
        for (int i = 0; i < 5; i++) {
            genererPlateformes();
        }

        genererBulles();
    }

    public void genererPlateformes() {
        if (-fenetreY + HighSeaTower.HEIGHT < num * 100) return;

        double random = Math.random();

        // évite 2 plateformes solides d'affilée
        if (plateformes.get(plateformes.size() - 1) instanceof PlateformeSolide) {
            plateformes.add(new Plateforme(num++));
            return;
        }

        if (random < 0.65) {
            plateformes.add(new Plateforme(num++));
        } else if (random < 0.85) {
            plateformes.add(new PlateformeRebondissante(num++));
        } else if (random < 0.95) {
            plateformes.add(new PlateformeAccelerante(num++));
        } else {
            plateformes.add(new PlateformeSolide(num++));
        }

        if (plateformes.size() > 6) {
            plateformes.remove(0); // enleve la plateforme qui n'est plus affichée
        }
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
        if (!debut) debut = true;
        meduse.left();
    }

    public void right() {
        if (!debut) debut = true;
        meduse.right();
    }

    public void endAcc() {
        meduse.endAcc();
    }

    public void switchDebug() {
        debugMode = !debugMode;
    }


    public void update(double dt) {

        genererPlateformes();

        if (!debugMode && debut) {
            vitesse += dt * acceleration;

            // si la méduse se trouve sur une plateforme accélérante
            if (vitesseAcceleree && meduse.isParterre()) {
                fenetreY -= vitesse * dt * 3;

            // sinon, c'est qu'elle a quitté ou n'est pas sur la plateforme
            } else {
                vitesseAcceleree = false;
                fenetreY -= vitesse * dt;
            }
        }


        if (debut) {
            meduse.setParterre(false);
            meduse.update(dt);
        }

        // dépasse les 75%
        if (meduse.getPosY() - fenetreY < HighSeaTower.HEIGHT * 0.25) {
            fenetreY  -= HighSeaTower.HEIGHT * 0.25 - meduse.getPosY() + fenetreY;

        // perdu
        } else if (meduse.getPosY() - fenetreY > HighSeaTower.HEIGHT) {
            controleur.nvPartie();
        }


        for (Plateforme pf : plateformes) {
            pf.update(dt);
            pf.testCollision(meduse);
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
            pf.setContactMeduse(false);
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
            context.fillText("Touche le sol : " + (meduse.isParterre() ? "oui" : "non"),5, 50);
        }

        Font before = context.getFont();
        context.setFont(new Font(25));
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText((int) -fenetreY + "m", (double) HighSeaTower.WIDTH / 2, 50);
        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(before);
    }
}
