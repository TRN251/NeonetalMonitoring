//package ACT.project.NeonetalMonitor.Entity;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.ManyToOne;
//
//@Entity
//public class Message {
//
//    @Id
//    private Long id;
//    @ManyToOne
//    private User sender;
//    @ManyToOne
//    private User receiver;
//
//    private String content;
//
//    public void setSender(User sender) {
//        this.sender = sender;
//    }
//
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public User getSender() {
//        return sender;
//    }
//    public User getReceiver() {
//        return receiver;
//    }
//
//    public void setReceiver(User receiver) {
//        this.receiver = receiver;
//    }
//
//
//
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//
//}