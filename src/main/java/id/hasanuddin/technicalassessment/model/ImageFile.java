package id.hasanuddin.technicalassessment.model;

import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import id.hasanuddin.technicalassessment.exception.UnsupportedImageTypeException;
import lombok.Getter;

@Getter
public class ImageFile {
	
	private final UUID id = UUID.randomUUID();
	private final MultipartFile file;
	
	public ImageFile(MultipartFile file) {
        validateImage(file);
        this.file = file;
    }
	
	public String name() {
        return file.getOriginalFilename();
    }

	private void validateImage(MultipartFile file) {
        try {
            if (ImageIO.read(file.getInputStream()) == null) {
                throw new UnsupportedImageTypeException(file.getOriginalFilename());
            }
        } catch (IOException e) {
            throw new UnsupportedImageTypeException(file.getOriginalFilename());
        }
    }
}
