package id.hasanuddin.technicalassessment.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import id.hasanuddin.technicalassessment.delivery.ImagePresenter;
import id.hasanuddin.technicalassessment.model.Image;
import id.hasanuddin.technicalassessment.model.ImageFile;
import id.hasanuddin.technicalassessment.model.User;
import id.hasanuddin.technicalassessment.response.ImageResponse;
import id.hasanuddin.technicalassessment.service.ImageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@Api(tags = "Images")
@RestController
@RequiredArgsConstructor
public class ImageController {

	private final ImageService imageService;

    @PostMapping(value = "/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ImageResponse> handleFileUpload(
            @AuthenticationPrincipal User user,
            @ApiParam(value = "Image file. Supported formats: JPEG, PNG, GIF, BMP and WBMP", required = true)
            @RequestParam("file") MultipartFile file) {
        ImageFile imageFile = new ImageFile(file);
        Image image = imageService.save(imageFile, user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(image.getId()).toUri();
        return ResponseEntity.created(location)
                .body(ImagePresenter.getResponse(image));
    }
}
