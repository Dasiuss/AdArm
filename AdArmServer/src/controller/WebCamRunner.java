package controller;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;
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
import model.ProgramsUtils;

public class WebCamRunner extends Program {

	private static final int CAMERA_WIDTH = 640;
	private static final int CAMERA_HEIGHT = 480;
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
		robot.system.attach();
		robot.C0.moveFast(100);
		robot.S2.moveFast(50);
		while (true) {
			try {
				IplImage img = converter.convert(cam.getFrame());

				CvSeq rectanglesSeq = rectangleDetector.findRectangles(img, storage);

				img = rectangleDetector.drawSquares(img, rectanglesSeq);

				canvas.showImage(converter.convert(img));
				cvReleaseImage(img);

				LinkedList<Rectangle> rectangles = RectangleCreator.getListFromCvSeq(rectanglesSeq);

				adjustPosition(rectangles);

				// release both images
				cvReleaseImage(img);
				// clear memory storage - reset free space position
				cvClearMemStorage(storage);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void adjustPosition(LinkedList<Rectangle> rectangles) throws InterruptedException {
		if (rectangles.isEmpty()) {
			return;
		}

		double avgX = rectangles.stream().mapToInt(r -> r.getCenter().x).average().getAsDouble();
		double avgY = rectangles.stream().mapToInt(r -> r.getCenter().y).average().getAsDouble();

		adjustX(avgX);
		adjustY(avgY);

		boolean isInCenter = avgY <= 300 && avgY >= 200 && avgX >= 260 && avgX <= 340;
		double effectorLevel = robot.getEffectorLevel();
		System.out.println("effectorLevel = " + effectorLevel);
		if (isInCenter && effectorLevel <= 9.5) {
			robot.C2.move(30);
			sleep(500);
			robot.C0.move(0);
			sleep(500);
			robot.C2.move(0);
			sleep(500);
			while(robot.S2.getLastPos() < 90 && robot.S1.getLastPos()< 90){
				if(robot.S2.getLastPos() < 90 ){
					robot.S2.moveRelative(1);
				}
				if(robot.S1.getLastPos() < 90 ){
					robot.S1.moveRelative(1);
				}
			}
			sleep(3000);
			robot.C0.move(100);
			sleep(500);

			ProgramsUtils.startPosition(robot);
			robot.C0.moveFast(100);
			robot.S2.moveFast(50);
			robot.system.detach();
			exit(0);
		} else if (isInCenter) {
			robot.S2.moveRelative(-1);
			robot.S1.moveRelative(-1);
			//sleep(30);
		} else {
			//sleep(10);
		}

	}

	private void adjustX(double avgX) throws InterruptedException {
		int positionDifference = (int) (avgX - CAMERA_WIDTH / 2);

		if (Math.abs(positionDifference) < 20) {
			return;
		}

		int degree = Math.floorDiv(positionDifference, 80);
		if (degree == 0) {
			degree = (int) Math.signum(positionDifference);
		}
		robot.S0.moveRelative(degree);
	}

	private void adjustY(double avgY) throws InterruptedException {
		int positionDifference = (int) (avgY - CAMERA_HEIGHT / 2);

		if (Math.abs(positionDifference) < 20) {
			return;
		}

		robot.S1.moveRelative((int) Math.signum(positionDifference));
		robot.S2.moveRelative((int) (-1 * Math.signum(positionDifference)));
//		robot.S1.moveRelative(Math.max(Math.floorDiv(positionDifference, 90) , 1));
//		robot.S2.moveRelative(Math.min(-Math.floorDiv(positionDifference, 90) , -1));
	}

}
