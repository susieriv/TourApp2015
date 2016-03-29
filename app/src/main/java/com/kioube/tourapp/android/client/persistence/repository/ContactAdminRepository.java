package com.kioube.tourapp.android.client.persistence.repository;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.j256.ormlite.stmt.PreparedQuery;
import com.kioube.tourapp.android.client.domain.ContactAdmin;
import com.kioube.tourapp.android.client.domain.TourItem;
import com.kioube.tourapp.android.client.ui.ContactAdminFragment;

/**
 * 
 * CoordinateRepository type definition
 * 
 * @author Susie Riviere
 * 
 */
public class ContactAdminRepository extends RepositoryBase<ContactAdmin> {
	
	/* --- Constants --- */
	
	protected static final String LOG_TAG = ContactAdminRepository.class.getSimpleName();
	protected static final String EMAIL_KEY = "EMAIL";
	protected static final String PHONE_KEY = "PHONE";
	
	/* --- Static fields --- */
	
	/* --- Fields --- */
	Context context;
	
	/* --- Getters & setters --- */
	
	/* --- .ctors --- */
	
	/**
	 * Constructs a new ContactAdminRepository object.
	 *
	 * @param context
	 */
	public ContactAdminRepository(Context context) {
		super(context);
		
		// Sets the ContactAdmin persistent type DAO
		try {
			this.setDao(this.getDatabaseHelper().getContactAdminDao());
		}
		catch (SQLException e) {
			Log.e(LOG_TAG, "Failed to get the ContactAdmin DAO.", e);
		}
	}
	
	/* --- Class operations --- */
	
	/* --- Object operations --- */

	/**
	 * Gets the configured email admin
	 * 
	 * @return The configured email admin
	 */
	public String getEmailAdmin() {
		String result = null;
		ContactAdmin contactadmin = this.getByKey(EMAIL_KEY);
		
		if ((contactadmin != null) && (!contactadmin.getValue().isEmpty())) {
			result = contactadmin.getValue();
		}
		
		return result;
	}

	/**
	 * Gets the configured phone admin
	 * 
	 * @return The configured phone admin
	 */
	public String getPhoneAdmin() {
		String result = null;
		ContactAdmin contactadmin = this.getByKey(PHONE_KEY);
		
		if ((contactadmin != null) && (!contactadmin.getValue().isEmpty())) {
			result = contactadmin.getValue();
		}
		
		return result;
	}

	/**
	 * Gets a persistent ContactAdmin entity by its key
	 * 
	 * @param key The key to find
	 * @return A persistent ContactAdmin entity matching the key (or null)
	 */
	public ContactAdmin getByKey(String key) {
		ContactAdmin result = null;

		try {
			PreparedQuery<ContactAdmin> query = this.getDao().queryBuilder()
				.where()
				.eq(ContactAdmin.KEY_FIELD_NAME, key)
				.prepare();
			
			List<ContactAdmin> resultList = this.getDao().query(query);
			
			// As the key is unique, the result size may be 0 or 1 if found
			if (resultList.size() == 1) {
				result = resultList.get(0);
			}
		}
		catch (SQLException e) {
			Log.e(LOG_TAG, "Failed to query for a persistent ContactAdmin entity by its key.", e);
		}

		return result;
	}
	
}
