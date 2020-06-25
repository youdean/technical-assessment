package id.hasanuddin.technicalassessment.model;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class OnePage<T> {
    private List<T> content;
    private long totalElements;
    private long totalPages;
}
