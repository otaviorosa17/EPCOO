package elementos.entidades;

import game.Main;

class Enemy {
	private int [] states;						// estados
	private double [] X;					// coordenadas x
	private double [] Y;					// coordenadas y
	private double [] V;					// velocidades
	private double [] angle;				// ângulos (indicam direção do movimento)
	private double [] RV;					// velocidades de rotação
	private double [] explosion_start;		// instantes dos inícios das explosões
	private double [] explosion_end;		// instantes dos finais da explosões			// instantes do próximo tiro
	private double radius;								// raio (tamanho do inimigo 1)
	private long nextEnemy;					// instante em que um novo inimigo 1 deve aparecer

	Enemy() {
		this.states = new int[10]; 
		this.X = new double[10];;						// estados				// coordenadas x
		this.Y = new double[10];					// coordenadas y
		this.V = new double[10];					// velocidades
		this.angle = new double[10];				// ângulos (indicam direção do movimento)
		this.RV = new double[10];					// velocidades de rotação
		this.explosion_start = new double[10];		// instantes dos inícios das explosões
		this.explosion_end = new double[10];		// instantes dos finais da explosões			// instantes do próximo tiro
		for(int i = 0; i < this.states.length; i++) this.states[i] = Main.INACTIVE;
	}

	public int[] getStates() {
		return states;
	}
	public double[] getX() {
		return X;
	}
	public double[] getY() {
		return Y;
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
	public double[] getExplosion_start() {
		return explosion_start;
	}
	public double[] getExplosion_end() {
		return explosion_end;
	}
	public double getRadius() {
		return radius;
	}
	public long getNextEnemy() {
		return nextEnemy;
	}
	void setNextEnemy(long nextEnemy) {
		this.nextEnemy = nextEnemy;
	}
	void setRadius(double radius) {
		this.radius = radius;
	}
}


