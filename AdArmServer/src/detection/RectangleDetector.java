package detection;

import static org.bytedeco.javacpp.helper.opencv_core.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

import org.bytedeco.javacpp.IntPointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_core.CvContour;
import org.bytedeco.javacpp.opencv_core.CvMemStorage;
import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.bytedeco.javacpp.opencv_core.CvSeq;
import org.bytedeco.javacpp.opencv_core.CvSize;
import org.bytedeco.javacpp.opencv_core.CvSlice;
import org.bytedeco.javacpp.opencv_core.IplImage;

public class RectangleDetector {
	private static final int LINE_THICKNESS = 1;
	public int NUMBER_OF_THRESHOLDS_TO_TRY = 11;
	public int AREA_MIN_LIMIT = 1000;
	public double COSINE_SIZE_LIMIT = 0.3;

	public CvSeq findRectangles(IplImage img, CvMemStorage storage) {
		CvSize sz = cvSize(img.width() & -2, img.height() & -2);
		IplImage timg = cvCloneImage(img); // make a copy of input image
		IplImage gray = cvCreateImage(sz, 8, 1);
		IplImage tgray;

		// create empty sequence that will contain points -
		CvSeq rectangles = cvCreateSeq(0, Loader.sizeof(CvSeq.class), Loader.sizeof(CvPoint.class), storage);

		// select the maximum ROI in the image
		cvSetImageROI(timg, cvRect(0, 0, sz.width(), sz.height()));

		tgray = cvCreateImage(sz, 8, 1);

		// find squares in every color plane of the image
		for (int color = 0; color < 3; color++) {
			// extract the c-th color plane
			cvSetImageCOI(timg, color + 1);
			cvCopy(timg, tgray);

			tryCannyDetector(storage, gray, tgray, rectangles);
			tryThresholds(storage, gray, tgray, rectangles);
		}

		// release all the temporary images
		cvReleaseImage(gray);
		cvReleaseImage(tgray);
		cvReleaseImage(timg);

		return rectangles;

	}

	/**
	 * this function draws all the squares into the image
	 */
	public IplImage drawSquares(IplImage img, CvSeq rectangles) {
		IplImage cpy = cvCloneImage(img);
		CvSlice slice = new CvSlice(rectangles);

		// read 4 sequence elements at a time (all vertices of a square)
		for (int i = 0; i < rectangles.total(); i += 4) {

			CvPoint rect = new CvPoint(4);
			IntPointer intPointer = new IntPointer(1);
			IntPointer count = intPointer.put(4);

			cvCvtSeqToArray(rectangles, rect, slice.start_index(i).end_index(i + 4));

			cvPolyLine(cpy, rect.position(0), count, 1, 1, CV_RGB(0, 255, 0), LINE_THICKNESS, CV_AA, 0);
			intPointer.close();
		}
		slice.close();
		return cpy;
	}

	private void tryThresholds(CvMemStorage storage, IplImage gray, IplImage tgray, CvSeq rectangles) {
		CvSeq contours;
		for (int thresholdNumber = 1; thresholdNumber < NUMBER_OF_THRESHOLDS_TO_TRY; thresholdNumber++) {
			cvThreshold(tgray, gray, (thresholdNumber + 1) * 255 / NUMBER_OF_THRESHOLDS_TO_TRY, 255, CV_THRESH_BINARY);
			contours = findContours(storage, gray);
			testContours(storage, rectangles, contours);
		}
	}

	private void tryCannyDetector(CvMemStorage storage, IplImage gray, IplImage tgray, CvSeq rectangles) {
		CvSeq contours;
		cvCanny(tgray, gray, 0, 50, 5);
		cvDilate(gray, gray, null, 1);

		contours = findContours(storage, gray);
		testContours(storage, rectangles, contours);
	}

	private CvSeq findContours(CvMemStorage storage, IplImage gray) {
		CvSeq contours = new CvSeq();
		cvFindContours(gray, storage, contours, Loader.sizeof(CvContour.class), CV_RETR_LIST, CV_CHAIN_APPROX_SIMPLE, cvPoint(0, 0));
		return contours;
	}

	private void testContours(CvMemStorage storage, CvSeq rectangles, CvSeq contours) {
		while (contours != null && !contours.isNull()) {

			CvSeq contour = cvApproxPoly(contours, Loader.sizeof(CvContour.class), storage, CV_POLY_APPROX_DP, cvContourPerimeter(contours) * 0.02, 0);

			if (isQuadrangle(contour) && isApprox90Degrees(contour)) {
				saveRectangle(rectangles, contour);
			}

			// take the next contour
			contours = contours.h_next();
		}
	}

	private void saveRectangle(CvSeq squares, CvSeq contour) {
		for (int i = 0; i < 4; i++) {
			cvSeqPush(squares, cvGetSeqElem(contour, i));
		}
	}

private boolean isApprox90Degrees(CvSeq contour) {
	return isMaxCosValid(contour);
}

private boolean isMaxCosValid(CvSeq contour) {
	for (int i = 2; i < 5; i++) {
		double cos = Math.abs(angle(pt(contour, i), pt(contour, i - 2), pt(contour, i - 1)));
		if (cos > COSINE_SIZE_LIMIT) {
			return false;
		}
	}
	return true;
}

	private CvPoint pt(CvSeq contour, int i) {
		return new CvPoint(cvGetSeqElem(contour, i));
	}

	private boolean isQuadrangle(CvSeq result) {
		return have4Vertices(result) && isLarge(result) && isConvex(result);
	}

	private boolean have4Vertices(CvSeq result) {
		return result.total() == 4;
	}

	private boolean isLarge(CvSeq result) {
		return Math.abs(cvContourArea(result, CV_WHOLE_SEQ, 0)) > AREA_MIN_LIMIT;
	}

	private boolean isConvex(CvSeq result) {
		return cvCheckContourConvexity(result) != 0;
	}

	private double angle(CvPoint pt1, CvPoint pt2, CvPoint pt0) {
		double dx1 = pt1.x() - pt0.x();
		double dy1 = pt1.y() - pt0.y();
		double dx2 = pt2.x() - pt0.x();
		double dy2 = pt2.y() - pt0.y();

		return (dx1 * dx2 + dy1 * dy2) / Math.sqrt((dx1 * dx1 + dy1 * dy1) * (dx2 * dx2 + dy2 * dy2) + 1e-10);
	}
}
