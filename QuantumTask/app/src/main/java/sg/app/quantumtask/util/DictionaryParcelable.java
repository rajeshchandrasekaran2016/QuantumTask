package sg.app.quantumtask.util;

import android.os.Parcel;
import android.os.Parcelable;

public class DictionaryParcelable implements Parcelable {

    private String word;
    private int frequency;
    private boolean statusFlag;


    public DictionaryParcelable(String word, int frequency) {
        this.word = word;
        this.frequency = frequency;

    }

    public boolean isStatusFlag() {
        return statusFlag;
    }

    public void setStatusFlag(boolean statusFlag) {
        this.statusFlag = statusFlag;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {
        dest.writeInt(frequency);
        dest.writeString(word);
        dest.writeByte((byte) (statusFlag ? 1 : 0));
    }

    public DictionaryParcelable(Parcel in) {
        frequency = in.readInt();
        word = in.readString();
        statusFlag = in.readByte() != 0;
    }

    public static final Parcelable.Creator<DictionaryParcelable> CREATOR = new Parcelable.Creator<DictionaryParcelable>() {
        public DictionaryParcelable createFromParcel(Parcel in) {
            return new DictionaryParcelable(in);
        }

        public DictionaryParcelable[] newArray(int size) {
            return new DictionaryParcelable[size];
        }
    };
}
