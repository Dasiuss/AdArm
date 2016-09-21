package detection;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.VideoInputFrameGrabber;

public class WebCam {

	private FrameGrabber grabber;

	public WebCam() {
		grabber = new VideoInputFrameGrabber(0);

		try {
			grabber.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Frame getFrame() throws org.bytedeco.javacv.FrameGrabber.Exception {
		return grabber.grab();
	}

}
