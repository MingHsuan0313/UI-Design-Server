package ntu.csie.selab.inventorysystem.model;
import javax.persistence.*;
import java.util.Date;

@Entity()
@Table(name = "ItemHistory")
public class ItemHistory {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iid")
    private Item item;
    @Column(name = "date")
    private Date date;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "event")
    private ItemHistoryEvent event;
    @Column(name = "price")
    private Double price;
    @Column(name = "adjust")
    private Integer adjust;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reason")
    private ItemHistoryAdjustReason reason;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "uid")
    private User user;
    @Column(name = "comment")
    private String comment;
    
    public ItemHistory(Item item, User user) {
        this.item = item;
        this.date = new Date();
        this.adjust = 0;
        this.user = user;
    }
    
    public Integer getId() {
        return id;
    }
    
    public Item getItem() {
        return item;
    }
    
    public Date getDate() {
        return date;
    }
    
    public ItemHistoryEvent getEvent() {
        return event;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public Integer getAdjust() {
        return adjust;
    }
    
    public ItemHistoryAdjustReason getReason() {
        return reason;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setItem(Item item) {
        this.item = item;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public void setEvent(ItemHistoryEvent event) {
        this.event = event;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public void setAdjust(Integer adjust) {
        this.adjust = adjust;
    }
    
    public void setReason(ItemHistoryAdjustReason reason) {
        this.reason = reason;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    @Override()
    public String toString() {
        return String.format("History[id=%d, iid=%d, date=\'%s\', event=\'%s\', price=%2f, adjust=%d, reason=\'%s\', user=\'%s\']", id, item.getId(), date.toString(), event.toString(), price, adjust, reason.getReason().equals("Other") ? reason.toString() : comment, user.getUsername());
    }

public void setAdjust(Integer adjust, String data) {
    this.adjust = adjust;
}
}