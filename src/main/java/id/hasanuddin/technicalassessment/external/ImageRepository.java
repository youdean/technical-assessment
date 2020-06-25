package id.hasanuddin.technicalassessment.external;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import id.hasanuddin.technicalassessment.model.Image;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ImageRepository {
	
	private final JpaImageRepository repository;
	private final ImageConverter imageConverter;
	
	public Image save(Image image) {
        ImageEntity persisted = repository.save(imageConverter.toEntity(image));
        return imageConverter.fromEntity(persisted);
    }

}

interface JpaImageRepository extends PagingAndSortingRepository<ImageEntity, UUID> {
    Optional<ImageEntity> findByUserAndId(UserEntity user, UUID id);
    Page<ImageEntity> findAllByUserAndFilenameIgnoreCaseContaining(UserEntity user, String filename, Pageable pageable);
}