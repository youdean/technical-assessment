package id.hasanuddin.technicalassessment.service;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.imageio.ImageIO;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import id.hasanuddin.technicalassessment.exception.StoreImageException;
import id.hasanuddin.technicalassessment.external.ImageRepository;
import id.hasanuddin.technicalassessment.model.Image;
import id.hasanuddin.technicalassessment.model.Image.Thumbnail;
import id.hasanuddin.technicalassessment.model.ImageFile;
import id.hasanuddin.technicalassessment.model.User;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;

@Slf4j
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	private final Path uploadPath;
	private final Tika mimeTypeDetector = new Tika();
	
	ImageService(ImageRepository imageRepository, @Value("${upload.dir}") Path uploadPath) throws IOException {
        this.imageRepository = imageRepository;
        this.uploadPath = uploadPath.toAbsolutePath();
        Path thumbnailPath = this.uploadPath.resolve(Thumbnail.DIRECTORY);
        log.debug("Create directories for images at: {} and {}", this.uploadPath, thumbnailPath);
        Files.createDirectories(thumbnailPath);
    }

	public Image save(ImageFile imageFile, User user) {
        Image image = getImage(imageFile, user);
        try {
            createAndStoreThumbnail(imageFile, image.getThumbnail());
            storeOriginalImage(imageFile, image);
            return imageRepository.save(image);
        } catch (Exception cleanupEx) {
            try {
                Path thumbnail = image.getThumbnail().getPath();
                Files.deleteIfExists(thumbnail);
                Path fullImage = image.getPath();
                Files.deleteIfExists(fullImage);
            } catch (IOException deleteEx) {
                throw new StoreImageException(deleteEx.initCause(cleanupEx));
            }
            throw new StoreImageException(cleanupEx);
        }
    }

	private Image getImage(ImageFile imageFile, User user) {
        return Image.builder()
                .id(imageFile.getId())
                .filename(imageFile.name())
                .path(uploadPath)
                .ownerId(user.getId())
                .mediaType(getMediaType(imageFile))
                .build();
    }
	
	private MediaType getMediaType(ImageFile imageFile) {
        try {
            return MediaType.valueOf(mimeTypeDetector.detect(imageFile.getFile().getBytes()));
        } catch (IOException e) {
            throw new StoreImageException(e);
        }
    }
	
	private void storeOriginalImage(ImageFile imageFile, Image image) throws IOException {
        imageFile.getFile().transferTo(image.getPath());
    }
	
	private void createAndStoreThumbnail(ImageFile imageFile, Thumbnail image) throws IOException {
        BufferedImage thumbnail = Thumbnails.of(imageFile.getFile().getInputStream())
                .size(Thumbnail.SIZE, Thumbnail.SIZE)
                .asBufferedImage();
        ImageIO.write(thumbnail, Thumbnail.FORMAT, image.getPath().toFile());
    }
}
