package seoultech.gdsc.mediMedi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class History {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Medi medi;

    private String memo;

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime registeredAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime modifiedAt;
}
