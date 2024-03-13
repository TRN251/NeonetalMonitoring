package ACT.project.NeonetalMonitor.Controller;

import ACT.project.NeonetalMonitor.Entity.*;
import ACT.project.NeonetalMonitor.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/nurse")
public class NurseController {


    private UserService userService;

    private AdminServiceImplementation adminServiceImplementation;



    private VideoServiceImplimentation videoService;

    private DoctorReportService doctorReportService;
    private DoctorOrderSheetService doctorService;

    private NurseReportServices nurseReportServices;


    @Autowired
    public NurseController(UserService userService, AdminServiceImplementation obj,
                           VideoServiceImplimentation video, DoctorReportService rpt,NurseReportServices nurseReportServices, DoctorOrderSheetService doctorService) {
        this.userService = userService;
        adminServiceImplementation=obj;
        videoService = video;
        doctorReportService = rpt;
        this.nurseReportServices = nurseReportServices;
        this.doctorService = doctorService;

    }


    @RequestMapping("/index")
    public String index(Model model){



        // get last seen
        String username="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            String Pass = ((UserDetails)principal).getPassword();
            System.out.println("One + "+username+"   "+Pass);


        } else {
            username = principal.toString();
            System.out.println("Two + "+username);
        }

        Admin admin = adminServiceImplementation.findByEmail(username);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();

        String log=now.toString();

        admin.setLastseen(log);

        adminServiceImplementation.save(admin);





        model.addAttribute("name",admin.getFirstName());

        model.addAttribute("email",admin.getEmail());


        model.addAttribute("user",admin.getFirstName()+" "+admin.getLastName());

      //List<NurseReports> nurseReports = nurseReportServices.getAllNurseReprot();

        List<Admin> adm = adminServiceImplementation.findByRole("ROLE_USER");

model.addAttribute("nurses",adm);


        return "nurse/index";
    }

    @GetMapping("/report-form")
    public String showReportForm(Model model) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String email = principal.getName();
        Admin admin = adminServiceImplementation.findByEmail(email);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();

        String log = now.toString();

        admin.setLastseen(log);

        adminServiceImplementation.save(admin);

        model.addAttribute("name",admin.getFirstName());

        model.addAttribute("email",admin.getEmail());


        model.addAttribute("user",admin.getFirstName()+" "+admin.getLastName());

        NurseReports obj = new NurseReports();

        obj.setNursename(admin.getFirstName() + " " + admin.getLastName());

        obj.setReportDate(log);

        System.out.println(obj);
        model.addAttribute("nurseReport", obj);
        return "/nurse/submitReport";
    }
    @PostMapping("/save-report")
    public String saveReport(@ModelAttribute("doctorReport") NurseReports report) {

        nurseReportServices.saveNurseReport(report);
        return "redirect:/nurse/index";
    }

    @GetMapping("/add-video")
    public String showFormForAdd(Model theModel) {


        // get last seen
        String username="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            String Pass = ((UserDetails)principal).getPassword();
            System.out.println("One + "+username+"   "+Pass);


        } else {
            username = principal.toString();
            System.out.println("Two + "+username);
        }

        Admin admin1 = adminServiceImplementation.findByEmail(username);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();

        String log=now.toString();

        admin1.setLastseen(log);

        adminServiceImplementation.save(admin1);

        theModel.addAttribute("name",admin1.getFirstName());

        theModel.addAttribute("email",admin1.getEmail());


        theModel.addAttribute("user",admin1.getFirstName()+" "+admin1.getLastName());

        // create model attribute to bind form data
        Video video = new Video();

        theModel.addAttribute("video", video);

        return "nurse/addVideo";
    }


    @PostMapping("/save-video")
    public String saveEmployee(@ModelAttribute("video") Video video, @RequestParam("videoFile") MultipartFile videoFile) throws IOException {

        // save the employee
        //	admin.setId(0);

        videoService.saveVideo(video, videoFile);
        videoService.save(video);
        return "redirect:/nurse/index";
    }

    @GetMapping("/viewVideo")
    public String requestFram(Model model)
    {
        // get last seen
        String username="";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            String Pass = ((UserDetails)principal).getPassword();
            System.out.println("One + "+username+"   "+Pass);


        } else {
            username = principal.toString();
            System.out.println("Two + "+username);
        }

        Admin admin = adminServiceImplementation.findByEmail(username);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date now = new Date();

        String log=now.toString();

        admin.setLastseen(log);

        adminServiceImplementation.save(admin);
        model.addAttribute("name",admin.getFirstName());

        model.addAttribute("email",admin.getEmail());


        model.addAttribute("user",admin.getFirstName()+" "+admin.getLastName());


        List<Video> vid = videoService.findAll();
        model.addAttribute("vid",vid);

        return "/nurse/selectVideo";
    }
    @GetMapping("/stream/{videoId}")
    public ResponseEntity<Resource> streamVideo(@PathVariable int videoId) throws IOException {

        Video video = videoService.getVideoById(videoId);

        // Load the video file using a Resource (e.g., ByteArrayResource or FileSystemResource)
        Resource videoResource = new ByteArrayResource(Files.readAllBytes(Paths.get("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository;", video.getFilepath())));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.set("Accept-Ranges", "bytes");

        // Return the video as a stream with a 200 OK status code
        return new ResponseEntity<>(videoResource, headers, HttpStatus.OK);
    }


    @GetMapping("/selectReport")
    public String Report(Model model) {

        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        String email = principal.getName();
        List<DoctorsOrderSheet> reports = doctorService.getAlldoctorReports();
        if (!reports.isEmpty()) {

            model.addAttribute("Report", reports);
        }
        return "nurse/selectReport";

    }

}
