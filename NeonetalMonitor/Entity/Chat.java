//package ACT.project.NeonetalMonitor.Entity;
//
//import javax.mail.Message;
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//public class Chat {
//    @ManyToMany
//    private Set<User> participants = new HashSet<>();
//
//    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
//    private List<Message> messages = new ArrayList<>();
//}
