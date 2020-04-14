import javafx.scene.paint.Color;

public class PlateformeSolide extends Plateforme {
    public PlateformeSolide(int i) {
        super(i);
        this.color = Color.rgb(184, 15, 36);
    }

    @Override
    public void testCollision(Meduse other) {
            if (intersects(other)) {
                pushOut(other);
                if (other.vy > 0) {
                    other.vy = 0;
                } else {
                    other.vy *= -0.8;
                }
                if (other.vx > 0 && Math.abs(other.posX + other.largeur - posX) < 10) {
                    other.vx *= -0.8;
                } else if (other.vx < 0 && Math.abs(other.posX - posX - largeur) < 10) {
                    other.vx *= -0.8;
                }

                other.setParterre(true);
                this.contactMeduse = true;
            }
    }

    @Override
    public void pushOut(Meduse other) {
        // à gauche de la plateforme
        if (other.vx > 0 && Math.abs(other.posX + other.largeur - this.posX) < 10) {
            other.posX = this.posX - other.largeur;
            // à droite
        } else if (other.vx < 0 && Math.abs(other.posX - this.posX - this.largeur) < 10){
            other.posX = this.posX + this.largeur;
            // en haut
        } else if (other.vy > 0) {
            other.posY = this.posY - other.hauteur;
            // en bas
        } else {
            other.posY = this.posY + this.hauteur;
        }
    }
}
