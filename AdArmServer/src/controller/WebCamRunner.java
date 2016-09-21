package controller;

import static org.bytedeco.javacpp.opencv_core.*;

import java.util.LinkedList;
import java.util.OptionalDouble;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import detection.Rectangle;
import detection.RectangleCreator;
import detection.RectangleDetector;
import detection.WebCam;

public class WebCamRunner extends Program {

	private RectangleDetector rectangleDetector = new RectangleDetector();
	private CanvasFrame canvas;
	private WebCam cam;

	public static void main(String[] args) {
		new WebCamRunner().run();
	}

	public WebCamRunner() {
		cam = new WebCam();
		canvas = new CanvasFrame("Web Cam");
		canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

	}

	@Override
	protected void run() {
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		CvMemStorage storage = cvCreateMemStorage(0);
		while (true) {
			try {
				IplImage img = converter.convert(cam.getFrame());

				CvSeq rectanglesSeq = rectangleDetector.findRectangles(img, storage);

				img = rectangleDetector.drawSquares(img, rectanglesSeq);

				canvas.showImage(converter.convert(img));
				cvReleaseImage(img);

				LinkedList<Rectangle> rectangles = RectangleCreator.getListFromCvSeq(rectanglesSeq);

				OptionalDouble averageOpt = rectangles.stream().mapToInt(r -> r.getCenter().x).average();
				if (averageOpt.isPresent()) {
					double avgX = averageOpt.getAsDouble();
					if (avgX < 300) {
						System.out.println("<");
					} else if (avgX > 340) {
						System.out.println(">");
					} else {
						double avgY = rectangles.stream().mapToInt(r -> r.getCenter().y).average().getAsDouble();
						if (avgY < 220) {
							System.out.println("^");
						} else if (avgY > 260) {
							System.out.println(",");
						} else {
							System.out.println("ok");
						}
					}
				}

				// release both images
				cvReleaseImage(img);
				// clear memory storage - reset free space position
				cvClearMemStorage(storage);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

}
