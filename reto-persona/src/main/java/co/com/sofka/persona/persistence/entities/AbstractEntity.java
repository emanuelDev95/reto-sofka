package co.com.sofka.persona.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor
public abstract class AbstractEntity<K>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private K id;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    public AbstractEntity(K id) {
        this.id = id;
    }
}
