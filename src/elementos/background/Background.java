package elementos.background;
import java.awt.Color;
import lib.GameLib;

class Background {
			
	private double [] X;
	private double [] Y;
	private double speed;
	private double count;

	Background(int X, int Y, double speed, double count){
		this.X = new double [X];
		this.Y = new double [Y];
		this.speed = speed;
		this.count = count;
		for(int i = 0; i < this.X.length; i++){
			
			this.X[i] = Math.random() * GameLib.WIDTH;
			this.Y[i] = Math.random() * GameLib.HEIGHT;
		}
	}
	
	public void draw(Long delta, Color cor, int size){    //metodo pra desenhar o background
		GameLib.setColor(cor);
			count += speed * delta;
			
			for(int i = 0; i < X.length; i++){
				
				GameLib.fillRect(X[i], (Y[i] + count) % GameLib.HEIGHT, size, size);
			}
	}

	/*Getters e Setters*/
	double[] get_X() {
		return X;
	}
	double[] get_Y() {
		return Y;
	}
	double get_count() {
		return count;
	}
	double get_speed() {
		return speed;
	}
	void set_X(double[] X) {
		this.X = X;
	}
	void set_Y(double[] Y) {
		this.Y = Y;
	}
	void set_count(double count) {
		this.count = count;
	}
	void set_speed(double speed) {
		this.speed = speed;
	}
} 
