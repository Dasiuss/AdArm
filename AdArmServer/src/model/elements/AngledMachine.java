package model.elements;

abstract class AngledMachine implements MachineDimensionDetails{
	abstract double getAlpha();

	abstract double getBeta();

	public double getEffectorLevel() {
		//first arm
		double alpha = getAlpha();
		double h1 = arm1Length * Math.sin(Math.toRadians(alpha));

//		java.lang.System.out.println("h1 = " + h1);

		//second arm
		double beta = getBeta();
		double beta_1 = 180 - 90 - alpha;
		double beta_2 = beta - beta_1;
		double h2 = arm2Length * Math.cos(Math.toRadians(beta_2));

//		java.lang.System.out.println("h2 = " + h2);

		//sum up
		return h1-h2 +baseHeight;
	}

	public double getEffectorReach() {
		//first arm
		double alpha = getAlpha();
		double h1 = arm1Length * Math.cos(alpha);

		//second arm
		double beta = getBeta();
		double beta_1 = 180 - 90 - alpha;
		double beta_2 = beta - beta_1;
		double h2 = arm2Length * Math.sin(beta_2);

		//sum up
		return h1-h2 +baseHeight;
	}


}
