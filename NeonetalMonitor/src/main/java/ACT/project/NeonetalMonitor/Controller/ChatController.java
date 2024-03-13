//package ACT.project.NeonetalMonitor.Controller;
//
//import ACT.project.NeonetalMonitor.Entity.Message;
//import ACT.project.NeonetalMonitor.Entity.User;
//import ACT.project.NeonetalMonitor.Service.AdminServiceImplementation;
//import ACT.project.NeonetalMonitor.Service.ChatService;
//import ACT.project.NeonetalMonitor.Service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.DestinationVariable;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Controller;
//
//import java.security.Principal;
//
//@Controller
//public class ChatController {
//    @Autowired
//    private ChatService messagingService;
//    Autowired
//    private SimpleMessagingTemplate messagingTemplate;
//    UserService userService;
//
//    @MessageMapping("/send")
//    public void sendMessage(Message messageDTO) {
//        Principal principal = SecurityContextHolder.getContext().getAuthentication();
//
//        User sender =messageDTO.getSender(); // Retrieve the sender user
//        User receiver =messageDTO.getReceiver(); // Retrieve the receiver user
//
//        if (sender != null && receiver != null) {
//            // Send the message to the receiver's topic
//            messagingTemplate.convertAndSendToUser(
//                    receiver.getUsername(), "/queue/messages", messageDTO);
//        }
//
//        messagingService.sendMessage(sender, receiver, messageDTO.getContent());
//    }
//}
