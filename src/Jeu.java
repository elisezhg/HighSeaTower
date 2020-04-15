//Elise ZHENG (20148416), Yuyin DING (20125263)

import java.util.ArrayList;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

/**
 * Représente le modèle du jeu High Sea Tower, contient la logique du jeu
 */
public class Jeu {

    // Variables globales
    public static int currentMode = 1;
    private static boolean showMenu = true;
    private static int bestScore = 0;
    public static boolean debugMode = false;
    public static boolean vitesseAcceleree = false;

    // Variables de l'instance
    private String[] modes = new String[] {"FACILE", "NORMAL", "DIFFICILE"};
    private Controleur controleur;
    private double fenetreX = 0, fenetreY = 0;  // Origine de la fenêtre
    private double vitesse = 50, acceleration = 2;
    private double tempsTotal = 0;
    private boolean debut = false;  // si le jeu a débuté ou non
    private int score = 0;

    // Entités dans le jeu
    private Meduse meduse;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Plateforme> plateformes = new ArrayList<>();
    private ArrayList<ArrayList<Bulle>> bulles;
    private int nbPf = 0; // nombre de plateformes générées


    /**
     * Constructeur du jeu
     * @param controleur controleur du jeu
     */
    public Jeu(Controleur controleur) {

        // Création de la méduse
        meduse = new Meduse();

        // Plateforme de début (sous la méduse)
        plateformes.add(new Plateforme(-1));

        // Génère les 5 premières plateformes
        for (int i = 0; i < 5; i++) {
            genererPlateformes();
        }

        genererBulles();
        Jeu.vitesseAcceleree = false;
        this.controleur = controleur;
    }


    /**
     * Génère des items aléatoirement
     */
    public void genererItems() {

        // Ne génère pas d'items s'il y en a assez à afficher
        if (-fenetreY + HighSeaTower.HEIGHT < nbPf * 100) return;

        double random = Math.random();
        Plateforme pf = plateformes.get(plateformes.size() - 1);

        if (random < 0.07) {
            Item item = new SacPlastique(pf);
            items.add(item);
        } else if (random < 0.14 && currentMode != 2) {
            Item item = new Poisson(pf);
            items.add(item);
        }

        // Enlève l'item qui n'est plus affichée
        if (items.size() > 6) {
            items.remove(0);
        }
    }


    /**
     * Génère des plateformes aléatoirement tout en supprimant celles qui ne sont plus utilisées
     */
    public void genererPlateformes() {

        // Ne génère pas de plateformes s'il y en a assez à afficher
        if (-fenetreY + HighSeaTower.HEIGHT < nbPf * 100) return;

        double random = Math.random();

        // Évite 2 plateformes solides d'affilée
        if (plateformes.get(plateformes.size() - 1) instanceof PlateformeSolide) {
            plateformes.add(new Plateforme(nbPf++));
            return;
        }

        // Probabilités des plateformes selon niveau de difficulté
        switch (currentMode) {
            case 0:
                if (random < 0.80) {
                    plateformes.add(new Plateforme(nbPf++));
                } else {
                    plateformes.add(new PlateformeRebondissante(nbPf++));
                }
                break;

            case 1:
                if (random < 0.65) {
                    plateformes.add(new Plateforme(nbPf++));
                } else if (random < 0.85) {
                    plateformes.add(new PlateformeRebondissante(nbPf++));
                } else if (random < 0.95) {
                    plateformes.add(new PlateformeAccelerante(nbPf++));
                } else {
                    plateformes.add(new PlateformeSolide(nbPf++));
                }
                break;

            case 2:
                if (random < 0.70) {
                    plateformes.add(new Plateforme(nbPf++));
                } else if (random < 0.80) {
                    plateformes.add(new PlateformeRebondissante(nbPf++));
                } else if (random < 0.95) {
                    plateformes.add(new PlateformeAccelerante(nbPf++));
                } else {
                    plateformes.add(new PlateformeSolide(nbPf++));
                }
                break;
        }

        // Enlève la plateforme qui n'est plus affichée
        if (plateformes.size() > 6) {
            plateformes.remove(0);
        }
    }


    /**
     * Génère 3 groupes de 5 bulles ayant une baseX aléatoire
     */
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


    /**
     * La touche up permet:
     * - si le menu est affiché, de changer le mode de difficulté
     * - sinon, de faire sauter la méduse
     */
    public void up() {
        if (showMenu) {
            currentMode = (currentMode - 1) % 3;
            currentMode = currentMode < 0 ? currentMode + 3 : currentMode; // positif
            controleur.nvPartie();
            return;
        }

        if (!debut) debut = true;
        meduse.jump();
    }


    /**
     * Si on est pas dans le menu:
     * - on débute le jeu si ce n'est pas déjà fait
     * - on fait bouger la méduse à gauche
     */
    public void left() {
        if (!debut && !showMenu) debut = true;
        meduse.left();
    }


    /**
     * Si on est pas dans le menu:
     * - on débute le jeu si ce n'est pas déjà fait
     * - on fait bouger la méduse à droite
     */
    public void right() {
        if (!debut && !showMenu) debut = true;
        meduse.right();
    }


    /**
     * L'accélération horizontale de la méduse prend fin
     */
    public void endAx() {
        meduse.endAx();
    }


    /**
     * Passe ou sort du mode debug
     */
    public void switchDebug() {
        debugMode = !debugMode;
    }


    /**
     * Si le menu est affiché, change le mode de difficulté
     * et crée une nouvelle partie de cette difficulté
     */
    public void down() {
        if (showMenu) {
            currentMode = (currentMode + 1) % 3;
            currentMode = currentMode < 0 ? currentMode + 3 : currentMode;
            controleur.nvPartie();
        }
    }


    /**
     * Passe ou sort du menu
     */
    public void switchMenu() {
        if (!debut) showMenu = !showMenu;
    }


    /**
     * Met à jour les positions, vitesses, accélérations de toutes les entités
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {

        if (debut) {

            if (!debugMode) {
                vitesse += dt * acceleration;

                // Si la méduse se trouve sur une plateforme accélérante
                if (vitesseAcceleree && meduse.getPfTouchee() != null) {
                    fenetreY -= vitesse * dt * 3;

                // Sinon, c'est qu'elle a quitté ou n'est pas sur la plateforme
                } else {
                    vitesseAcceleree = false;
                    fenetreY -= vitesse * dt;
                }
            }

            // MAJ score
            score = (int) -fenetreY;

            // Génère des bulles toutes les 3 secondes
            tempsTotal += dt;
            if (tempsTotal >= 3) {
                tempsTotal = 0;
                genererBulles();
            }

            // MAJ bulles
            for (ArrayList<Bulle> grpBulles : bulles) {
                for (Bulle bulle : grpBulles) {
                    bulle.update(dt);
                }
            }

            // MAJ méduse
            meduse.update(dt);
            meduse.setPfTouchee(null);
        }

        // MAJ plateformes
        for (Plateforme pf : plateformes) {
            pf.update(dt);
            pf.testCollision(meduse);
        }

        // MAJ items
        for (Item item : items) {
            if (item.isVisible()) {
                item.update(dt);
                item.testCollision(meduse);
            }
        }

        genererItems();
        genererPlateformes();

        // La méduse dépasse les 75%
        if (meduse.getPosY() - fenetreY < HighSeaTower.HEIGHT * 0.25) {
            fenetreY  -= HighSeaTower.HEIGHT * 0.25 - meduse.getPosY() + fenetreY;

        // Perdu
        } else if (meduse.getPosY() - fenetreY > HighSeaTower.HEIGHT) {
            if (score > bestScore) bestScore = score;
            controleur.nvPartie();
        }
    }


    /**
     * Dessine toutes les entités (méduse, plateformes, items) sur le canvas
     * @param context contexte graphique
     */
    public void draw(GraphicsContext context) {

        // Dessine les bulles
        for (ArrayList<Bulle> grpBulles : bulles) {
            for (Bulle bulle : grpBulles) {
                bulle.draw(context, fenetreX, fenetreY);
            }
        }

        // Dessine les plateformes
        for (Plateforme pf : plateformes) {
            pf.draw(context, fenetreX, fenetreY);
            pf.setContactMeduse(false);
        }

        // Dessine les items
        for (Item item : items) {
            if (item.isVisible()) item.draw(context, fenetreX, fenetreY);
        }

        // Dessine la méduse
        meduse.draw(context, fenetreX, fenetreY);


        // Meilleur score
        if (score > bestScore) {
            context.setFill(Color.ORANGERED);
            context.fillText("Record : " + score + "m", 5, 470);
        } else {
            context.setFill(Color.WHITE);
            context.fillText("Record : " + bestScore + "m", 5, 470);
        }

        // Mode actuel
        context.setFill(Color.WHITE);
        context.fillText(modes[currentMode], 5, 455);

        // Debug mode
        if (debugMode) {
            int medusePosX = (int) meduse.getPosX();
            int medusePosY = (int) (-meduse.getPosY() + HighSeaTower.HEIGHT);
            context.fillText("Position = (" + medusePosX + ", " + medusePosY+ ")", 5, 20);
            context.fillText("v = (" + (int) meduse.getVx() + ", " + (int) meduse.getVy() + ")", 5, 30);
            context.fillText("a = (" + (int) meduse.getAx() + ", " + (int) meduse.getAy() + ")", 5, 40);
            context.fillText("Touche le sol : " + (meduse.getPfTouchee() != null? "oui" : "non"),5, 50);
        }

        if (!debut) {
            context.setTextAlign(TextAlignment.RIGHT);
            context.fillText("Menu [M]", 345, 470);
        }

        // Score actuel
        Font before = context.getFont();
        context.setFont(new Font(25));
        context.setTextAlign(TextAlignment.CENTER);
        context.fillText(score + "m", (double) HighSeaTower.WIDTH / 2, 50);


        // Menu
        if (showMenu) {
            context.applyEffect(new GaussianBlur());
            context.setFill(Color.rgb(0, 0, 0, 0.5));
            context.fillRect(0, 0, HighSeaTower.WIDTH, HighSeaTower.HEIGHT);

            for (int i = 0; i < modes.length; i++) {
                context.setFill(Color.LIGHTSALMON);
                if (i == currentMode) context.setFill(Color.RED);
                context.fillText(modes[i], (double) HighSeaTower.WIDTH / 2, i*100 + 150);
            }
        }

        context.setTextAlign(TextAlignment.LEFT);
        context.setFont(before);
    }
}
