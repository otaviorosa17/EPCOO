package elementos.projeteis;

public class EnemyProjectile extends Projectile {
	private double radius;
	public EnemyProjectile() {
		super(new int [200], new double[200], new double[200], new double[200], new double[200]);
		this.initializeStates();
		this.radius = 2.0;
	}
	public double getRadius() {
		return radius;
	}
}
