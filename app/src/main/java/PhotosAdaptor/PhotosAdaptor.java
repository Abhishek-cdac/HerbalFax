package PhotosAdaptor;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herbal.herbalfax.customer.selectInterest.Interest;

import java.util.ArrayList;

public class PhotosAdaptor extends RecyclerView.Adapter {
    public PhotosAdaptor(ArrayList<Interest> dealsCategory, Context activity) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
