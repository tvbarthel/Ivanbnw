package fr.tvbarthel.ivanbnw.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import fr.tvbarthel.ivanbnw.R;
import fr.tvbarthel.ivanbnw.utils.BackgroundUtils;

/**
 * View used to encapsulate business logic linked to the {@link fr.tvbarthel.ivanbnw.home.HomeView}
 * background
 */
class HomeBackgroundView extends FrameLayout {

    private static final float START_RATIO = 0.90f;
    private static final float RIGHT_TRIANGLE_SCROLL_SPEED = 0.65f;
    private static final float LEFT_TRIANGLE_SCROLL_SPEED = 0.75f;
    private static final float BACKGROUND_SCROLL_SPEED = 1.10f;

    private View listBackground;
    private View leftTriangle;
    private View rightTriangle;
    private float listBackgroundInitialTranslationY;
    private View listBackgroundShadow;
    private boolean animatedIn;
    private Interpolator interpolator;

    /**
     * View used to encapsulate business logic linked to the {@link fr.tvbarthel.ivanbnw.home.HomeView}
     * background
     *
     * @param context holding context.
     */
    public HomeBackgroundView(Context context) {
        this(context, null);
    }

    /**
     * View used to encapsulate business logic linked to the {@link fr.tvbarthel.ivanbnw.home.HomeView}
     * background
     *
     * @param context holding context.
     * @param attrs   attr from xml.
     */
    public HomeBackgroundView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * View used to encapsulate business logic linked to the {@link fr.tvbarthel.ivanbnw.home.HomeView}
     * background
     *
     * @param context      holding context.
     * @param attrs        attr from xml.
     * @param defStyleAttr style.
     */
    public HomeBackgroundView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    @Override
    public void setScrollY(int value) {
        if (animatedIn) {
            rightTriangle.setTranslationY(-value * RIGHT_TRIANGLE_SCROLL_SPEED);
            leftTriangle.setTranslationY(-value * LEFT_TRIANGLE_SCROLL_SPEED);
            float translation = listBackgroundInitialTranslationY - value * BACKGROUND_SCROLL_SPEED;
            translation = Math.max(translation, 0);
            listBackground.setTranslationY(translation);
            listBackgroundShadow.setTranslationY(translation - listBackgroundShadow.getHeight());
        }
    }

    /**
     * Display the background component smoothly.
     * <p/>
     * Before calling this method, only the background color is visible.
     */
    public void animateIn() {
        leftTriangle.animate()
                .translationY(0)
                .setStartDelay(200)
                .setInterpolator(interpolator)
                .setDuration(300)
                .setListener(null);
        rightTriangle.animate()
                .translationY(0)
                .setStartDelay(200)
                .setInterpolator(interpolator)
                .setDuration(300)
                .setListener(null);
        listBackground.animate()
                .translationY(listBackgroundInitialTranslationY)
                .setStartDelay(200)
                .setInterpolator(interpolator)
                .setDuration(300)
                .setListener(null);
        listBackgroundShadow.animate()
                .translationY(listBackgroundInitialTranslationY - listBackgroundShadow.getHeight())
                .setStartDelay(200)
                .setInterpolator(interpolator)
                .setDuration(300)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animatedIn = true;
                    }
                });
    }

    /**
     * Used to initialize internal component.
     *
     * @param context holding context.
     */
    private void initialize(Context context) {
        LayoutInflater.from(context).inflate(R.layout.home_background_view, this);
        setBackgroundColor(
                ContextCompat.getColor(context, R.color.home_view_background_color)
        );

        animatedIn = false;

        listBackgroundShadow = findViewById(R.id.home_background_view_list_background_shadow);
        listBackground = findViewById(R.id.home_background_view_list_background);
        listBackground.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                listBackground.getViewTreeObserver().removeOnPreDrawListener(this);
                listBackgroundInitialTranslationY = START_RATIO * listBackground.getHeight();

                leftTriangle.setTranslationY(getHeight());
                rightTriangle.setTranslationY(getHeight());
                listBackground.setTranslationY(getHeight());
                listBackgroundShadow.setTranslationY(getHeight());
                return true;
            }
        });

        rightTriangle = findViewById(R.id.home_background_view_right_triangle);
        BackgroundUtils.setBackground(rightTriangle, new RightTriangleDrawable(context));

        leftTriangle = findViewById(R.id.home_background_view_left_triangle);
        BackgroundUtils.setBackground(leftTriangle, new LeftTriangleDrawable(context));

        interpolator = new DecelerateInterpolator();

    }

    /**
     * Drawable used to draw the right triangle of the background.
     */
    private static final class RightTriangleDrawable extends Drawable {

        /**
         * Points used to draw the triangle on the right.
         */
        private static final float[] RIGHT_TRIANGLE_POINTS = new float[]{
                1.00f, -0.10f,
                1.00f, 1.00f,
                0.00f, 1.00f
        };


        private final Path rightTrianglePath;
        private final Paint rightTrianglePaint;
        private final Rect bounds;

        /**
         * Drawable used to draw the right triangle of the background.
         *
         * @param context context used to initialize internal components.
         */
        public RightTriangleDrawable(Context context) {
            rightTrianglePath = new Path();
            rightTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            rightTrianglePaint.setStyle(Paint.Style.FILL);
            rightTrianglePaint.setColor(
                    ContextCompat.getColor(context, R.color.home_view_background_second_triangle_color)
            );
            bounds = new Rect();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.getClipBounds(bounds);
            int width = bounds.width();
            int height = bounds.height();

            rightTrianglePath.rewind();
            rightTrianglePath.moveTo(RIGHT_TRIANGLE_POINTS[0] * width, RIGHT_TRIANGLE_POINTS[1] * height);
            rightTrianglePath.lineTo(RIGHT_TRIANGLE_POINTS[2] * width, RIGHT_TRIANGLE_POINTS[3] * height);
            rightTrianglePath.lineTo(RIGHT_TRIANGLE_POINTS[4] * width, RIGHT_TRIANGLE_POINTS[5] * height);
            rightTrianglePath.close();
            canvas.drawPath(rightTrianglePath, rightTrianglePaint);
        }

        @Deprecated
        @Override
        public void setAlpha(int alpha) {

        }

        @Deprecated
        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSPARENT;
        }
    }

    /**
     * Drawable used to draw the left triangle of the background.
     */
    private static final class LeftTriangleDrawable extends Drawable {

        /**
         * Points used to draw the triangle on the left.
         */
        private static final float[] LEFT_TRIANGLE_POINTS = new float[]{
                -0.25f, 0.00f,
                1.00f, 0.70f,
                1.00f, 1.00f,
                -0.25f, 1.00f
        };


        private final Path leftTrianglePath;
        private final Paint leftTrianglePaint;
        private final Rect bounds;

        /**
         * Drawable used to draw the left triangle of the background.
         *
         * @param context context used to initialize internal components.
         */
        public LeftTriangleDrawable(Context context) {
            leftTrianglePath = new Path();
            leftTrianglePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            leftTrianglePaint.setStyle(Paint.Style.FILL);
            leftTrianglePaint.setColor(
                    ContextCompat.getColor(context, R.color.home_view_background_first_triangle_color)
            );
            bounds = new Rect();
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.getClipBounds(bounds);
            int width = bounds.width();
            int height = bounds.height();

            // draw the left triangle
            leftTrianglePath.rewind();
            leftTrianglePath.moveTo(LEFT_TRIANGLE_POINTS[0] * width, LEFT_TRIANGLE_POINTS[1] * height);
            leftTrianglePath.lineTo(LEFT_TRIANGLE_POINTS[2] * width, LEFT_TRIANGLE_POINTS[3] * height);
            leftTrianglePath.lineTo(LEFT_TRIANGLE_POINTS[4] * width, LEFT_TRIANGLE_POINTS[5] * height);
            leftTrianglePath.lineTo(LEFT_TRIANGLE_POINTS[6] * width, LEFT_TRIANGLE_POINTS[7] * height);
            leftTrianglePath.close();
            canvas.drawPath(leftTrianglePath, leftTrianglePaint);
        }

        @Deprecated
        @Override
        public void setAlpha(int alpha) {

        }

        @Deprecated
        @Override
        public void setColorFilter(ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSPARENT;
        }
    }


}
