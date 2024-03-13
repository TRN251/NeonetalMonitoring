package ACT.project.NeonetalMonitor.Controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.math3.stat.descriptive.moment.Kurtosis;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.KNearest;
import org.opencv.ml.Ml;
import org.opencv.ml.SVM;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;



@Controller
@RequestMapping("/detection")
public class FaceDetectController {






    BufferedImage face;
    BufferedImage skin;
    BufferedImage skinbalanced;

    String imageBase64 ;
    Model model;


    @PostMapping("/upload")
    public String uploadVideo(@RequestParam("filepath") String filepath,
                              Model model) throws IOException {

        String path = "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository;\\" + filepath;
        String directoryPath = "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\Frames";
        //  List<Mat> detectedFrames = detectFaces(path);

        Mat fac =detectFaces(path,model);
        BufferedImage facI = ConvertMat2Image(fac);


        model.addAttribute("img", Convert2imageBase64(facI) );


        List<BufferedImage> skins = new ArrayList<>();
       /* for (Mat mat : detectedface) {
            skins.add(ConvertMat2Image(skinDetection(mat)));
            //  jaundiceDetection(skinDetection(mat));
        }*/
        Mat BalancedSkin = skinDetection(fac,model);
        double Biluribon =  jaundiceDetection(BalancedSkin);
        skinbalanced = ConvertMat2Image(BalancedSkin);
        model.addAttribute("skin",Convert2imageBase64(skin));
        saveImagesAsJpeg(skin, directoryPath);

        model.addAttribute("bilirubin",Biluribon);
        //   model.addAttribute("skins",Convert2imageBase64(skins.get(0)));
        return "/doctor/reviewJaundice";
    }

    public Mat detectFaces(String filePath,Model model) throws IOException {

        VideoCapture videoCapture = new VideoCapture();
        videoCapture.open(filePath);

        if (videoCapture.isOpened()) {
            Mat frame = new Mat();
            CascadeClassifier faceDetector = new CascadeClassifier("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\NeonetalMonitor\\data\\haarcascade_frontalface_default.xml");
            CascadeClassifier eyeDetector = new CascadeClassifier("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\NeonetalMonitor\\data\\haarcascade_eye.xml");

            Integer frames = 4;

            List<Mat> detectedface = null;
            List<BufferedImage> imageList = null;
            List<BufferedImage> bff= null;

            while (videoCapture.read(frame)) {
                frames = frames - 1;
                if (frames <= 0) {
                    break;
                }

                MatOfRect faceDetections = new MatOfRect();
                faceDetector.detectMultiScale(frame, faceDetections);
                detectedface = new ArrayList<>();
                imageList = new ArrayList<>();
                bff = new ArrayList<>();
                List<Mat> detectedFrames = new ArrayList<>();

                for (Rect rect : faceDetections.toArray()) {
                    Mat roi = new Mat(frame, rect);
                    Mat croppedFace = roi.clone();
                    face = ConvertMat2Image(croppedFace);

                    // Add the cropped face image to the list
                    bff.add(face);
                    detectedface.add(croppedFace);

                    Imgproc.putText(frame, "Face", new Point(rect.x, rect.y - 5), 1, 2, new Scalar(0, 0, 255));
                    Imgproc.rectangle(frame, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 100, 0), 3);
                }

                detectedFrames.add(frame);
                BufferedImage img = ConvertMat2Image(frame);
                saveImageAsJpeg(bff, "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\imgs\\faces");
                imageList.add(img);
            }
            saveImageAsJpeg(imageList, "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\imgs\\Detected Frames");
           // model.addAttribute("face", Convert2imageBase64(bff.get(0)));
            model.addAttribute("frames", Convert2ListimageBase64(imageList));
            videoCapture.release();


            //  return detectedFrames;
           // return detectedface.get(0);
            return frame;
        } else {
            System.out.println("video cnt be found");
            return null;
        }
    }

    public double[] calculateFeatures(Mat image) {
        int width = image.cols();
        int height = image.rows();

        // Calculate average color values
        Scalar avgColor = Core.mean(image);
        double avgRed = avgColor.val[0];
        double avgGreen = avgColor.val[1];
        double avgBlue = avgColor.val[2];

        // Calculate color difference
        double colorDifference = Math.sqrt(Math.pow(avgRed - avgGreen, 2) +
                Math.pow(avgGreen - avgBlue, 2) +
                Math.pow(avgBlue - avgRed, 2));

        // Convert image to grayscale for gradient calculation
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);

        // Calculate gradients using Sobel filters
        Mat gradX = new Mat();
        Mat gradY = new Mat();
        Imgproc.Sobel(grayImage, gradX, CvType.CV_32F, 1, 0);
        Imgproc.Sobel(grayImage, gradY, CvType.CV_32F, 0, 1);

        // Calculate gradient magnitude and orientation
        Mat gradMagnitude = new Mat();
        Mat gradOrientation = new Mat();
        Core.cartToPolar(gradX, gradY, gradMagnitude, gradOrientation, false);

        // Calculate average gradient magnitude
        Scalar avgMagnitude = Core.mean(gradMagnitude);
        double avgGradientMagnitude = avgMagnitude.val[0];

        // Convert gradients to MatOfFloat to use in machine learning
        MatOfFloat gradientMagnitudeVector = new MatOfFloat();
        gradMagnitude.convertTo(gradientMagnitudeVector, CvType.CV_32F);

        // Create a feature array
        double[] features = {
                avgRed, avgGreen, avgBlue,
                colorDifference,
                avgGradientMagnitude
        };

        return features;
    }

    public Mat createTraining() {


        // Directory containing your image files
        String imagesDirectory = "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\training data";


        File directory = new File(imagesDirectory);
        File[] imageFiles = directory.listFiles();
        int numFeatures = 5; // Number of features extracted by calculateFeatures
        int numImages = imageFiles.length;
        Mat trainingDataMat = new Mat(numImages, numFeatures, CvType.CV_32F);
        if (imageFiles != null) {
            /*List<double[]> featuresList = new ArrayList<>();
            List<Integer> labelsList = new ArrayList<>();*/


            for (int i = 0; i < imageFiles.length; i++) {
                String imagePath = imageFiles[i].getAbsolutePath();
                Mat image = Imgcodecs.imread(imagePath);

                double[] features = calculateFeatures(image);

                for (int j = 0; j < numFeatures; j++) {
                    trainingDataMat.put(i, j, features[j]);
                }
            }

        }
        return trainingDataMat;
    }


    public Mat createlabel() {

        // Directory containing your image files
        String imagesDirectory = "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\training data";


        File directory = new File(imagesDirectory);
        File[] imageFiles = directory.listFiles();
        int numFeatures = 5; // Number of features extracted by calculateFeatures
        int numImages = imageFiles.length;
        Mat labelsMat = new Mat(numImages, 1, CvType.CV_32F);
        if (imageFiles != null) {

            String[] imagePaths = new String[imageFiles.length];
            int[] labels = new int[imageFiles.length];

            for (int i = 0; i < imageFiles.length; i++) {
                imagePaths[i] = imageFiles[i].getAbsolutePath();
                labels[i] = imagePaths[i].contains("Jaundice") ? 1 : 0;
            }
            for (int i = 0; i < numImages; i++) {
                labelsMat.put(i, 0, labels[i]);
            }


        }
        return labelsMat;
    }


    public double performKNN(double[] features, Mat trainingDataMat, Mat labelsMat) {

        // Create and train the kNN model
        KNearest knn = KNearest.create();
        knn.train(trainingDataMat, Ml.ROW_SAMPLE, labelsMat);

        // Perform kNN prediction
        Mat testData = new Mat(1, features.length, CvType.CV_32F);
        for (int i = 0; i < features.length; i++) {
            testData.put(0, i, features[i]);
        }

        Mat results = new Mat();
        Mat neighborResponses = new Mat();
        Mat dist = new Mat();

        knn.findNearest(testData, knn.getAlgorithmType(), results, neighborResponses, dist);

        return results.get(0, 0)[0]; // Predicted value
    }

    public double performSVR(double[] features, Mat trainingDataMat, Mat labelsMat, double nuValue) {


        // Create and train the SVR model
        SVM svm = SVM.create();
        svm.setType(SVM.NU_SVR);
        svm.setKernel(SVM.LINEAR);
        svm.setNu(Math.max(0.0, Math.min(1.0, nuValue)));
        svm.train(trainingDataMat, Ml.ROW_SAMPLE, labelsMat);

        // Perform SVR prediction
        Mat testData = new Mat(1, features.length, CvType.CV_32F);
        for (int i = 0; i < features.length; i++) {
            testData.put(0, i, features[i]);
        }

        return svm.predict(testData);
    }


    private BufferedImage ConvertMat2Image(Mat mat) {
        MatOfByte imageByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, imageByte);
        byte[] byteArray = imageByte.toArray();
        BufferedImage image = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            image = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return image;
        // return byteArray;
    }


    private void saveImageAsJpeg(List<BufferedImage> images, String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        for (int i = 0; i < images.size(); i++) {
            BufferedImage image = images.get(i);
            String imagePath = directoryPath + "\\frame_" + i + ".jpeg";
            File imageFile = new File(imagePath);
            ImageIO.write(image, "jpeg", imageFile);
        }
    }

    private void saveImagesAsJpeg(BufferedImage images, String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String imagePath = directoryPath + "\\frame_" + 0 + ".jpeg";
        File imageFile = new File(imagePath);
        ImageIO.write(images, "jpeg", imageFile);
    }


    public Mat skinDetection(Mat image,Model model) throws IOException {

        Mat ycrcbImage = new Mat();
        Imgproc.cvtColor(image, ycrcbImage, Imgproc.COLOR_BGR2YCrCb);

        Mat skinMask = new Mat();
        Core.inRange(ycrcbImage, new Scalar(0, 133, 77), new Scalar(255, 173, 127), skinMask);

        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(5, 5));
        Imgproc.erode(skinMask, skinMask, kernel);
        Imgproc.dilate(skinMask, skinMask, kernel);

        Mat skinDetected = new Mat();
        Core.bitwise_and(image, image, skinDetected, skinMask);

        /*model.addAttribute("originalImage", image);
        model.addAttribute("skinDetected", skinDetected);*/
        skin = ConvertMat2Image(skinDetected);
     //   saveImagesAsJpeg(skin, "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\imgs\\skin");

        skinbalanced = performWhiteBalancing(skin);
        saveImagesAsJpeg(skinbalanced, "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\imgs\\balanced");
        model.addAttribute("balanced",Convert2imageBase64(skinbalanced));
   //     saveImagesAsJpeg(skinbalanced, "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\imgs\\balanced");
        // Mat skinDetecte = Imgcodecs.imread("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\imgs\\balanced\\frame_0.jpeg");

        return skinDetected;
    }

    public  BufferedImage performWhiteBalancing(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        // Calculate average intensity for each color channel
        double avgRed = 0, avgGreen = 0, avgBlue = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                avgRed += red;
                avgGreen += green;
                avgBlue += blue;
            }
        }

        int numPixels = width * height;
        avgRed /= numPixels;
        avgGreen /= numPixels;
        avgBlue /= numPixels;

        // Apply white balancing by scaling color channels
        BufferedImage balancedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                // Scale channels
                red = (int) (red * (avgGreen / avgRed));
                green = (int) (green * (avgGreen / avgGreen));
                blue = (int) (blue * (avgGreen / avgBlue));

                int newPixel = (red << 16) | (green << 8) | blue;
                balancedImage.setRGB(x, y, newPixel);
            }
        }

        return balancedImage;
    }


    public double jaundiceDetection(Mat mat) {

        double[] features = calculateFeatures(mat);
        Mat trainingdata = createTraining();
        Mat label = createlabel();//Create training data set and labels
        double kNNResult = performKNN(features, trainingdata, label);
        double SVRResult = performSVR(features, trainingdata, label, 0.8);

        // Stage 4: Bilirubin Level Estimation
        double threshold = 1.91;
        double maxOutput = Math.max(kNNResult, SVRResult);
        double minOutput = Math.min(kNNResult, SVRResult);

        double bilirubinEstimation;
        if (maxOutput - minOutput < threshold) {
            bilirubinEstimation = (maxOutput + minOutput) / 2;
        } else {
            bilirubinEstimation = minOutput + 0.9 * (maxOutput - minOutput);
        }

        System.out.println("Estimated Bilirubin Level: " + bilirubinEstimation);
        return bilirubinEstimation;
    }
    public String Convert2imageBase64(BufferedImage bufferedImage) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, "png", baos);
        imageBase64 = java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
        return imageBase64;

    }

    public List<String> Convert2ListimageBase64(List<BufferedImage> bufferedImage) throws IOException {
        List<String> baseList = new ArrayList<>();
        for(BufferedImage fr: bufferedImage) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(fr, "png", baos);
            imageBase64 = java.util.Base64.getEncoder().encodeToString(baos.toByteArray());
            baseList.add(imageBase64);
        }
        return baseList;
    }

}

     /*

    private void calculateKurtosis(Mat channel) {
        *//*double[] pixelValues = new double[channel.rows() * channel.cols()];
        channel.get(0, 0, pixelValues);

        Kurtosis kurtosis = new Kurtosis();
        return kurtosis.evaluate(pixelValues);*//*
        int numRows = channel.rows();
        int numCols = channel.cols();
        double[] pixelValues = new double[numRows * numCols];


        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                double pixelValue = channel.get(row, col)[0]; // Get the pixel value
                System.out.println("Pixel value at (" + row + ", " + col + "): " + pixelValue);
                pixelValues[row * numCols + col] = channel.get(row, col)[0];

            }
        }
        System.out.println(pixelValues);
        Kurtosis kurtosis = new Kurtosis();
        double kurtosisValue = kurtosis.evaluate(pixelValues);
        System.out.println("Kurtosis value: " + kurtosisValue);

    }
}


*/

/*
    @GetMapping("/playReview")
    public ResponseEntity<StreamingResponseBody> videoOut(List<BufferedImage> img) {

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body((outputStream) -> {
                    for (BufferedImage image1 : img) {
                        try {
                            ImageIO.write(image1, "jpg", outputStream);
                            outputStream.flush();
                            // You might need to introduce a delay here to control frame rate
                            Thread.sleep(8000); // Adjust the delay as needed
                        } catch (IOException e)//| InterruptedException e)
                        {
                            e.printStackTrace();
                            break;
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });

    }
}*/

   /* @PostMapping("/processing")
    public String process(String videoPath, Model model)
    {
        String path= "D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository;\\"+videoPath;
        VideoCapture videoDevice = new VideoCapture(path); // Open the default camera (webcam)
        if (videoDevice.isOpened()) {
            Mat frameCapture = new Mat();
            MatOfRect faceDetections = new MatOfRect();
            MatOfRect eyeDetection = new MatOfRect();
            CascadeClassifier faceDetector = new CascadeClassifier("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\NeonetalMonitor\\data\\haarcascade_frontalface_default.xml");
            CascadeClassifier eyeDetector = new CascadeClassifier("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\NeonetalMonitor\\data\\haarcascade_eye.xml");

            while (true && !isExitButtonPressed) {
                videoDevice.read(frameCapture);
                if (frameCapture.empty()) {
                    break;

                }

                faceDetector.detectMultiScale(frameCapture, faceDetections);
                eyeDetector.detectMultiScale(frameCapture, eyeDetection);

                // Draw rectangles and labels on the frame
                for (Rect rect : faceDetections.toArray()) {
                    Imgproc.putText(frameCapture, "Face", new Point(rect.x, rect.y - 5), 1, 2, new Scalar(0, 0, 255));
                    Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 100, 0), 3);
                }

                for (Rect rect : eyeDetection.toArray()) {
                    Imgproc.putText(frameCapture, "Eye", new Point(rect.x, rect.y - 5), 1, 2, new Scalar(0, 0, 255));

                    Imgproc.rectangle(frameCapture, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(200, 200, 100), 2);
                }

                BufferedImage image = ConvertMat2Image(frameCapture);
                if (image != null) {
                    imageList.add(image);
                }

                // PushImage(ConvertMat2Image(frameCapture));

            }
        }
         else {
            System.out.println("Camera not available.");
        }
        return "redirect:/doctor/selectVideo"; // Redirect to video streaming page
    }*/

 /* Mat ycbcrImage = new Mat();
        Imgproc.cvtColor(facemat, ycbcrImage, Imgproc.COLOR_BGR2YCrCb);*/

// Mat image = Imgcodecs.imdecode(new MatOfByte(imageBytes), Imgcodecs.IMREAD_COLOR);
       /* Mat ycbcrImage = new Mat();
        Imgproc.cvtColor(mat, ycbcrImage, Imgproc.COLOR_BGR2YCrCb);

        // Split the YCbCr channels
        List<Mat> channels = new ArrayList<>();
        Core.split(ycbcrImage, channels);

        // Get the Cb channel
        Mat cbChannel = channels.get(2);
        System.out.println(cbChannel);
        System.out.println(cbChannel.rows());
        System.out.println(cbChannel.cols());*/

// Calculate kurtosis for the Cb channel
//double kurtosisCb = calculateKurtosis(cbChannel);
//  calculateKurtosis(cbChannel);
        /*Mat grayimage = new Mat();
        Imgproc.cvtColor(facemat, grayimage , Imgproc.COLOR_BGR2YCrCb);

        List<Mat> channels = new ArrayList<>();
        Core.split(grayimage, channels);

        Mat crChannel = channels.get(1); // Cr channel
        Mat cbChannel = channels.get(2); // Cb channel

        double kurtosisCb = calculateKurtosis(cbChannel);

        double kurtosisCr = calculateKurtosis(crChannel);
        System.out.println(kurtosisCb);
        System.out.println(kurtosisCr);*/

      /*  int totalPixels = croppedFrame.rows() * croppedFrame.cols();
        Scalar yellowThreshold = new Scalar(100);



        int yellowPixelsCb = Core.countNonZero(cbChannel, yellowThreshold);
        int yellowPixelsCr = Core.countNonZero(crChannel, yellowThreshold);

        double yellowPercentageCb = (double) yellowPixelsCb / totalPixels * 100;
        double yellowPercentageCr = (double) yellowPixelsCr / totalPixels * 100;

        System.out.println("Yellow Percentage (Cb): " + yellowPercentageCb);
        System.out.println("Yellow Percentage (Cr): " + yellowPercentageCr);

*/
       /* Mat yellowMask = new Mat();
        Core.inRange(crChannel, minCrValue, maxCrValue, yellowMask);

        Mat yellowMask = new Mat();
        Core.inRange(ycbcrImage, new Scalar(minY, minCb, minCr), new Scalar(maxY, maxCb, maxCr), yellowMask);


        double yellowPixelCount = Core.countNonZero(yellowMask);

        double totalPixelCount = yellowMask.rows() * yellowMask.cols();
        double yellowPixelPercentage = (yellowPixelCount / totalPixelCount) * 100.0;*/

// You can now use 'yellowPixelPercentage' to determine the presence of jaundice.

  /*  private BufferedImage ConvertMat2Image(Mat mat) {
        MatOfByte imageByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, imageByte);
        byte[] byteArray = imageByte.toArray();
        BufferedImage image = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            image = ImageIO.read(in);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return image;
    }

    @GetMapping("/playReview")
    public ResponseEntity<StreamingResponseBody> videoOut() {

                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body((outputStream) -> {
                            for (BufferedImage image1 : imageList) {
                                try {
                                    ImageIO.write(image1, "jpg", outputStream);
                                    outputStream.flush();
                                    // You might need to introduce a delay here to control frame rate
                                    Thread.sleep(8000); // Adjust the delay as needed
                                } catch (IOException e)//| InterruptedException e)
                                {
                                    e.printStackTrace();
                                    break;
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });

            }*/












