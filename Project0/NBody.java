public class NBody {
	public static double readRadius (String filename) {
		In in = new In(filename);
		in.readInt();
		double radius = in.readDouble();
		return radius;
	}

	public static Planet[] readPlanets (String filename) {
		In in = new In(filename);
		int num = in.readInt();
		in.readDouble();

		Planet[] planets = new Planet[num];
		for (int i=1; i<= num; i++ ) {
			Planet p = new Planet(in.readDouble(), 
				in.readDouble(), in.readDouble(), 
				in.readDouble(), in.readDouble(), 
				"./images/"+in.readString());
			planets[i-1] = p;
		}
		return planets;
	}

	public static void drawBackground(double radius) {
		String imageToDraw = "./images/starfield.jpg";
		double size = 2*radius;
		StdDraw.setScale(-radius, radius);
		StdDraw.picture(0, 0, imageToDraw, size, size);
	}

	public static void drawPlanet(Planet p) {
		p.draw();
	}

	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt= Double.parseDouble(args[1]);
		String filename=args[2];
		double radidus = readRadius (filename);
		Planet[] planets = readPlanets (filename);

		readRadius(filename);
		readPlanets(filename);

		drawBackground(2.50e+11);
		for (int i=0; i<planets.length; i++) {
			planets[i].draw();
		}

	}
}
