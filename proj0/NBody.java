
public class NBody {
    /**
     * 酷炫的太阳系运行
     * @param args
     */
    public static void main(String[] args) {
        // creating an animation
        double T, dt;
        T = Double.parseDouble(args[0]);            // 终止时间
        dt = Double.parseDouble(args[1]);           // time delta
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        Double radius = readRadius(filename);

        StdDraw.enableDoubleBuffering();
        StdDraw.setScale(-radius, radius);
        StdDraw.picture(0, 0, "./images/starfield.jpg");	// 图片的中心在画布5，5的位置
        StdDraw.show();
//        System.out.println("src/images/starfield.jpg");

        double time = 0;
        double[] xForces, yForces;
        xForces = new double[planets.length];
        yForces = new double[planets.length];
        while (time < T) {
            for (int i = 0; i < planets.length; i++) {
                Planet p = planets[i];
                xForces[i] = p.calcNetForceExertedByX(planets);
                yForces[i] = p.calcNetForceExertedByY(planets);
            }
            StdDraw.clear();
            StdDraw.picture(0, 0, "./images/starfield.jpg");	// 图片的中心在画布5，5的位置
            for (int i = 0; i < planets.length; i++) {
                Planet p = planets[i];
                p.update(dt, xForces[i], yForces[i]);
                p.draw();
            }
            StdDraw.show();
            StdDraw.pause(10);
            time += dt;
        }

        // print the universe
        StdOut.printf("%d\n", planets.length);
        StdOut.printf("%.2e\n", radius);
        for (int i = 0; i < planets.length; i++) {
            Planet p = planets[i];
            StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n", p.xxPos, p.yyPos, p.xxVel, p.yyVel, p.mass, p.imgFileName);
        }
//        System.exit(0);         // 留坑，不清楚为什么不会自动退出main
    }

    public static double readRadius (String file_path) {
        In in = new In(file_path);
        int count_of_planets = in.readInt();
        double radius = in.readDouble();
        return radius;
    }

    public static Planet[] readPlanets (String file_path) {
        In in = new In(file_path);
        int num = in.readInt();
        in.readDouble();

        Planet[] planets = new Planet[num];
        for (int i = 0; i < num; i++) {
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            String img = imgFileName;
            planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, img);
        }
        return planets;

    }
}
