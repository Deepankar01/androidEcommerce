package live.Abhinav.ecommerce.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.volley.toolbox.ImageLoader;
import live.Abhinav.ecommerce.app.AppController;
import live.Abhinav.ecommerce.app.R;
import live.Abhinav.ecommerce.pojo.Transaction;

import java.util.ArrayList;

/**
 * Created by Abhinav on 6/19/2015.
 */
public class AdapterTransactions extends RecyclerView.Adapter<AdapterTransactions.ViewHolderTransactions> {

    private ArrayList<Transaction> listTransactions = new ArrayList<Transaction>();
    private LayoutInflater layoutInflater;
    private AppController volleySingleton;

    private float netBuy;
    private float netSold;

    public AdapterTransactions(Context context) {
        layoutInflater = LayoutInflater.from(context);
        volleySingleton = AppController.getInstance();

    }

    public void setListTransactions(ArrayList<Transaction> listTransactions) {
        this.listTransactions = listTransactions;
        notifyItemRangeChanged(0, listTransactions.size());
    }

    @Override
    public ViewHolderTransactions onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_transaction, parent, false);
        ViewHolderTransactions viewHolder = new ViewHolderTransactions(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolderTransactions holder, int position) {
        Transaction currentTransaction = listTransactions.get(position);
        holder.transactionDate.setText(currentTransaction.getDate());
        holder.transactionOtherPartyName.setText(currentTransaction.getOtherPartyName());
        holder.transactionCost.setText(currentTransaction.getCost());
        if (currentTransaction.getIsBuy()) {
            netBuy += Float.parseFloat(currentTransaction.getCost());
            holder.transactionIndicator.setBackgroundColor(Color.parseColor("#FFD9642C"));
        } else if (currentTransaction.getIsSell()) {
            netSold += Float.parseFloat(currentTransaction.getCost());
            holder.transactionIndicator.setBackgroundColor(Color.parseColor("#ff239642"));
        }
    }

    @Override
    public int getItemCount() {
        return listTransactions.size();
    }


    static class ViewHolderTransactions extends RecyclerView.ViewHolder {

        private TextView transactionDate;
        private TextView transactionOtherPartyName;
        private TextView transactionCost;
        private TextView transactionIndicator;


        public ViewHolderTransactions(View itemView) {
            super(itemView);
            transactionDate = (TextView) itemView.findViewById(R.id.tv_dateOfTransaction);
            transactionOtherPartyName = (TextView) itemView.findViewById(R.id.tv_nameOtherParty);
            transactionCost = (TextView) itemView.findViewById(R.id.tv_cost);
            transactionIndicator = (TextView) itemView.findViewById(R.id.tv_buySellIndicator);

        }
    }
}
