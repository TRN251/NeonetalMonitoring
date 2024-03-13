package ACT.project.NeonetalMonitor.Repository;

import ACT.project.NeonetalMonitor.Entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("videoRepository")
public interface VideoRepository extends JpaRepository<Video, Integer> {
    List<Video> findByallowedUser (String allowedUser);

}