package controller;

import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;

import java.util.LinkedList;

import javax.swing.*;

import detection.Rectangle;
import detection.RectangleCreator;
import detection.RectangleDetector;
import detection.WebCam;

import static java.lang.Math.abs;
import static org.bytedeco.javacpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvCreateMemStorage;
import static org.bytedeco.javacpp.opencv_core.cvReleaseImage;

public class WebCamView extends Program {

	private RectangleDetector rectangleDetector = new RectangleDetector();
	private CanvasFrame canvas;
	private WebCam cam;

	public static void main(String[] args) {
		new WebCamView().start();
	}

	public WebCamView() {
		cam = new WebCam();
		canvas = new CanvasFrame("Web Cam");
		canvas.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

	}

	@Override
	public void start() {
		OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
		CvMemStorage storage = cvCreateMemStorage(0);
		while (true) {
			try {
				refreshRectangles(converter, storage);
			} catch (FrameGrabber.Exception e) {
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

}
