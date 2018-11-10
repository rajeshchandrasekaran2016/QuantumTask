package sg.app.quantumtask.view;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import sg.app.quantumtask.R;
import sg.app.quantumtask.adapter.DictionaryListAdapter;
import sg.app.quantumtask.util.CommonUtils;
import sg.app.quantumtask.util.DictionaryParcelable;

public class MainActivity extends AppCompatActivity {

    private final int REQ_CODE_SPEECH_INPUT = 100;
    DictionaryListAdapter dictionaryListAdapter;
    ArrayList<DictionaryParcelable> arraylist;
    List<String> wordList;
    @Nullable
    @BindView(R.id.ll_root_view)
    LinearLayout rootView;
    @Nullable
    @BindView(R.id.rv_dictionary_list)
    RecyclerView dictionaryListView;
    @Nullable
    @BindView(R.id.txtSpeechInput)
    TextView txtSpeechInput;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(MainActivity.this);
        init();
    }

    @OnClick(R.id.btnSpeak)
    public void onSpeakClick(View view) {
        promptSpeechInput();
    }

    private void init() {
        Bundle bundle = getIntent().getExtras();
        arraylist = bundle.getParcelableArrayList("dictionary_list");
        Collections.sort(arraylist, new SortByFrequency());
        wordList = new ArrayList<>();
        for (int i = 0; i < arraylist.size(); i++) {
            wordList.add(arraylist.get(i).getWord().toLowerCase());
        }
        LinearLayoutManager llm = new LinearLayoutManager(this);
        dictionaryListView.setHasFixedSize(true);
        dictionaryListView.setLayoutManager(llm);
        dictionaryListView.setItemAnimator(new DefaultItemAnimator());
        dictionaryListAdapter = new DictionaryListAdapter(this, arraylist);
        dictionaryListView.setAdapter(dictionaryListAdapter);
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    resetHighLight();
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String statement = result.get(0).toLowerCase();
                    if (wordList.contains(statement)) {
                        int count = arraylist.get(wordList.indexOf(statement)).getFrequency();
                        arraylist.get(wordList.indexOf(statement)).setFrequency(count + 1);
                        arraylist.get(wordList.indexOf(statement)).setStatusFlag(true);
                        Collections.sort(arraylist, new SortByFrequency());
                        dictionaryListAdapter.notifyDataSetChanged();
                        dictionaryListView.getLayoutManager().scrollToPosition(wordList.indexOf(statement));
                    } else {
                        Collections.sort(arraylist, new SortByFrequency());
                        dictionaryListAdapter.notifyDataSetChanged();
                        CommonUtils.showErrorSnackBarView(MainActivity.this, rootView, getString(R.string.str_no_match));
                    }
                    txtSpeechInput.setText(result.get(0));
                }
                break;
            }
        }
    }

    private void resetHighLight() {
        wordList.clear();
        for (int i = 0; i < arraylist.size(); i++) {
            arraylist.get(i).setStatusFlag(false);
            wordList.add(arraylist.get(i).getWord().toLowerCase());
        }
    }

    class SortByFrequency implements Comparator<DictionaryParcelable> {
        @Override
        public int compare(DictionaryParcelable t1, DictionaryParcelable t2) {
            return t2.getFrequency() - t1.getFrequency();
        }
    }


}
