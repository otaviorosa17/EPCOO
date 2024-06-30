package elementos.entidades;

import elementos.*;
import elementos.projeteis.Projectile;

public abstract class Entidade extends Elemento{
	protected double [] explosion_start;		// instantes dos inícios das explosões
	protected double [] explosion_end;		// instantes dos finais da explosões			
	protected double radius;								// raio   
	protected Projectile p;

    public Entidade(int [] states, double [] X, double [] Y, double [] explosion_start, double [] explosion_end, double radius){
    	super(states, X, Y);
    	this.explosion_start = explosion_start;
    	this.explosion_end = explosion_end;
    	this.radius = radius;
    }
    
	public void animacaoExplode(long currentTime, int i) {

	}

    public double[] getExplosion_start() {
		return explosion_start;
	}
	public double[] getExplosion_end() {
		return explosion_end;
	}	
	public double getRadius() {
		return radius;
	}
	void setRadius(double radius) {
		this.radius = radius;
	}
}