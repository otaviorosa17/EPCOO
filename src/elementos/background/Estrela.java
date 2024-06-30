package elementos.background;

import java.awt.Color;
import lib.GameLib;

public class Estrela {
	private double [] X;
	private double [] Y;
	private double speed;
	private Color cor;
	private int size;
	private double count;

	public Estrela(double [] X, double [] Y, double speed, Color cor, int size) {
		this.X = X;
		this.Y = Y;
		this.speed = speed;
		this.cor = cor;
		this.size = size;
		this.count = 0;
		for(int i = 0; i < this.X.length; i++){
			this.X[i] = Math.random() * GameLib.WIDTH;
			this.Y[i] = Math.random() * GameLib.HEIGHT;
		}
	}

	public void draw(long delta) {
        GameLib.setColor(this.cor);
        this.setCount(this.getCount() + this.getSpeed() * delta);
        
        for(int i = 0; i < this.getX().length; i++){
            
            GameLib.fillRect(this.getX()[i], (this.getY()[i] + getCount()) % GameLib.HEIGHT, size, size);
        }
    }

	public Color getCor() {
		return cor;
	}
	public int getSize() {
		return size;
	}
	public double getSpeed() {
		return speed;
	}
	public double[] getX() {
		return X;
	}
	public double[] getY() {
		return Y;
	}
	public double getCount() {
		return count;
	}

	public void setCount(double count) {
		this.count = count;
	}
}

