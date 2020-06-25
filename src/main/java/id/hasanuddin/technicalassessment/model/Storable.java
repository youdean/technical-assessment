package id.hasanuddin.technicalassessment.model;

import java.nio.file.Path;

public interface Storable {
	Path getPath();
    String getFilename();
}
