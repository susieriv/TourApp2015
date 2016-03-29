package com.kioube.tourapp.android.client.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.kioube.tourapp.android.client.R;
import com.kioube.tourapp.android.client.domain.ContactAdmin;
import com.kioube.tourapp.android.client.domain.Coordinate;
import com.kioube.tourapp.android.client.domain.Message;
import com.kioube.tourapp.android.client.service.MessageService;
import com.kioube.tourapp.android.client.service.listener.MessageServiceListener;
import com.kioube.tourapp.android.client.service.response.MessageResponse;
import com.kioube.tourapp.android.client.ui.adapter.ActionItemAdapter;
import com.kioube.tourapp.android.client.ui.adapter.MessageItemAdapter;
import com.kioube.tourapp.android.client.ui.domain.ActionItem;

import java.util.ArrayList;
import java.util.List;

import com.kioube.tourapp.android.client.persistence.repository.ContactAdminRepository;

/**
 * 
 * ContactAdminFragment type definition
 * 
 * @author Susie Riviere
 * 
 */
public class ContactAdminFragment extends FragmentBase {
	
	/* --- Constants --- */
	
	private static final String LOG_TAG = ContactAdminFragment.class.getSimpleName();
	
	/* --- Fields --- */

	private View view;
	private List<ActionItem> actionItemList;
	private ContactAdminRepository contactadminRepository= new ContactAdminRepository(this.getActivity());

	/* --- Getters and setters --- */

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#getView()
	 */
	@Override
	public View getView() {
		return super.getView() != null ? super.getView() : this.view;
	}	
	
	/*
	 * (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater,
		ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		
		// Inflate the layout for this fragment
		this.view = inflater.inflate(R.layout.fragment_contact_admin, container, false);
		
		// Sets fragment title
		this.getMainActivity().setViewTitle(
				this.getResources().getString(R.string.contact_admin)
		);
		
		// Loads messages
		this.loadActions();

		return view;
	}

	/**
	 * Loads and render actions
	 */
	private void loadActions() {
        contactadminRepository= new ContactAdminRepository(this.getActivity());
        this.actionItemList = new ArrayList<ActionItem>();

		// Email action
		this.actionItemList.add(new ActionItem(
				this.getResources().getString(R.string.email),
				R.drawable.ic_mail,
				new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(Intent.ACTION_SEND);
						intent.setType("plain/text");
						intent.putExtra(Intent.EXTRA_EMAIL, new String[]{contactadminRepository.getEmailAdmin()});
                        startActivity(Intent.createChooser(intent, ""));
						//this.startActivity(Intent.createChooser(intent, ""));
					}
				}
		));

		// Phone action
		this.actionItemList.add(new ActionItem(
				this.getResources().getString(R.string.call),
				R.drawable.ic_phone,
				new Runnable() {
					@Override
					public void run() {
						Intent intent = new Intent(Intent.ACTION_CALL);
						String numberPhone = "tel:" + contactadminRepository.getPhoneAdmin();
						intent.setData(Uri.parse(numberPhone));
						startActivity(intent);
						//this.startActivity(intent);
					}
				}
		));

		// Adapts the ActionItem objects list to the GridView
		GridView actionGridView = (GridView) this.getView().findViewById(R.id.action_grid_view);

		actionGridView.setAdapter(new ActionItemAdapter(
				this.getActivity(),
				this.actionItemList
		));

		// Sets the item clicked event
		actionGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				ContactAdminFragment.this.actionItemList.get(position).getRunnable().run();
			}
		});
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Fragment#onDestroyView()
	 */
	@Override
	public void onDestroyView() {
		super.onDestroyView();
	}
}