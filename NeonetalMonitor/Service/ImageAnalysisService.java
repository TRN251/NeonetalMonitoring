package ACT.project.NeonetalMonitor.Service;

import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.springframework.stereotype.Service;

@Service("imageAnalysisService")
public class ImageAnalysisService {
    public Mat convertRGBtoYCbCr(Mat rgbImage) {
        Mat ycbcrImage = new Mat(rgbImage.rows(), rgbImage.cols(), CvType.CV_8UC3);

        for (int row = 0; row < rgbImage.rows(); row++) {
            for (int col = 0; col < rgbImage.cols(); col++) {
                double[] rgbPixel = rgbImage.get(row, col);

                double r = rgbPixel[0];
                double g = rgbPixel[1];
                double b = rgbPixel[2];

                double y = 0.299 * r + 0.587 * g + 0.114 * b;
                double cb = (b - y) * 0.564 + 128;
                double cr = (r - y) * 0.713 + 128;

                ycbcrImage.put(row, col, y, cb, cr);
            }
        }

        return ycbcrImage;
    }
}
