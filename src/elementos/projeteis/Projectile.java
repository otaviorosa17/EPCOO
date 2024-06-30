package elementos.projeteis;

import lib.GameLib;
import game.Main;

class Projectile {
	private int [] states;					// estados
	private double [] X;				// coordenadas x
	private double [] Y;				// coordenadas y
	private double [] VX;				// velocidades no eixo x
	private double [] VY;				// velocidades no eixo y

	public Projectile(int [] states, double [] X, double [] Y, double [] VX, double [] VY) {
		this.states = states; 					// estados
		this.X = X;				// coordenadas x
		this.Y = Y;				// coordenadas y
		this.VX = VX;				// velocidades no eixo x
		this.VY = VY;
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
	public double[] getVX() {
		return VX;
	}
	public double[] getVY() {
		return VY;
	}
	public void track(long delta) {
		for(int i = 0; i < this.getStates().length; i++){
				
			if(this.getStates()[i] == Main.ACTIVE){
				
				/* verificando se projétil saiu da tela */
				if(this.getY()[i] < 0 || this.getY()[i] > GameLib.HEIGHT ) {
					
					this.getStates()[i] = Main.INACTIVE;
				}
				else {
				
					this.getX()[i] += this.getVX()[i] * delta;
					this.getY()[i] += this.getVY()[i] * delta;
				}
			}
		}
	}
	void initializeStates() {
		for(int i = 0; i < this.getStates().length; i++) this.states[i] = Main.INACTIVE;
	}
	
}
