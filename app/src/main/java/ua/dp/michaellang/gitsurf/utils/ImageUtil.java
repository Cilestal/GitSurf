package ua.dp.michaellang.gitsurf.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import ua.dp.michaellang.gitsurf.GitHubConstants;
import ua.dp.michaellang.gitsurf.R;

/**
 * Date: 05.03.17
 *
 * @author Michael Lang
 */
public final class ImageUtil {

    private ImageUtil() {

    }

    public static void loadImageFile(Context context, ImageView imageView, String url) {
        String imgUrl = GitHubConstants.IMAGE_URL + url;
        Glide.with(context)
                .load(imgUrl)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .fitCenter()
                .into(imageView);
    }

    public static void loadUserCircleAvatar(Context context, ImageView imageView, String avatarUrl) {
        if (avatarUrl == null) {
            loadCircleBlankAvatar(context, imageView);
            return;
        }

        Glide.with(context)
                .load(avatarUrl)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .crossFade()
                .into(imageView);
    }

    public static void loadUserCircleAvatar(Context context, ImageView imageView, int id) {
        String avatarUrl = GitHubConstants.AVATAR_URL + id;
        loadUserCircleAvatar(context, imageView, avatarUrl);
    }

    public static void loadCircleBlankAvatar(Context context, ImageView imageView) {
        Glide.with(context)
                .load(R.drawable.ic_user_avatar)
                .bitmapTransform(new CropCircleTransformation(context))
                .into(imageView);
    }

    public static void loadUserAvatar(final Context context, final ImageView imageView,
            String avatarUrl, final CollapsingToolbarLayout ctl) {
        final PalletteListener listener = new PalletteListener(ctl);

        Glide.with(context)
                .load(avatarUrl)
                .asBitmap()
                .fitCenter()
                .placeholder(R.drawable.ic_user_avatar)
                .error(R.drawable.ic_user_avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        new Palette.Builder(resource).generate(listener);
                        super.onResourceReady(resource, glideAnimation);
                    }
                });
    }

    private static final class PalletteListener implements Palette.PaletteAsyncListener {
        private final CollapsingToolbarLayout mLayout;

        PalletteListener(CollapsingToolbarLayout layout) {
            mLayout = layout;
        }

        @Override
        public void onGenerated(Palette palette) {
            Palette.Swatch[] swatches = {
                    palette.getVibrantSwatch(),
                    palette.getDarkVibrantSwatch(),
                    palette.getLightVibrantSwatch()
            };

            Palette.Swatch swatch = null;
            int maxPopulation = 0;
            for (Palette.Swatch sw : swatches) {
                if (sw != null) {
                    int population = sw.getPopulation();
                    if (population > maxPopulation) {
                        maxPopulation = population;
                        swatch = sw;
                    }
                }
            }

            if (swatch != null) {
                mLayout.setContentScrimColor(swatch.getRgb());
                mLayout.setStatusBarScrimColor(swatch.getRgb());
                mLayout.setCollapsedTitleTextColor(swatch.getTitleTextColor());
            }
        }
    }
}
