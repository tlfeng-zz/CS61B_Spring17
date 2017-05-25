/* a test that creates two planets 
   and prints out the pairwise force between them*/

public class TestPlanet {
	public static void main(String[] args) {
        calcForce();
    }

    private static void calcForce() {
    	Planet a = new Planet(1,2,1,2,50,"jupiter.gif");
    	Planet b = new Planet(3,4,3,4,100,"jupiter.gif");

    	System.out.println("pairwise force is "+ 
    		a.calcForceExertedBy(b));
    }

}