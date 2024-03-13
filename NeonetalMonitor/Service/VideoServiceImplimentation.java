package ACT.project.NeonetalMonitor.Service;

import ACT.project.NeonetalMonitor.Entity.Video;
import ACT.project.NeonetalMonitor.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service("videoService")
public class VideoServiceImplimentation implements VideoService{

    private VideoRepository videoRepository;

    @Value("${upload.directory}")
    private String uploadDir;

    //inject employee dao
    @Autowired   //Adding bean id @Qualifier
    public VideoServiceImplimentation(VideoRepository vid)
    {
        videoRepository=vid;
    }


    public void  save(Video vid)
    {

        videoRepository.save(vid);
    }


    public List<Video> findAll() {
        return videoRepository.findAll();
    }


    @Override
    public Video saveVideo(Video video, MultipartFile file) throws IOException {

        /*String uploadDir="D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository";
*/
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath);

        video.setFilepath(fileName);
        return videoRepository.save(video);


    /*    String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        String uploadDir = "/VideoRepository";

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath))
        {
            Files.createDirectories(uploadPath);
        }

      try{  InputStream inputStream = file.getInputStream();
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream,filePath, StandardCopyOption.REPLACE_EXISTING);}catch (IOException e) {
          throw new IOException
                  ("FILE COULDNOT BE SAVED");*/
     /* }*/

      /*  video.setFilepath(fileName);*/
    }



    @Override
    public Video getVideoById(Integer id) {
        return videoRepository.findById(id).orElse(null);
    }

    public boolean isUserAllowedToViewVideo(String authenticatedUser, Video video) {
        return video.getAllowedUser().contains(authenticatedUser);
    }


    public  List<Integer> getVideoIdforUsers (String username)
    {
      List<Video> video =  videoRepository.findByallowedUser(username);
      List<Integer> videoIds = new ArrayList<>();
      for (Video videos:video)
      {
          videoIds.add(videos.getVideoID());
      }

     return videoIds;
    }
    @Override
    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}