package id.hasanuddin.technicalassessment.external;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Tolerate;

@Getter
@Setter
@Builder
@Entity(name = "images")
public class ImageEntity {

    @Id
    private UUID id;

    private String filename;

    @Column(nullable = false, updatable = false)
    private String mediaType;

    @Column(nullable = false, updatable = false)
    private LocalDateTime uploadTimestamp;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private UserEntity user;

    @Tolerate
    ImageEntity() {
        
    }

}