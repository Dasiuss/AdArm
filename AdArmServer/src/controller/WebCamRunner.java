package controller;

import static java.lang.Math.abs;
import static java.lang.System.exit;
import static java.lang.Thread.sleep;
import static org.bytedeco.javacpp.opencv_core.*;

import java.util.LinkedList;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;

import javax.swing.*;

import detection.Rectangle;
import detection.RectangleCreator;
import detection.RectangleDetector;
import detection.WebCam;
import model.ProgramsUtils;

public class WebCamRunner extends Program {

	public static final int TARGET_ACQUIRING_TIME = 1000;
	private static final int CAMERA_WIDTH = 640;
	private static final int CAMERA_HEIGHT = 480;

	private static final int MAX_X_VARIATION = 100;
	private static final int MAX_Y_VARIATION = 50;
	private static final int CENTER_X = 300; //slightly left
	private static final int CENTER_Y = 300; //slightly up

	private RectangleDetector rectangleDetector = new RectangleDetector();
	private CanvasFrame canvas;
	private WebCam cam;
	private boolean lookingClose = true;
	private boolean targetLocked = false;
	private double lockingLevel;

	public static void main(String[] args) {
		new WebCamRunner().run();
	}

	public WebCamRunner() {
		cam = new WebCam();
		canvas = new CanvasFrame("Web Cam");
		canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	@Override
	protected void run() {
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		CvMemStorage storage = cvCreateMemStorage(0);
		robot.system.attach();
		returnToStartPosition();
		while (true) {
			try {
				LinkedList<Rectangle> rectangles = refreshRectangles(converter, storage);

				if (!targetLocked) {
					if (rectangles.isEmpty()) {
						lookAround();
					} else {
						sleep(TARGET_ACQUIRING_TIME);
						if (!refreshRectangles(converter, storage).isEmpty()) {
							targetLocked = true;
							lockingLevel = robot.getEffectorLevel();
						}
					}
				} else {
					if (!rectangles.isEmpty()) {
						adjustPosition(rectangles);
					} else {
						if (robot.getEffectorLevel() > lockingLevel) {
							targetLocked = false;
							returnToStartPosition();
						}else {
							moveUp();
							sleep(500);
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private LinkedList<Rectangle> refreshRectangles(OpenCVFrameConverter.ToIplImage converter, CvMemStorage storage) throws org.bytedeco.javacv.FrameGrabber.Exception {
		CvSeq rectanglesSeq = getImage(converter, storage);
		LinkedList<Rectangle> rectangles = RectangleCreator.getListFromCvSeq(rectanglesSeq);
		releaseImage(storage);
		return rectangles;
	}

	private CvSeq getImage(OpenCVFrameConverter.ToIplImage converter, CvMemStorage storage) throws org.bytedeco.javacv.FrameGrabber.Exception {
		IplImage img = converter.convert(cam.getFrame());

		CvSeq rectanglesSeq = rectangleDetector.findRectangles(img, storage);

		img = rectangleDetector.drawSquares(img, rectanglesSeq);

		canvas.showImage(converter.convert(img));
		cvReleaseImage(img);
		return rectanglesSeq;
	}

	private void releaseImage(CvMemStorage storage) {
		cvClearMemStorage(storage);
	}

	private void lookAround() {
		if (isLookingClose()) {
			moveRight();
		} else {
			moveLeft();
		}
		if (isOnRightBorder()) {
			moveAway();
		} else if (isOnLeftBorder()) {
			moveCloser();
		}
		sleep(1000);
	}

	private void returnToStartPosition() {
		ProgramsUtils.startPosition(robot);
		robot.C0.moveFast(100);
		robot.S1.moveFast(80);
		robot.S2.moveFast(90);
		sleep(1000);
	}

	private boolean isOnRightBorder() {
		return robot.S0.getLastPos() >= 110;
	}

	private boolean isOnLeftBorder() {
		return robot.S0.getLastPos() <= 70;
	}

	private void moveAway() {
		lookingClose = false;
		robot.S1.moveRelative(-20);
		robot.S2.moveRelative(20);
	}

	private void moveCloser() {
		lookingClose = true;
		robot.S1.moveRelative(20);
		robot.S2.moveRelative(-20);
	}

	private boolean isLookingClose() {
		return lookingClose;
	}

	private void moveRight() {
		robot.S0.moveRelative(5);
	}

	private void moveLeft() {
		robot.S0.moveRelative(-5);
	}

	private void adjustPosition(LinkedList<Rectangle> rectangles) throws InterruptedException {

		double avgX = rectangles.stream().mapToInt(r -> r.getCenter().x).average().getAsDouble();
		double avgY = rectangles.stream().mapToInt(r -> r.getCenter().y).average().getAsDouble();

		adjustX(avgX);
		adjustY(avgY);

		boolean isInCenter = isInCenter(avgX, avgY);

		double effectorLevel = robot.getEffectorLevel();
		if (isInCenter && effectorLevel <= 9.0) {
			catchObject();

			goToReleasePosition();
			sleep(1000);
			releaseObject();

			returnToStartPosition();

			robot.system.detach();
			exit(0);
		} else if (isInCenter) {
			moveDown();
		}

	}

	private void releaseObject() {
		robot.C0.move(100);
		sleep(500);
	}

	private void goToReleasePosition() {
		while (robot.S2.getLastPos() < 90 && robot.S1.getLastPos() < 90) {
			if (robot.S2.getLastPos() < 90) {
				robot.S2.moveRelative(1);
			}
			if (robot.S1.getLastPos() < 90) {
				robot.S1.moveRelative(1);
			}
		}
	}

	private void catchObject() {
		//move a little forward to catch in the middle
		robot.S1.moveRelative(-1);
		robot.S2.moveRelative(1);

		robot.C2.move(30);
		sleep(500);
		robot.C0.move(0);
		sleep(500);
		robot.C2.move(0);
		sleep(500);
	}

	private void moveUp() {
		robot.S2.moveRelative(1);
		robot.S1.moveRelative(1);
	}

	private void moveDown() {
		robot.S2.moveRelative(-1);
		robot.S1.moveRelative(-2);
	}

	private boolean isInCenter(double avgX, double avgY) {
		return abs(avgX - CENTER_X) < MAX_X_VARIATION && abs(avgY - CENTER_Y) < MAX_Y_VARIATION;
	}

	private void adjustX(double avgX) throws InterruptedException {
		int positionDifference = (int) (avgX - CENTER_X);

		if (abs(positionDifference) < MAX_X_VARIATION) {
			return;
		}

		int degree = getScaledDegree(positionDifference);
		robot.S0.moveRelative(degree);
		sleep(50);
	}


	private void adjustY(double avgY) throws InterruptedException {
		int positionDifference = (int) (avgY - CENTER_Y);

		if (abs(positionDifference) < MAX_Y_VARIATION) {
			return;
		}

		robot.S1.moveRelative((int) Math.signum(positionDifference));
		robot.S2.moveRelative((int) (-1 * Math.signum(positionDifference)));
	}

	private int getScaledDegree(int positionDifference) {
		int degree = Math.floorDiv(positionDifference, 100);
		if (degree == 0) {
			degree = (int) Math.signum(positionDifference);
		}
		return degree;
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}
}
