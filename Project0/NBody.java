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
				in.readString());
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

	public static void animation(Planet[] p, double T, 
		double dt, double radius) {
		for (double time = 0; time<T; time+=dt) {
			double[] xForce = new double[p.length];
			double[] yForce = new double[p.length];
			for (int i=0; i<p.length; i++) {
				xForce[i] = p[i].calcNetForceExertedByX(p);
				yForce[i] = p[i].calcNetForceExertedByY(p);
			}
			for (int i=0; i<p.length; i++) {
				p[i].update (dt,xForce[i],yForce[i]);
			}

			drawBackground(radius);
			for (int i=0; i<p.length; i++)
				p[i].draw();

			StdDraw.show(10);
		}
	}

	public static void playSound(String filename) {
		StdAudio.play(filename);
	}

	public static void printState(Planet[] p, double radius) {
		StdOut.printf("%d\n", p.length);
		StdOut.printf("%7.2e\n", radius);
		for (int i=0; i<p.length; i++) 
			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", 
			p[i].xxPos, p[i].yyPos, p[i].xxVel, p[i].yyVel, 
			p[i].mass, p[i].imgFileName);
	}

	public static void main (String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt= Double.parseDouble(args[1]);
		String filename=args[2];
		double radius = readRadius (filename);
		Planet[] planets = readPlanets (filename);

		readRadius(filename);
		readPlanets(filename);

		playSound("./audio/2001.mid");
		animation(planets, T, dt, radius);
		printState(planets, radius);

	}
}
