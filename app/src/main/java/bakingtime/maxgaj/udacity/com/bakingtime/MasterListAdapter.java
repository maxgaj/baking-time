package bakingtime.maxgaj.udacity.com.bakingtime;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import bakingtime.maxgaj.udacity.com.bakingtime.Model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.List;

public class MasterListAdapter extends BaseAdapter {
    private Context context;
    private List<Step> stepList;

    @BindView(R.id.item_master_list_description) TextView descriptionTextView;

    public MasterListAdapter(Context context, List<Step> stepList){
        this.context = context;
        this.stepList = stepList;
    }

    @Override
    public int getCount() {
        return stepList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Step step = stepList.get(position);
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_master_list, parent, false);
        }
        ButterKnife.bind(this, convertView);
        descriptionTextView.setText(step.getShortDescription());
        return convertView;
    }
}
