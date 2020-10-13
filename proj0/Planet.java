import edu.princeton.cs.algs4.StdDraw;

public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;

	public Planet(double xP, double yP, double xV,
				double yV, double m, String img){
		this.xxPos = xP;
		this.yyPos = yP;
		this.xxVel = xV;
		this.yyVel = yV;
		this.mass = m;
		this.imgFileName = img;
	}
	public Planet(Planet p){
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}

	public double calcDistance(Planet p){
		/** calculate the distance between two planets */
		double r;		// no public.
		double dx = p.xxPos - this.xxPos;
		double dy = p.yyPos - this.yyPos;
		r = Math.pow((Math.pow(dx,2)+Math.pow(dy,2)),0.5);
		return r;
	}

	public double calcForceExertedBy(Planet p){
		/** calculate the gravitational force between two planets */
		return 6.67*Math.pow(10,-11)*this.mass*p.mass/ Math.pow(this.calcDistance(p),2);
	}

	public double calcForceExertedByX(Planet p){
		double dx = p.xxPos - this.xxPos;
		double Fx = calcForceExertedBy(p) * dx / calcDistance(p);
		return Fx;
	}

	public double calcForceExertedByY(Planet p){
		double dy = p.yyPos - this.yyPos;
		double Fy = calcForceExertedBy(p) * dy / calcDistance(p);
		return Fy;
	}


	public double calcNetForceExertedByX(Planet[] allPlanets){
		int i = 0;
		double NetForceByX = 0;
		while (i < allPlanets.length){
			if (this.equals(allPlanets[i]) != true){
				NetForceByX = NetForceByX + calcForceExertedByX(allPlanets[i]);
			}
			i++;
		}
		return NetForceByX;
	}

	public double calcNetForceExertedByY(Planet[] allPlanets){
		int i = 0;
		double NetForceByY = 0;
		while (i < allPlanets.length){
			if (this.equals(allPlanets[i]) != true){
				NetForceByY = NetForceByY + calcForceExertedByY(allPlanets[i]);
			}
			i++;
		}
		return NetForceByY;
	}

	/**
	 * 外力作用下，planet自身的速度和位置有什么变化
	 * @param dt
	 * @param Fx
	 * @param Fy
	 */
	public void update(double dt, double Fx, double Fy){
		double ax = Fx / this.mass;
		double ay = Fy / this.mass;				// acceleration
		this.xxVel = this.xxVel + dt * ax;
		this.yyVel = this.yyVel + dt * ay;				// current velocity
		this.xxPos = this.xxPos + dt * this.xxVel;
		this.yyPos = this.yyPos + dt * this.yyVel;		// current position
	}

	public void draw() {
//		System.out.println(this.imgFileName);
		StdDraw.picture(this.xxPos, this.yyPos, "src/images/" + this.imgFileName);
	}
}