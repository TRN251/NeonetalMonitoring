package ACT.project.NeonetalMonitor.Controller;

import ACT.project.NeonetalMonitor.Entity.*;
import ACT.project.NeonetalMonitor.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
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

import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/user")
public class UserController {

	private AppointmentServiceImplementation appointmentServiceImplementation;

	private AdminServiceImplementation adminServiceImplementation;

	private final VideoServiceImplimentation videoService;
	private final DoctorReportService doctorReportService;

	@Autowired
	public UserController(AppointmentServiceImplementation obj1, AdminServiceImplementation obj, VideoServiceImplimentation obj2, DoctorReportService drs) {
		appointmentServiceImplementation = obj1;
		adminServiceImplementation = obj;
		videoService = obj2;
		doctorReportService = drs;
	}

	@GetMapping("/index")
	public String index(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);
		Video video = new Video();
		model.addAttribute("video", video);

		return "user/index";
	}

	@PostMapping("/save-app")
	public String saveEmploye(@ModelAttribute("app") Appointment obj) {

		appointmentServiceImplementation.save(obj);


		// use a redirect to prevent duplicate submissions
		return "redirect:/user/index";
	}


	@GetMapping("/about")
	public String about(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);

		Video video = new Video();
		model.addAttribute("video", video);

		return "user/about";
	}

	@GetMapping("/blog-single")
	public String bs(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);

		return "user/blog-single";
	}

	@GetMapping("/blog")
	public String blog(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);

		return "user/blog";
	}

	@GetMapping("/contact")
	public String contact(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);

		return "user/contact";
	}


	@GetMapping("/department-single")
	public String d(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);


		return "user/department-single";
	}

	@GetMapping("/departments")
	public String dep(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);

		return "user/departments";
	}

	@GetMapping("/doctor")
	public String doctor(Model model) {

		// get last seen
		String username = "";
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
			String Pass = ((UserDetails) principal).getPassword();
			System.out.println("One + " + username + "   " + Pass);


		} else {
			username = principal.toString();
			System.out.println("Two + " + username);
		}

		Admin admin = adminServiceImplementation.findByEmail(username);

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Date now = new Date();

		String log = now.toString();

		admin.setLastseen(log);

		adminServiceImplementation.save(admin);


		Appointment obj = new Appointment();

		obj.setName(admin.getFirstName() + " " + admin.getLastName());

		obj.setEmail(admin.getEmail());

		System.out.println(obj);

		model.addAttribute("app", obj);


		return "user/doctor";
	}

	@GetMapping("/videoGallery")
	public String videoGallery(Model model) {

		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		String email = principal.getName();
		List<Integer> videoId = videoService.getVideoIdforUsers(email);
	//	int videoId =45;
		model.addAttribute("videoId", videoId);
		return "/user/videoGallery";
	}



	@GetMapping("/stream/{videoId}")
	public ResponseEntity<Resource> streamVideo(@PathVariable int videoId, Principal principal) throws IOException {
		String authenticatedUser = principal.getName();

		Video video = videoService.getVideoById(videoId);

		if (video != null && videoService.isUserAllowedToViewVideo(authenticatedUser, video)) {
			// Load the video file using a Resource (e.g., ByteArrayResource or FileSystemResource)
			Resource videoResource = new ByteArrayResource(Files.readAllBytes(Paths.get("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository;", video.getFilepath())));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.valueOf("video/mp4"));
			headers.set("Accept-Ranges", "bytes");

			// Return the video as a stream with a 200 OK status code
			return new ResponseEntity<>(videoResource, headers, HttpStatus.OK);
		} else {
			// Return 403 Forbidden if the user is not allowed to view the video
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}







	@GetMapping("/Report")
	 public String Report(Model model) {

		Principal principal = SecurityContextHolder.getContext().getAuthentication();
		String email = principal.getName();
		 List<DoctorReport> reports = doctorReportService.findDoctorReportByAllowedUser(email);
		if (!reports.isEmpty()) {

			model.addAttribute("Report", reports);
		}
		return "user/ReportView";

	 }

	/*@GetMapping("/Form/{reportId}")
	public ResponseEntity<Resource> streamVideo(@PathVariable int videoId, Principal principal) throws IOException {
		String authenticatedUser = principal.getName();

		Video video = videoService.getVideoById(videoId);

		if (video != null && videoService.isUserAllowedToViewVideo(authenticatedUser, video)) {
			// Load the video file using a Resource (e.g., ByteArrayResource or FileSystemResource)
			Resource videoResource = new ByteArrayResource(Files.readAllBytes(Paths.get("D:\\ACT\\Thesis Project ideas\\NeonetalMonitor (2)\\VideoRepository;", video.getFilepath())));

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.valueOf("video/mp4"));
			headers.set("Accept-Ranges", "bytes");

			// Return the video as a stream with a 200 OK status code
			return new ResponseEntity<>(videoResource, headers, HttpStatus.OK);
		} else {
			// Return 403 Forbidden if the user is not allowed to view the video
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
	}*/

	/* @GetMapping("/videoList")
	public List<Video> getAllVideosForUser(String username) {

		//List<Video> allVideos = videoRepository.findAll();
		return  videoService.findAll().stream()
				.filter(video -> video.getAllowedUser().contains(username))
				.collect(Collectors.toList());
	}*/

 /*   @GetMapping("/upload")
    public String showUploadForm(Model model) {
        model.addAttribute("video", new Video());
        return "redirect:/index";
        *//*return "video/upload";*//*
    }*/
}