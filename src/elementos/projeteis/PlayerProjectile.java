package elementos.projeteis;

public class PlayerProjectile extends Projectile {
	public PlayerProjectile() {
		super(new int [10], new double[10], new double[10], new double[10], new double[10]);
		this.initializeStates();
	}
}
