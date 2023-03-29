package models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class Case {
    private String title;
    private int status;
    private int severity;
    private int priority;
    private int type;
    private int layer;
    @SerializedName(value = "is_flaky")
    private int isFlaky;
    private int behavior;
    @SerializedName(value = "automation")
    private int automationStatus;
}
