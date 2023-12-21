package com.example.salesforce.snackbar;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

/**
 * A handler for multiple {@link Snackbar}s
 */
public class SnackbarManager {

    private static final String TAG = SnackbarManager.class.getSimpleName();
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    private static WeakReference<Snackbar> snackbarReference;

    private SnackbarManager() {
    }

    /**
     * Displays a {@link Snackbar} in the current {@link Activity}, dismissing
     * the current Snackbar being displayed, if any. Note that the Activity will be obtained from
     * the Snackbar's {@link android.content.Context}. If the Snackbar was created with
     * {@link Activity#getApplicationContext()} then you must explicitly pass the target
     * Activity using {@link #show(Snackbar, Activity)}
     *
     * @param snackbar instance of {@link Snackbar} to display
     */
    public static void show(@NonNull Snackbar snackbar) {
        try {
            show(snackbar, (Activity) snackbar.getContext());
        } catch (ClassCastException e) {
            Log.e(TAG, "Couldn't get Activity from the Snackbar's Context. Try calling " +
                    "#show(Snackbar, Activity) instead", e);
        }
    }

    /**
     * Displays a {@link Snackbar} in the current {@link Activity}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar instance of {@link Snackbar} to display
     * @param activity target {@link Activity} to display the Snackbar
     */
    public static void show(@NonNull final Snackbar snackbar, @NonNull final Activity activity) {
        MAIN_THREAD.post(new Runnable() {
            @Override
            public void run() {
                Snackbar currentSnackbar = getCurrentSnackbar();
                if (currentSnackbar != null) {
                    if (currentSnackbar.isShowing() && !currentSnackbar.isDimissing()) {
                        currentSnackbar.dismissAnimation(false);
                        currentSnackbar.dismissByReplace();
                        snackbarReference = new WeakReference<Snackbar>(snackbar);
                        snackbar.showAnimation(false);
                        snackbar.showByReplace(activity);
                        return;
                    }
                    currentSnackbar.dismiss();
                }
                snackbarReference = new WeakReference<Snackbar>(snackbar);
                snackbar.show(activity);
            }
        });
    }

    /**
     * Displays a {@link Snackbar} in the specified {@link ViewGroup}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar instance of {@link Snackbar} to display
     * @param parent   parent {@link ViewGroup} to display the Snackbar
     */
    public static void show(@NonNull Snackbar snackbar, @NonNull ViewGroup parent) {
        show(snackbar, parent, Snackbar.shouldUsePhoneLayout(snackbar.getContext()));
    }

    /**
     * Displays a {@link Snackbar} in the specified {@link ViewGroup}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar       instance of {@link Snackbar} to display
     * @param parent         parent {@link ViewGroup} to display the Snackbar
     * @param usePhoneLayout true: use phone layout, false: use tablet layout
     */
    public static void show(@NonNull final Snackbar snackbar, @NonNull final ViewGroup parent,
                            final boolean usePhoneLayout) {
        MAIN_THREAD.post(new Runnable() {
            @Override
            public void run() {
                Snackbar currentSnackbar = getCurrentSnackbar();
                if (currentSnackbar != null) {
                    if (currentSnackbar.isShowing() && !currentSnackbar.isDimissing()) {
                        currentSnackbar.dismissAnimation(false);
                        currentSnackbar.dismissByReplace();
                        snackbarReference = new WeakReference<Snackbar>(snackbar);
                        snackbar.showAnimation(false);
                        snackbar.showByReplace(parent, usePhoneLayout);
                        return;
                    }
                    currentSnackbar.dismiss();
                }
                snackbarReference = new WeakReference<Snackbar>(snackbar);
                snackbar.show(parent, usePhoneLayout);
            }
        });
    }

    /**
     * Dismisses the {@link Snackbar} shown by this manager.
     */
    public static void dismiss() {
        final Snackbar currentSnackbar = getCurrentSnackbar();
        if (currentSnackbar != null) {
            MAIN_THREAD.post(new Runnable() {
                @Override
                public void run() {
                    currentSnackbar.dismiss();
                }
            });
        }
    }

    /**
     * Return the current Snackbar
     */
    public static Snackbar getCurrentSnackbar() {
        if (snackbarReference != null) {
            return snackbarReference.get();
        }
        return null;
    }
}
