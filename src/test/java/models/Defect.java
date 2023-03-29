package models;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "set")
public class Defect {
    private String title;
    @SerializedName(value = "actual_result")
    private String actualResult;
    private int severity;
}
