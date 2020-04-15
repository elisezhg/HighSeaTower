//Elise ZHENG (20148416), Yuyin DING (20125263)

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class Item extends Entity{

    protected Image img;
    protected Plateforme pf;
    protected boolean visible = true;   // visibilité de l'item


    /**
     * Constructeur de l'item
     * @param pf plateforme sur laquelle se trouve l'item
     */
    public Item(Plateforme pf) {
        this.largeur = 30;
        this.hauteur = 30;
        this.posX = Math.random() * (pf.largeur - this.largeur) + pf.posX;
        this.posY = pf.posY - this.hauteur;
        this.pf = pf;
    }


    /**
     * Les sous-classes doivent déterminer quelle action effectuer s'il y a collision avec la méduse
     * @param other Méduse du jeu
     */
    public abstract void testCollision(Meduse other);


    /**
     * Met à jour la position et la vitesse de l'item
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        super.update(dt);
        posX += dt * pf.vx;  // l'item se déplace avec la plateforme
    }


    /**
     * Affiche l'image de l'item sur le canvas
     * @param context contexte graphique
     * @param fenetreX origine X de la fenêtre
     * @param fenetreY origine Y de la fenêtre
     */
    @Override
    public void draw(GraphicsContext context, double fenetreX, double fenetreY) {
        double xAffiche = posX - fenetreX;
        double yAffiche = posY - fenetreY;
        context.drawImage(img, xAffiche, yAffiche, largeur, hauteur);
    }


    // Getters et setters

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
