package ACT.project.NeonetalMonitor.Service;

import ACT.project.NeonetalMonitor.Entity.Video;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface VideoService {
    Video saveVideo(Video video, MultipartFile file) throws IOException;
    Video getVideoById(Integer id);

    List<Video> getAllVideos();
}
