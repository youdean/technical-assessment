package id.hasanuddin.technicalassessment.delivery;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import id.hasanuddin.technicalassessment.model.Image;
import id.hasanuddin.technicalassessment.model.OnePage;
import id.hasanuddin.technicalassessment.response.ImageResponse;
import id.hasanuddin.technicalassessment.response.PageResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImagePresenter {
	
	public static ImageResponse getResponse(Image image) {
        return ImageResponse.builder()
                .id(image.getId().toString())
                .filename(image.getFilename())
                .uploadTimestamp(getTimestamp(image.getUploadTimestamp()))
                .build();
    }

	public static PageResponse<ImageResponse> getResponse(OnePage<Image> images) {
        List<ImageResponse> content =  images.getContent().stream()
                .map(ImagePresenter::getResponse)
                .collect(Collectors.toList());
        return PageResponse.<ImageResponse>builder()
                .content(content)
                .totalElements(images.getTotalElements())
                .totalPages(images.getTotalPages())
                .build();
    }
	
	private static long getTimestamp(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }
}
