package com.example.myapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressLint("ViewConstructor")
public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<HomePageModel> homePageModelList;

    public HomePageAdapter(List<HomePageModel> homePageModelList) {
        this.homePageModelList = homePageModelList;
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

    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch (homePageModelList.get( position ).getType()) {
            case HomePageModel.BANNER_SLIDER:
                List<SliderModel> sliderModelList = homePageModelList.get( position ).getSliderModelList();
                ((BannerSliderViewholder) viewHolder).setBannerSliderViewPager2( sliderModelList );
                break;
            case HomePageModel.STRIP_AD_BANNER:
                int resource = homePageModelList.get( position ).getResource();
                String color = homePageModelList.get( position ).getBackgroundColor();
                ((StripAdBannerViewHolder) viewHolder).setStripAd( resource, color );
                break;
            case HomePageModel.HORIZONTAL_PRODUCT_VIEW:
                String horizontalTitle = homePageModelList.get( position ).getTitle();
                List<HorizontalProductScrollModel> horizontalProductScrollModelList = homePageModelList.get( position ).getHorizontalProductScrollModelList();
                ((HorizontalProductViewHolder) viewHolder).setHorizontalProductLayout( horizontalProductScrollModelList, horizontalTitle );
                break;
            case HomePageModel.GRID_PRODUCT_VIEW:
                String gridLayoutTitle = homePageModelList.get( position ).getTitle();
                List<HorizontalProductScrollModel> gridProductScrollModelList = homePageModelList.get( position ).getHorizontalProductScrollModelList();
                ((GridProductViewHolder) viewHolder).setGridProductLayout( gridProductScrollModelList, gridLayoutTitle );
                break;
            default:
                return;
        }
    }

    public int getItemCount() {
        return homePageModelList.size();
    }


    public class BannerSliderViewholder extends RecyclerView.ViewHolder {

        private ViewPager2 bannerSliderViewPager2;
        private int currentPage = 2;
        private Timer timer;
        private final long DELAY_TIME = 3333;
        private final long PERIOD_TIME = 3333;

        public BannerSliderViewholder(@NonNull View itemView) {
            super( itemView );
            bannerSliderViewPager2 = itemView.findViewById( R.id.banner_slider_view_pager );

        }

        private void setBannerSliderViewPager2(List<SliderModel> sliderModelList) {
            SliderAdapter sliderAdapter = new SliderAdapter( sliderModelList );
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
                        pageLooper( sliderModelList );
                    }
                }
            };
            startBannerSlideShow( sliderModelList );

            bannerSliderViewPager2.setOnTouchListener( new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    pageLooper( sliderModelList );
                    stopBannerSlideShow();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        startBannerSlideShow( sliderModelList );
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

        private void setStripAd(int resource, String color) {
            stripAdImage.setImageResource( resource );
            stripAdContainer.setBackgroundColor( Color.parseColor( color ) );
        }
    }

    public class HorizontalProductViewHolder extends RecyclerView.ViewHolder {

        private TextView horizontalLayoutTitle;
        private Button horizontalLayoutViewAllBtn;
        private RecyclerView horizontalRecycleView;

        public HorizontalProductViewHolder(@NonNull View itemView) {
            super( itemView );
            horizontalLayoutTitle = itemView.findViewById( R.id.horizontal_scroll_layout_title );
            horizontalLayoutViewAllBtn = itemView.findViewById( R.id.horizontal_scroll_viewAll_btn );
            horizontalRecycleView = itemView.findViewById( R.id.horizontal_product_recyclerview );
        }

        private void setHorizontalProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title) {
            horizontalLayoutTitle.setText( title );
            if (horizontalProductScrollModelList.size() > 8) {
                horizontalLayoutViewAllBtn.setVisibility( View.VISIBLE );
                horizontalLayoutViewAllBtn.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                        viewAllIntent.putExtra( "layout_code",0 );
                        itemView.getContext().startActivity( viewAllIntent );
                    }
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

        private TextView gridLayoutTitle;
        private Button gridLayoutViewAllBtn;
        private GridView gridView;

        public GridProductViewHolder(@NonNull View itemView) {
            super( itemView );
            gridLayoutTitle = itemView.findViewById( R.id.grid_product_layout_title );
            gridLayoutViewAllBtn = itemView.findViewById( R.id.grid_product_layout_viewall_btn );
            gridView = itemView.findViewById( R.id.grid_product_layout_gridview );
        }

        private void setGridProductLayout(List<HorizontalProductScrollModel> horizontalProductScrollModelList, String title) {
            gridLayoutTitle.setText( title );
            gridView.setAdapter( new GridProductLayoutAdapter( horizontalProductScrollModelList ) );
            gridLayoutViewAllBtn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent viewAllIntent = new Intent(itemView.getContext(),ViewAllActivity.class);
                    viewAllIntent.putExtra( "layout_code",1 );
                    itemView.getContext().startActivity( viewAllIntent );
                }
            } );

        }
    }

}
