package com.jiffy.jiffyapp.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.model.ContactsBean;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Created by Bhoopesh.
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ItemViewHolder> {

    private Context context;
    private ArrayList<ContactsBean> mContactList;

    public ContactsAdapter(Context context, ArrayList<ContactsBean> list) {
        this.mContactList = list;
        this.context = context;
    }


    @Override
    public ContactsAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.my_contacts_adapter, parent, false);

        return new ContactsAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ContactsAdapter.ItemViewHolder holder, final int i) {
        holder.tvName.setText(mContactList.get(i).getName());
        holder.tvEmail.setText(mContactList.get(i).getEmail());
        if (  mContactList.get(i).isSelected()){
            holder.cbSelect.setChecked(true);
        }else {
            holder.cbSelect.setChecked(false);
        }
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
              if (b){
                  mContactList.get(i).setSelected(true);
              }else {
                  mContactList.get(i).setSelected(false);
              }
                try {
                    notifyItemChanged(i);
                }catch (Exception err){
                    err.printStackTrace();
                }
            }
        });

        holder.setIsRecyclable(false);
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvEmail;
        private CheckBox cbSelect;


        public ItemViewHolder(View itemView) {
            super(itemView);
            cbSelect = (CheckBox) itemView.findViewById(R.id.cbSelect);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
            tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
        }
    }

}
