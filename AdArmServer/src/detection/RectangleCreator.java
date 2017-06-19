package detection;

import static org.bytedeco.javacpp.opencv_core.*;

import java.util.LinkedList;
import java.util.List;

import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.CvSlice;

public class RectangleCreator {

	public static LinkedList<Rectangle> getListFromCvSeq(CvSeq rectangles) {
		LinkedList<Rectangle> resultRectangles = new LinkedList<>();

		CvSlice slice = new CvSlice(rectangles);
		for (int i = 0; i < rectangles.total(); i += 4) {
			CvPoint rect = new CvPoint(4);
			cvCvtSeqToArray(rectangles, rect, slice.start_index(i).end_index(i + 4));

			List<Coords> vertices = getVertices(rect);
			if (vertices.stream().noneMatch(RectangleCreator::onImageEdge)) {
				resultRectangles.add(new Rectangle(vertices));
			}
		}
		slice.close();
		return resultRectangles;
	}

	private static List<Coords> getVertices(CvPoint rect) {
		List<Coords> vertices = new LinkedList<>();
		for (int i1 = 0; i1 < 4; i1++) {
			CvPoint position = rect.position(i1);
			vertices.add(new Coords(position.x(), position.y()));
		}
		return vertices;
	}

	private static boolean onImageEdge(Coords coords) {
		return coords.x == 1 || coords.y == 1 || coords.x == 638 || coords.y == 478;
	}

}
