package ACT.project.NeonetalMonitor;


import nu.pattern.OpenCV;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.nio.charset.CoderResult;

@SpringBootApplication
@EnableAsync
public class NeonetalMonitorApplication {


	public static void main(String[] args)
	{
		SpringApplication.run(NeonetalMonitorApplication.class, args);
		OpenCV.loadShared();

	}

}
