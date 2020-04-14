import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PlateformeAccelerante extends Plateforme {
    public PlateformeAccelerante(int i) {
        super(i);
        this.color = Color.rgb(230, 221, 58);
    }

    @Override
    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - this.posY) < 10 && other.vy > 0) {
            pushOut(other);
            other.vy = 0;
            Jeu.vitesseAcceleree = true;

            other.setParterre(true);
            this.contactMeduse = true;
        }
    }
}
