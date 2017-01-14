package com.jiffy.jiffyapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jiffy.jiffyapp.R;
import com.jiffy.jiffyapp.adapter.ContactsAdapter;
import com.jiffy.jiffyapp.constant.ApplicationConstant;
import com.jiffy.jiffyapp.manager.MySingleton;
import com.jiffy.jiffyapp.model.ContactsBean;

import java.util.ArrayList;

/**
 * Created by Bhoopesh.
 */
public class ContactsFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "ContactsFragment";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 10;
    private Context mContext;
    private View view;
    private ContactsAdapter mAdapter;
    private RecyclerView rvContacts;
    private ImageView ivReload;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<ContactsBean> mContactsList = new ArrayList<>();
    private ProgressDialog progress;
    private TextView tvSendMail;
    private TextView tvNoContacts;

    public ContactsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_contacts, container, false);
        return view;
    }

    @Override
    public void onStart() {
        findViews();
        super.onStart();
    }

    /**
     * Method to find views and also check permission for android M and load contacts by using async task.
     */
    private void findViews() {
        progress = new ProgressDialog(mContext);
        progress.setMessage(getString(R.string.loading_contacts));
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        ivReload = (ImageView) view.findViewById(R.id.ivReload);
        tvSendMail = (TextView) view.findViewById(R.id.tvSendMail);
        tvNoContacts = (TextView) view.findViewById(R.id.tvNoContacts);
        rvContacts = (RecyclerView) view.findViewById(R.id.rvContacts);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rvContacts.setLayoutManager(layoutManager);

        // check permission for android M and above.
        if ((int) Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) mContext,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            ApplicationConstant.showSnckbar(tvNoContacts, getString(R.string.allow_permission));
        } else {
            if (MySingleton.getInstance().getmContactsList() != null && MySingleton.getInstance().getmContactsList().size() == 0) {
                loadContacts();
            } else {
                mContactsList = MySingleton.getInstance().getmContactsList();
                setAdapter();
            }
        }
        tvSendMail.setOnClickListener(this);
        ivReload.setOnClickListener(this);

    }

    /****
     * Method to call async task to load contacts
     ****/
    private void loadContacts() {
        LoadContacts loadContacts = new LoadContacts();
        loadContacts.execute();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    loadContacts();
                } else {
                    ApplicationConstant.showSnckbar(tvNoContacts, getString(R.string.enable_contact_permission));
                }
                break;
        }
    }

    /******
     * Method to share url via email
     *****
     * @param emailList*/
    private void sendEmail(ArrayList<String> emailList) {
        String[] selectedEmailIds = new String[emailList.size()];
        selectedEmailIds = emailList.toArray(selectedEmailIds);
        String urlToShare=""; // contain url of youtube or M3U8 according to the user preference
        if (MySingleton.getInstance().isYoutubeSelected()){
            urlToShare=ApplicationConstant.YOUTUBE_URL_TO_SHARE;
        }else{
            urlToShare=ApplicationConstant.M3U8_URL;
        }

        final Intent emailLauncher = new Intent(Intent.ACTION_SEND);
        emailLauncher.setType("message/rfc822");
        emailLauncher.putExtra(Intent.EXTRA_EMAIL, selectedEmailIds);
        emailLauncher.putExtra(Intent.EXTRA_SUBJECT, ApplicationConstant.EMAIL_SUBJECT);
        emailLauncher.putExtra(Intent.EXTRA_TEXT, urlToShare);
        try {
            startActivity(emailLauncher);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /***
     * Method to set adapter
     *****/
    private void setAdapter() {
        if (mContactsList!=null&& mContactsList.size()!=0) {
            tvSendMail.setVisibility(View.VISIBLE);
            tvNoContacts.setVisibility(View.GONE);
            for (int i = 0; i < mContactsList.size(); i++) {
                if (mContactsList.get(i).isSelected()) {
                    mContactsList.get(i).setSelected(false);
                }
            }
            mAdapter = new ContactsAdapter(mContext, mContactsList);
            rvContacts.setAdapter(mAdapter);
        }else{
            tvSendMail.setVisibility(View.GONE);
            tvNoContacts.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view==tvSendMail){
            // getting list of selected contacts
            ArrayList<String> emailList=new ArrayList<>();
            for (int i = 0; i < mContactsList.size(); i++) {
                if (mContactsList.get(i).isSelected()) {
                    emailList.add(mContactsList.get(i).getEmail());
                }
            }
            if (emailList.size()>0){
                sendEmail(emailList);
            }else{
                ApplicationConstant.showSnckbar(view,getString(R.string.select_contact_to_share_mail));
            }
        }else{
            loadContacts();
        }
    }


    /****
     * Async task to load contacts from phone storage
     ****/
    public class LoadContacts extends AsyncTask {
        @Override
        protected void onPreExecute() {
            progress.show();
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            ArrayList<ContactsBean> names = new ArrayList<ContactsBean>();
            ContentResolver cr = mContext.getContentResolver();
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    Cursor cur1 = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (cur1.moveToNext()) {
                        ContactsBean contact = new ContactsBean();
                        //to get the contact names
                        String name = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        contact.setEmail(email);
                        contact.setName(name);
                        ApplicationConstant.log("Email - ", email + "  name- " + name);
                        if (email != null) {
                            names.add(contact);
                        }
                    }
                    cur1.close();
                }
            }
            return names;
        }

        @Override
        protected void onPostExecute(Object obj) {
            mContactsList = (ArrayList<ContactsBean>) obj;
            if (mContactsList != null) {
                MySingleton.getInstance().setmContactsList(mContactsList);
                setAdapter();
            }
            progress.dismiss();
            super.onPostExecute(obj);
        }
    }

}
