package elementos.projeteis;

public class EnemyProjectile extends Projectile {
	public EnemyProjectile() {
		super(new int [200], new double[200], new double[200], new double[200], new double[200], 2.0);
		this.initializeStates();
	}
}
