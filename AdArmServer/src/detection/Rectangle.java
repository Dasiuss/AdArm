package detection;

import static java.lang.Math.*;

import java.util.List;

public class Rectangle {
	public List<Coords> vertices;

	public Rectangle(List<Coords> vertices) {
		this.vertices = vertices;
	}

	public Coords getCenter() {
		double x = vertices.stream().mapToInt(v -> v.x).average().orElseThrow(() -> new RuntimeException("rectangle badly initialised"));
		double y = vertices.stream().mapToInt(v -> v.y).average().orElseThrow(() -> new RuntimeException("rectangle badly initialised"));
		return new Coords((int) round(x), (int) round(y));
	}

}
