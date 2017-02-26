package dk.dren.lightmotion.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.File;
import java.util.List;

/**
 * All of the light motion specific configuration.
 */
@Data
public class LightMotionConfig {

    /**
     * The cameras to pull from
     */
    @NotEmpty
    @JsonProperty
    private List<CameraConfig> cameras;

    /**
     * The minimum interval between polling of jpeg snapshots from each camera in milliseconds.
     */
    @JsonProperty
    private Integer pollInterval = 2000;

    /**
     * The directory to store temporary files in, each camera will record into this directory, so there will be a lot
     * of sequential writing to a number of files in parallel, so it might eat an SSD too quickly.
     *
     * Default is to use /run/user/${uid}/light-motion if possible or /tmp/light-motion otherwise
     */
    @JsonProperty
    private File workingRoot = TmpFsFinder.getDefaultTmpFs();

    /**
     * The directory where the long-term state is stored.
     *
     * Default is to use ${HOME}/.light-motion/state
     */
    @JsonProperty
    private File stateRoot = new File(System.getProperty("user.home"), ".light-motion/state");

    /**
     * The directory where the recordings are written before motion is detected.
     *
     * 12 MB per minute per camera is written here.
     *
     * Default is to use ${HOME}/.light-motion/pre-record
     */
    @JsonProperty
    private File chunkRoot = new File(System.getProperty("user.home"), ".light-motion/chunks");

    /**
     * The directory where the recordings are written when motion is detected.
     *
     * Default is to use ${HOME}/.light-motion/recordings
     */
    @JsonProperty
    private File recordingRoot = new File(System.getProperty("user.home"), ".light-motion/recording");


    /**
     * The number of seconds to record in each chunk, default is 60 seconds.
     *
     * The minimum recording size will be chunkLength*(chunksBeforeDetection+chunksAfterDetection) seconds long
     */
    @JsonProperty
    private final Integer chunkLength = 60;

    /**
     * The number of chunks to keep before movement was detected
     *
     * The minimum recording size will be chunkLength*(chunksBeforeDetection+chunksAfterDetection) seconds long
     */
    @JsonProperty
    private final Integer chunksBeforeDetection = 2;

    /**
     * The number of chunks to keep after movement was no-longer detected
     *
     * The minimum recording size will be chunkLength*(chunksBeforeDetection+chunksAfterDetection) seconds long
     */
    @JsonProperty
    private final Integer chunksAfterDetection = 2;

}
