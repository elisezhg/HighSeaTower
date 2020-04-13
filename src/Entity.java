import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Entity {
    protected double largeur, hauteur;
    protected double posX, posY;

    protected double vx = 0, vy = 0;
    protected double ax = 0, ay = 0;

    protected Color color;

    /**
     * Met à jour la position et la vitesse de la balle
     *
     * @param dt Temps écoulé depuis le dernier update() en secondes
     */
    public void update(double dt) {
        vx += dt * ax;
        vy += dt * ay;
        posX += dt * vx;
        posY += dt * vy;

        // Force à rester dans les bornes de l'écran
        if (posX + largeur > HighSeaTower.WIDTH || posX < 0) {
            vx *= -0.6;
        }
        if (posY + hauteur > HighSeaTower.HEIGHT || posY < 0) {
            //vy *= -0.8;
        }
        posX = Math.min(posX, HighSeaTower.WIDTH - largeur);
        posX = Math.max(posX, 0);
        posY = Math.min(posY, HighSeaTower.HEIGHT - hauteur);
        //posY = Math.max(posY, 0);
    }

    public abstract void draw(GraphicsContext context, double fenetreX, double fenetreY);


    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getAx() {
        return ax;
    }

    public double getAy() {
        return ay;
    }
}
