package id.hasanuddin.technicalassessment.model;

import org.springframework.http.MediaType;

public interface Media extends Storable {
    MediaType getMediaType();
}
