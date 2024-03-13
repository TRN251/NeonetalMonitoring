package ACT.project.NeonetalMonitor.Entity;

import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "video")
public class Video {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "VideoID")
    private int id;


    @Column(name = "Title")
    @NotEmpty(message = "Please provide title of video")
    private String title;

    @Column(name = "Description")
    @NotEmpty(message = "Please provide description of video")
    private String description;

    @Column(name = "Filepath")
    @NotEmpty(message = "Please provide file path of video")
    private String filepath;

    @Column(name = "allowed_user")
    @NotEmpty(message = "Please provide User that view this video")
    private String allowedUser;


    public String getAllowedUser() {
        return allowedUser;
    }

    public void setAllowedUser(String allowedUser) {
        this.allowedUser = allowedUser;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }


    public int getVideoID() {
        return id;
    }

    public void setVideID(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Video [id=" + id + ", Title=" + title + ", Description=" + description +", Filepath=" + filepath
                + ", allowedUser="+ allowedUser +" ]";
    }



}
