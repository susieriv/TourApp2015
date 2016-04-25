package com.kioube.tourapp.android.client.ui;

import java.util.Collection;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;

import com.kioube.tourapp.android.client.R;
import com.kioube.tourapp.android.client.domain.TourItem;
import com.kioube.tourapp.android.client.domain.TourItemImage;
import com.kioube.tourapp.android.client.persistence.repository.TourItemImageRepository;
import com.kioube.tourapp.android.client.ui.adapter.ThumbnailItemAdapter;
import com.kioube.tourapp.android.client.ui.filter.TourItemFilter;
import com.kioube.tourapp.android.client.ui.filter.TourItemImageFilter;

/**
 * 
 * GalleryFragment type definition
 * 
 * @author Julien Mellerin
 * 
 */
public class GalleryFragment extends FragmentBase {
	
	/* --- Fields --- */
	
	private View view;
	private List<TourItemImage> tourItemImageList;
	private TourItemImageRepository tourItemImageRepository = new TourItemImageRepository(this.getActivity());
	
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
	
	/**
	 * Gets the Fragment TourItem to render
	 */
	public TourItem getTourItem() {
		return this.getFilter().getTourItem();
	}
	
	/**
	 * Gets the fragment filter used to filter theme results
	 */
	public TourItemFilter getFilter() {
		return (TourItemFilter) super.getFilter();
	}
	
	/**
	 * Sets the fragment filter used to filter results
	 * 
	 * @param filter The filter used to filter results in the view
	 */
	public void setFilter(TourItemFilter filter) {
		super.setFilter(filter);
	}

    public Collection<TourItemImage> getTourItemImage() {
        return this.getTourItem().getImageList();
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
		this.view = inflater.inflate(R.layout.fragment_gallery, container, false);
		
		// Fills the thumbnail GridView
		this.tourItemImageList = this.tourItemImageRepository.getByTourItem(this.getTourItem());

        /*HorizontalScrollView scrollView = (HorizontalScrollView) this.getView().findViewById((R.id.horizontalScrollView));
        scrollView.setAdapter(new ThumbnailItemAdapter(this.getActivity(), this.tourItemImageList));*/

        // Sets the image
        final ImageView imageView = (ImageView) this.getView().findViewById(R.id.imageView);

		GridView thumbnailGridView = (GridView) this.getView().findViewById(R.id.gridView);
		thumbnailGridView.setAdapter(new ThumbnailItemAdapter(
			this.getActivity(),
			this.tourItemImageList
		));
		
		// Sets the item clicked event
		thumbnailGridView.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
	        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				/*GalleryFragment.this.getMainActivity().browseToImage(
					tourItemImageList.get(position)
				);*/

                TourItemImage image = tourItemImageList.get(position);

                if (image.getByteCode() != null && image.getByteCode().length() > 0) {

                    String imageBase64 = image.getByteCode();

                    byte[] imageData = Base64.decode(imageBase64, Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);

                    imageView.setImageBitmap(bitmap);
                }
	        }


	    });
		
		return this.view;
	}
}