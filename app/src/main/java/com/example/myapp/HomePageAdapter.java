package com.example.myapp;

import static com.example.myapp.utils.Constants.FB_PRODUCT_ID;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ViewConstructor")
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<HomePageModel> homePageModelList;
    private RecyclerView.RecycledViewPool recycledViewPool;
//    private int lastPosition = -1;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        recycledViewPool = new RecyclerView.RecycledViewPool();
    }

    public void setData(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        switch (homePageModelList.get( position ).getType()) {
            case 0:
                return HomePageModel.BANNER_SLIDER;
            case 1:
                return HomePageModel.STRIP_AD_BANNER;
            case 2:
                return HomePageModel.HORIZONTAL_PRODUCT_VIEW;
            case 3:
                return HomePageModel.GRID_PRODUCT_VIEW;
            default:
                return -1;
        }
    }

    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        switch (viewType) {
            case HomePageModel.BANNER_SLIDER:
                View bannerSliderView = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.sliding_ad_layout, viewGroup, false );
                return new BannerSliderViewholder( bannerSliderView );
            case HomePageModel.STRIP_AD_BANNER:
                View stripAdBanner = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.strip_ad_layout, viewGroup, false );
                return new StripAdBannerViewHolder( stripAdBanner );
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                View horizontalProductView = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.horizontal_scroll_layout, viewGroup, false );
                return new HorizontalProductViewHolder( horizontalProductView );
            case HomePageModel.GRID_PRODUCT_VIEW:
                View gridProductView = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.grid_product_layout, viewGroup, false );
                return new GridProductViewHolder( gridProductView );
            default:
                return null;
        }
    }

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        switch (homePageModelList.get( position ).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get( position ).getSliderModelList();
                ((BannerSliderViewholder) viewHolder).setBannerSliderViewPager2( sliderModelList );
                break;
            case HomePageModel.STRIP_AD_BANNER:
                String resource = homePageModelList.get( position ).getResource();
                String color = homePageModelList.get( position ).getBackgroundColor();
                ((StripAdBannerViewHolder) viewHolder).setStripAd( resource, color );
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String layoutColor = homePageModelList.get( position ).getBackgroundColor();
                String horizontalTitle = homePageModelList.get( position ).getTitle();
                List<WishlistModel> viewAllProductList = homePageModelList.get( position ).getViewAllProductList();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get( position ).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout( horizontalProductScrollModelList, horizontalTitle, layoutColor, viewAllProductList );
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutcolor = homePageModelList.get( position ).getBackgroundColor();
                String gridLayoutTitle = homePageModelList.get( position ).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get( position ).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) viewHolder).setGridProductLayout( gridProductScrollModelList, gridLayoutTitle, gridLayoutcolor );
                break;
            default:
                return;
        }
//       if (lastPosition < position) {
//           Animation animation = AnimationUtils.loadAnimation( viewHolder.itemView.getContext(), R.anim.fade_in );
//          viewHolder.itemView.setAnimation( animation );
//            lastPosition = position;
//       }
    }

    public int getItemCount() {
        return homePageModelList.size();
    }


    public class BannerSliderViewholder extends RecyclerView.ViewHolder {

        private ViewPager2 bannerSliderViewPager2;
        private int currentPage;
        private Timer timer;
        private final long DELAY_TIME = 3333;
        private final long PERIOD_TIME = 3333;
        private List<SliderModel> arrangedList;

        public BannerSliderViewholder(@NonNull View itemView) {
            super( itemView );
            bannerSliderViewPager2 = itemView.findViewById( R.id.banner_slider_view_pager );

        }

        private void setBannerSliderViewPager2(List<SliderModel> sliderModelList) {
            currentPage = 2;
            if (timer != null) {
                timer.cancel();
            }
            arrangedList = new ArrayList<>();
            for (int i = 0; i < sliderModelList.size(); i++) {
                arrangedList.add( i, sliderModelList.get( i ) );
            }
            arrangedList.add( 0, sliderModelList.get( sliderModelList.size() - 2 ) );
            arrangedList.add( 0, sliderModelList.get( sliderModelList.size() - 1 ) );
            arrangedList.add( sliderModelList.get( 0 ) );
            arrangedList.add( sliderModelList.get( 1 ) );

            SliderAdapter sliderAdapter = new SliderAdapter( arrangedList );
            bannerSliderViewPager2.setAdapter( sliderAdapter );
            bannerSliderViewPager2.setClipToPadding( false );
            bannerSliderViewPager2.setCurrentItem( currentPage );

            ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int i) {
                    currentPage = i;
                }

                @Override
                public void onPageScrollStateChanged(int i) {
                    if (i == ViewPager.SCROLL_STATE_IDLE) {
                        pageLooper( arrangedList );
                    }
                }
            };
            startBannerSlideShow( arrangedList );

            bannerSliderViewPager2.setOnTouchListener( new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper( arrangedList );
                    stopBannerSlideShow();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow( arrangedList );
                    }
                    return false;
                }
            } );
        }

        private void pageLooper(List<SliderModel> sliderModelList) {
            if (currentPage == sliderModelList.size() - 2) {
                currentPage = 2;
                bannerSliderViewPager2.setCurrentItem( currentPage, false );
            }
            if (currentPage == 1) {
                currentPage = sliderModelList.size() - 3;
                bannerSliderViewPager2.setCurrentItem( currentPage, false );
            }

        }

        private void startBannerSlideShow(final List<SliderModel> sliderModelList) {
            final Handler handler = new Handler();
            final Runnable update = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= sliderModelList.size()) {
                        currentPage = 1;
                    }
                    bannerSliderViewPager2.setCurrentItem( currentPage++, true );
                }
            };
            timer = new Timer();
            timer.schedule( new TimerTask() {
                @Override
                public void run() {
                    handler.post( update );
                }
            }, DELAY_TIME, PERIOD_TIME );
        }

        private void stopBannerSlideShow() {
            timer.cancel();
        }
    }

    public class StripAdBannerViewHolder extends RecyclerView.ViewHolder {

        private ImageView stripAdImage;
        private ConstraintLayout stripAdContainer;

        public StripAdBannerViewHolder(@NonNull View itemView) {
            super( itemView );
            stripAdImage = itemView.findViewById( R.id.strip_ad_image );
            stripAdContainer = itemView.findViewById( R.id.strip_ad_container );
        }

        private void setStripAd(String resource, String color) {
            Glide.with( itemView.getContext() ).load( resource ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_image_24 ) ).into( stripAdImage );
            stripAdContainer.setBackgroundColor( Color.parseColor( color ) );
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout container;
        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllBtn;
        private RecyclerView horizontalRecycleView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super( itemView );
            container = itemView.findViewById( R.id.gird_container );
            horizontalLayoutTitle = itemView.findViewById( R.id.horizontal_scroll_layout_title );
            horizontalLayoutViewAllBtn = itemView.findViewById( R.id.horizontal_scroll_viewAll_btn );
            horizontalRecycleView = itemView.findViewById( R.id.horizontal_product_recyclerview );
            horizontalRecycleView.setRecycledViewPool( recycledViewPool );
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title, String color, List<WishlistModel> viewAllProductList) {
            container.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( color ) ) );
            horizontalLayoutTitle.setText( title );
            if (horizontalProductScrollModelList.size() > 8) {
                horizontalLayoutViewAllBtn.setVisibility( View.VISIBLE );
                horizontalLayoutViewAllBtn.setOnClickListener( view -> {
                    ViewAllActivity.wishlistModelList = viewAllProductList;
                    Intent viewAllIntent = new Intent( itemView.getContext(), ViewAllActivity.class );
                    viewAllIntent.putExtra( "layout_code", 0 );
                    viewAllIntent.putExtra( "title", title );
                    itemView.getContext().startActivity( viewAllIntent );
                } );
            } else {
                horizontalLayoutViewAllBtn.setVisibility( View.INVISIBLE );
            }
            HorizontalProductScrollAdapter horizontalProductScrollAdapter = new HorizontalProductScrollAdapter( horizontalProductScrollModelList );
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( itemView.getContext() );
            linearLayoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
            horizontalRecycleView.setLayoutManager( linearLayoutManager );
            horizontalRecycleView.setAdapter( horizontalProductScrollAdapter );
            horizontalProductScrollAdapter.notifyDataSetChanged();
        }
    }

    public class GridProductViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout container;
        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridLayout gridProductLayout;

        public GridProductViewHolder(@NonNull View itemView) {
            super( itemView );
            container = itemView.findViewById( R.id.gird_container );
            gridLayoutTitle = itemView.findViewById( R.id.grid_product_layout_title );
            gridLayoutViewAllBtn = itemView.findViewById( R.id.grid_product_layout_viewall_btn );
            gridProductLayout = itemView.findViewById( R.id.grid_layout );
        }

        private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title, String color) {
            container.setBackgroundTintList( ColorStateList.valueOf( Color.parseColor( color ) ) );
            gridLayoutTitle.setText( title );

            for (int i = 0; i < 4; i++) {
                ImageView productImage = gridProductLayout.getChildAt( i ).findViewById( R.id.h_s_product_image );
                TextView productTitle = gridProductLayout.getChildAt( i ).findViewById( R.id.h_s_product_title );
                TextView productColor = gridProductLayout.getChildAt( i ).findViewById( R.id.h_s_product_color );
                TextView productPrice = gridProductLayout.getChildAt( i ).findViewById( R.id.h_s_product_price );

                Glide.with( itemView.getContext() ).load( horizontalProductScrollModelList.get( i ).getProduceImage() ).apply( new RequestOptions().placeholder( R.drawable.ic_baseline_image_24 ) ).into( productImage );
                productTitle.setText( horizontalProductScrollModelList.get( i ).getProductTitle() );
                productColor.setText( horizontalProductScrollModelList.get( i ).getProductColor() );
                productPrice.setText( "Rs." + horizontalProductScrollModelList.get( i ).getProductPrice() + "/-" );
                gridProductLayout.getChildAt( i ).setBackgroundColor( Color.parseColor( "#ffffff" ) );

                if (!title.equals( "" )) {
                    int finalI = i;
                    gridProductLayout.getChildAt( i ).setOnClickListener( view -> {
                        Intent productDetailsIntent = new Intent( itemView.getContext(), PRoductDEtailshActivity.class );
                        productDetailsIntent.putExtra( FB_PRODUCT_ID, horizontalProductScrollModelList.get( finalI ).getProductID() );
                        itemView.getContext().startActivity( productDetailsIntent );
                    } );
                }
            }
            if (!title.equals( "" )) {
                gridLayoutViewAllBtn.setOnClickListener( view -> {
                    ViewAllActivity.productList = horizontalProductScrollModelList;
                    Intent viewAllIntent = new Intent( itemView.getContext(), ViewAllActivity.class );
                    viewAllIntent.putExtra( "layout_code", 1 );
                    viewAllIntent.putExtra( "title", title );
                    itemView.getContext().startActivity( viewAllIntent );
                } );
            }
        }
    }

}
