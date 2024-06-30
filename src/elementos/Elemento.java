package elementos;

public abstract class Elemento{
	protected int [] states;
    protected double [] X;
    protected double [] Y;
    
    public Elemento(int [] states, double [] X, double [] Y) {
        this.states = states;
    	this.X = X;
    	this.Y = Y;
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
    
}