package sg.app.quantumtask.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import sg.app.quantumtask.R;
import sg.app.quantumtask.util.DictionaryParcelable;

public class DictionaryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<DictionaryParcelable> dictionaryList;

    public DictionaryListAdapter(Context context,
                                 ArrayList<DictionaryParcelable> dictionaryList) {
        this.context = context;
        this.dictionaryList = dictionaryList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.tv_dictionary_word)
        TextView tvWord;
        @BindView(R.id.tv_dictionary_word_count)
        TextView tvCount;
        @BindView(R.id.ll_card_view_bg)
        LinearLayout llCardView;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_dictionary,
                parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder tableViewHolder = (MyViewHolder) holder;
        String word = dictionaryList.get(position).getWord();
        String upperWordStr = word.substring(0,1).toUpperCase() + word.substring(1);
        tableViewHolder.tvWord.setText(upperWordStr);
        tableViewHolder.tvCount.setText("" + dictionaryList.get(position).getFrequency());
        if (dictionaryList.get(position).isStatusFlag()) {
            tableViewHolder.llCardView.setBackgroundResource(R.drawable.highlight_bg);
        } else {
            tableViewHolder.llCardView.setBackgroundResource(R.drawable.normal_bg);
        }
    }

    @Override
    public int getItemCount() {
        return dictionaryList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
