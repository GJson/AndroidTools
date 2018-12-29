package com.gjson.androidtools.commonview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gjson.androidtools.R;
import java.util.List;


/**
 * Created by gjson on 2018/11/18.
 * Name AutoScrollBanner
 * Version 1.0
 */
public class AutoScrollBanner extends RelativeLayout {

  private static final int RMP = LayoutParams.MATCH_PARENT;
  private static final int RWP = LayoutParams.WRAP_CONTENT;
  private static final int LWC = LinearLayout.LayoutParams.WRAP_CONTENT;
  private static final int WHAT_AUTO_PLAY = 1000;
  //Point位置
  public static final int CENTER = 0;
  public static final int LEFT = 1;
  public static final int RIGHT = 2;

  private LinearLayout mPointRealContainerLl;

  private ViewPager mViewPager;
  //本地图片资源
  private List<Integer> mImages;
  //网络图片资源
  private List<String> mImageUrls;
  //是否是网络图片
  private boolean mIsImageUrl = false;
  //是否只有一张图片
  private boolean mIsOneImg = false;
  //是否可以自动播放
  private boolean mAutoPlayAble = true;
  //是否正在播放
  private boolean mIsAutoPlaying = false;
  //自动播放时间
  private int mAutoPalyTime = 5000;
  //当前页面位置
  private int mCurrentPositon;
  //指示点位置
  private int mPointPosition = CENTER;
  //指示点资源
  private int mPointDrawableResId = R.drawable.selector_in;
  //指示容器背景
  private Drawable mPointContainerBackgroundDrawable;
  //指示容器布局规则
  private LayoutParams mPointRealContainerLp;
  //提示语
  private TextView mTips;

  private List<String> mTipsDatas;
  //指示点是否可见
  private boolean mPointsIsVisible = true;


  private Handler mAutoPlayHandler = new Handler() {
    @Override
    public void handleMessage(Message msg) {
      mCurrentPositon++;
      mViewPager.setCurrentItem(mCurrentPositon);
      mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
    }
  };

  public AutoScrollBanner(Context context) {
    this(context, null);
  }

  public AutoScrollBanner(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AutoScrollBanner(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    init(context, attrs);
  }

  private void init(Context context, AttributeSet attrs) {

    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AutoScrollBanner);

    mPointsIsVisible = a.getBoolean(R.styleable.AutoScrollBanner_points_visibility, true);
    mPointPosition = a.getInt(R.styleable.AutoScrollBanner_points_position, CENTER);
    mPointContainerBackgroundDrawable
        = a.getDrawable(R.styleable.AutoScrollBanner_points_container_background);

    a.recycle();

    setLayout(context);
  }

  private void setLayout(Context context) {
    //关闭view的OverScroll
    setOverScrollMode(OVER_SCROLL_NEVER);
    //设置指示器背景
    if (mPointContainerBackgroundDrawable == null) {
      mPointContainerBackgroundDrawable = new ColorDrawable(Color.parseColor("#00aaaaaa"));
    }
    //添加ViewPager
    mViewPager = new ViewPager(context);
    addView(mViewPager, new LayoutParams(RMP, RMP));
    //设置指示器背景容器
    RelativeLayout pointContainerRl = new RelativeLayout(context);
    if (Build.VERSION.SDK_INT >= 16) {
      pointContainerRl.setBackground(mPointContainerBackgroundDrawable);
    } else {
      pointContainerRl.setBackgroundDrawable(mPointContainerBackgroundDrawable);
    }
    //设置内边距
    pointContainerRl.setPadding(0, 10, 0, 10);
    //设定指示器容器布局及位置
    LayoutParams pointContainerLp = new LayoutParams(RMP, RWP);
    pointContainerLp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
    addView(pointContainerRl, pointContainerLp);
    //设置指示器容器
    mPointRealContainerLl = new LinearLayout(context);
    mPointRealContainerLl.setOrientation(LinearLayout.HORIZONTAL);
    mPointRealContainerLp = new LayoutParams(RWP, RWP);
    pointContainerRl.addView(mPointRealContainerLl, mPointRealContainerLp);
    //设置指示器容器是否可见
    if (mPointRealContainerLl != null) {
      if (mPointsIsVisible) {
        mPointRealContainerLl.setVisibility(View.VISIBLE);
      } else {
        mPointRealContainerLl.setVisibility(View.GONE);
      }
    }
    //设置指示器布局位置
    if (mPointPosition == CENTER) {
      mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
    } else if (mPointPosition == LEFT) {
      mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    } else if (mPointPosition == RIGHT) {
      mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    }
  }

  /**
   * 设置本地图片
   * @param images
   */
  public void setImages(List<Integer> images) {
    //加载本地图片
    mIsImageUrl = false;
    this.mImages = images;
    if (images.size() <= 1)
      mIsOneImg = true;
    //初始化ViewPager
    initViewPager();
  }

  /**
   * 设置网络图片
   * @param urls
   */
  public void setImagesUrl(List<String> urls) {
    //加载网络图片
    mIsImageUrl = true;
    this.mImageUrls = urls;
    if (urls.size() <= 1)
      mIsOneImg = true;
    //初始化ViewPager
    initViewPager();
  }

  /**
   * 设置指示点是否可见
   * @param isVisible
   */
  public void setPointsIsVisible(boolean isVisible) {
    if (mPointRealContainerLl != null) {
      if (isVisible) {
        mPointRealContainerLl.setVisibility(View.VISIBLE);
      } else {
        mPointRealContainerLl.setVisibility(View.GONE);
      }
    }
  }

  /**
   * 对应三个位置 CENTER,RIGHT,LEFT
   * @param position
   */
  public void setPoinstPosition(int position) {
    //设置指示器布局位置
    if (position == CENTER) {
      mPointRealContainerLp.addRule(RelativeLayout.CENTER_HORIZONTAL);
    } else if (position == LEFT) {
      mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
    } else if (position == RIGHT) {
      mPointRealContainerLp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    }
  }

  private void initViewPager() {
    //当图片多于1张时添加指示点
    if(!mIsOneImg) {
      addPoints();
    }
    //设置ViewPager
    FlyPageAdapter adapter = new FlyPageAdapter();
    mViewPager.setAdapter(adapter);
    mViewPager.addOnPageChangeListener(mOnPageChangeListener);
    //跳转到首页
    mViewPager.setCurrentItem(1, false);
    //当图片多于1张时开始轮播
    if (!mIsOneImg) {
      startAutoPlay();
    }
  }


  /**
   * 返回真实的位置
   * @param position
   * @return
   */
  private int toRealPosition(int position) {
    int realPosition;
    if (mIsImageUrl) {
      realPosition = (position - 1) % mImageUrls.size();
      if (realPosition < 0)
        realPosition += mImageUrls.size();
    } else {
      realPosition = (position - 1) % mImages.size();
      if (realPosition < 0)
        realPosition += mImages.size();
    }

    return realPosition;
  }

  private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset,
        int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
      if (mIsImageUrl) {
        mCurrentPositon = position % (mImageUrls.size() + 2);
      } else {
        mCurrentPositon = position % (mImages.size() + 2);
      }
      switchToPoint(toRealPosition(mCurrentPositon));
    }

    @Override
    public void onPageScrollStateChanged(int state) {
      if (state == ViewPager.SCROLL_STATE_IDLE) {
        int current = mViewPager.getCurrentItem();
        int lastReal = mViewPager.getAdapter().getCount()-2;
        if (current == 0) {
          mViewPager.setCurrentItem(lastReal, false);
        } else if (current == lastReal+1) {
          mViewPager.setCurrentItem(1, false);
        }
      }
    }
  };

  private class FlyPageAdapter extends PagerAdapter {

    @Override
    public int getCount() {
      //当只有一张图片时返回1
      if (mIsOneImg) {
        return 1;
      }
      //当为网络图片，返回网页图片长度
      if (mIsImageUrl)
        return mImageUrls.size() + 2;
      //当为本地图片，返回本地图片长度
      return mImages.size()+2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
      return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
      ImageView imageView = new ImageView(getContext());
      imageView.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View view) {
          if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(toRealPosition(position));
          }
        }
      });
      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
      if (mIsImageUrl) {
        Glide.with(getContext())
            .load(mImageUrls.get(toRealPosition(position)))
            .into(imageView);
      } else {
        imageView.setImageResource(mImages.get(toRealPosition(position)));
      }
      container.addView(imageView);

      return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
      container.removeView((View)object);
      if (object != null)
        object = null;
    }
  }


  /**
   * 添加指示点
   */
  private void addPoints() {
    mPointRealContainerLl.removeAllViews();
    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LWC, LWC);
    lp.setMargins(10, 10, 10, 10);
    ImageView imageView;
    int length = mIsImageUrl ? mImageUrls.size() : mImages.size();
    for (int i = 0; i < length; i++) {
      imageView = new ImageView(getContext());
      imageView.setLayoutParams(lp);
      imageView.setImageResource(mPointDrawableResId);
      mPointRealContainerLl.addView(imageView);
    }

    switchToPoint(0);
  }

  /**
   * 切换指示器
   * @param currentPoint
   */
  private void switchToPoint(final int currentPoint) {
    for (int i = 0; i < mPointRealContainerLl.getChildCount(); i++) {
      mPointRealContainerLl.getChildAt(i).setEnabled(false);
    }
    mPointRealContainerLl.getChildAt(currentPoint).setEnabled(true);

  }

  @Override
  public boolean dispatchTouchEvent(MotionEvent ev) {
    if (mAutoPlayAble && !mIsOneImg) {
      switch (ev.getAction()) {
        case MotionEvent.ACTION_DOWN:
          stopAutoPlay();
          break;
        case MotionEvent.ACTION_UP:
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_OUTSIDE:
          startAutoPlay();
          break;
      }
    }
    return super.dispatchTouchEvent(ev);
  }

  /**
   * 开始播放
   */
  public void startAutoPlay() {
    if (mAutoPlayAble && !mIsAutoPlaying) {
      mIsAutoPlaying = true;
      mAutoPlayHandler.sendEmptyMessageDelayed(WHAT_AUTO_PLAY, mAutoPalyTime);
    }
  }

  /**
   * 停止播放
   */
  public void stopAutoPlay() {
    if (mAutoPlayAble && mIsAutoPlaying) {
      mIsAutoPlaying = false;
      mAutoPlayHandler.removeMessages(WHAT_AUTO_PLAY);
    }
  }

  private OnItemClickListener mOnItemClickListener;

  public void setOnItemClickListener(OnItemClickListener listener) {
    this.mOnItemClickListener = listener;
  }

  public interface OnItemClickListener {
    void onItemClick(int position);
  }
}