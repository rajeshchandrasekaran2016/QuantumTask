package sg.app.quantumtask.beans;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DictionaryResponse {

    @SerializedName("dictionary")
    @Expose
    private ArrayList<Dictionary> dictionary = null;

    public ArrayList<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(ArrayList<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

}