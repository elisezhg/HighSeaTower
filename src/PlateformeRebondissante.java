import javafx.scene.paint.Color;

public class PlateformeRebondissante extends Plateforme {
    public PlateformeRebondissante(int i) {
        super(i);
        this.color = Color.LIGHTGREEN;
    }

    @Override
    public void testCollision(Meduse other) {
        if (intersects(other) && Math.abs(other.posY + other.hauteur - this.posY) < 10 && other.vy > 0) {
            pushOut(other);
            other.vy *= -1.5;
            if (other.vy > -100) {
                other.vy = -100;
            }

            other.setParterre(true);
            this.contactMeduse = true;
        }
    }
}
