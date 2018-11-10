package sg.app.quantumtask.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dictionary {

    @SerializedName("word")
    @Expose
    private String word;
    @SerializedName("frequency")
    @Expose
    private Integer frequency;

    private boolean status;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}