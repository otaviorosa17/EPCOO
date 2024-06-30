package elementos.entidades;
import java.awt.Color;
import elementos.projeteis.EnemyProjectile;
import static lib.GameLib.*;
import static game.Main.*;

class Enemy extends Entidade {
	private double [] V;
	private double [] angle;				// ângulos (indicam direção do movimento)
	private double [] RV;					// velocidades de rotação
	private long nextEnemy;					// instante em que um novo inimigo 1 deve aparecer
	protected EnemyProjectile p;

	public Enemy(double radius) {
		super(new int[10], new double[10], new double[10], new double[10], new double[10], radius);
		this.angle = new double[10];
		this.V = new double[10];
		this.RV = new double[10];					
		for(int i = 0; i < this.states.length; i++) this.states[i] = INACTIVE;
	}

	public void animacaoExplode(long currentTime, int i) {
		this.getStates()[i] = EXPLODING;
		this.getExplosion_start()[i] = currentTime;
		this.getExplosion_end()[i] = currentTime + 500;
	} 

	public void draw(long currentTime, int numEnemy) {	
		for(int i = 0; i < this.getStates().length; i++){
			
			if(this.getStates()[i] == EXPLODING){
				
				double alpha = (currentTime - this.getExplosion_start()[i]) / (this.getExplosion_end()[i] - this.getExplosion_start()[i]);
				drawExplosion(this.getX()[i], this.getY()[i], alpha);
			}
			
			if(this.getStates()[i] == ACTIVE){
		
				if (numEnemy == 1) {
					setColor(Color.CYAN);
					drawCircle(this.getX()[i], this.getY()[i], this.getRadius());
				}
				if (numEnemy == 2) {
					setColor(Color.MAGENTA);
					drawDiamond(this.getX()[i], this.getY()[i], this.getRadius());
				}
			}
		}
	}

	public double[] getV() {
		return V;
	}
	public double[] getAngle() {
		return angle;
	}
	public double[] getRV() {
		return RV;
	}
	public long getNextEnemy() {
		return nextEnemy;
	}
	void setNextEnemy(long nextEnemy) {
		this.nextEnemy = nextEnemy;
	}
}


