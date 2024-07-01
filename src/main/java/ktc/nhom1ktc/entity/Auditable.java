//package ktc.nhom1ktc.entity;
//
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.data.annotation.CreatedBy;
//import org.springframework.data.annotation.LastModifiedBy;
//import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//
//import java.util.Date;
//
//@MappedSuperclass
//@EntityListeners(AuditingEntityListener.class)
//@Getter
//@Setter
//public abstract class Auditable {
//
//    @CreatedBy
//    @Column(name = "created_by", updatable = false)
//    private String createdBy;
//
//    @LastModifiedBy
//    @Column(name = "updated_by")
//    private String updatedBy;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "created_at", updatable = false)
//    private Date createdAt;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    @Column(name = "updated_at")
//    private Date updatedAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = new Date();
//    }
//
//    @PreUpdate
//    protected void onUpdate() {
//        updatedAt = new Date();
//    }
//}
