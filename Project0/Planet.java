public class Planet {
	double xxPos, yyPos, xxVel, yyVel, mass;
	String imgFileName;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img) {
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p) {
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;
	}

	public double calcDistance (Planet p) {
		double r = Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos)
			+(yyPos-p.yyPos)*(yyPos-p.yyPos));
		return r;
	}

	public double calcForceExertedBy (Planet p) {
		double f = 6.67* Math.pow(10,-11) * mass * p.mass / 
			(calcDistance(p)*calcDistance(p));
		return f;
	}

	public double calcForceExertedByX (Planet p) {
		double fx;
			fx = calcForceExertedBy(p) * 
				(p.xxPos-xxPos)/calcDistance(p);
		return fx;
	}

	public double calcForceExertedByY (Planet p) {
		double fy;
			fy = calcForceExertedBy(p) * 
				(p.yyPos-yyPos)/calcDistance(p);
		return fy;
	}

	public double calcNetForceExertedByX (Planet[] p) {
		double fnx = 0;
		for(int i=0; i<p.length; i++) {
			if (p[i].equals(this) == false) {
				fnx += calcForceExertedByX(p[i]);
			}
		}
		return fnx;
	}

	public double calcNetForceExertedByY (Planet[] p) {
		double fny = 0;
		for(int i=0; i<p.length; i++) {
			if (p[i].equals(this) == false) {
				fny += calcForceExertedByY(p[i]);
			}
		}
		return fny;
	}

	public void update(double dt, double fX, double fY) {
		double ax, ay;
		ax = fX/mass;  ay = fY/mass;
		xxVel = xxVel + dt*ax;  yyVel = yyVel + dt*ay;
		xxPos = xxPos + dt*xxVel; yyPos = yyPos + dt*yyVel; 
	}

	public void draw() {
		StdDraw.picture(xxPos, yyPos, 
			"./images/"+imgFileName);
	}
}
