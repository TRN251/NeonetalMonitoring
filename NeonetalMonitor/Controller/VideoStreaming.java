package ACT.project.NeonetalMonitor.Controller;

import ACT.project.NeonetalMonitor.Entity.Video;
import ACT.project.NeonetalMonitor.Service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RequestMapping("/video")
public class VideoStreaming {


        @Autowired
        private VideoService videoService;

        @GetMapping("/stream/{videoId}")
        public ResponseEntity<byte[]> streamVideo(@PathVariable int videoId) throws IOException {
            // Get the authenticated user's username (You can access it from the security context)
            String authenticatedUser = "username"; // Replace with actual username retrieval

            // Get the video by its ID
            Video video = videoService.getVideoById(videoId);

            // Check if the authenticated user is allowed to view the video
            if (video != null && video.getAllowedUser().contains(authenticatedUser)) {
                // Load the video file from the resources/static/videos directory
                ClassPathResource videoFile = new ClassPathResource("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository;" + video.getFilepath());

                byte[] videoData = videoFile.getInputStream().readAllBytes();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.valueOf("video/mp4"));
                headers.set("Accept-Ranges", "bytes");

                // Return the video as a stream with a 200 OK status code
                return new ResponseEntity<>(videoData, headers, HttpStatus.OK);
            } else {
                // Return 403 Forbidden if the user is not allowed to view the video
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
    }

