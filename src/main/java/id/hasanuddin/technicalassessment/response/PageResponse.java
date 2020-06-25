package id.hasanuddin.technicalassessment.response;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ApiModel(value = "Page")
public class PageResponse<T> {

    @ApiModelProperty(value = "Requested content")
    private final List<T> content;

    @ApiModelProperty(value = "Total number of elements", example = "21")
    private long totalElements;

    @ApiModelProperty(value = "Total number of pages", example = "3")
    private long totalPages;
}
