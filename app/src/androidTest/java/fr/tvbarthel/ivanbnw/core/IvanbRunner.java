package fr.tvbarthel.ivanbnw.core;

import android.app.Application;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.test.runner.AndroidJUnitRunner;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;

/**
 * Runner used to override the application only for instrumentation.
 * <p/>
 * See also : {@link InstrumentationIvanbApplication}
 */
public class IvanbRunner extends AndroidJUnitRunner {

    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, InstrumentationIvanbApplication.class.getName(), context);
    }

    @Override
    public void onStart() {
        runOnMainSync(new Runnable() {
            @Override
            public void run() {
                Context app = getTargetContext().getApplicationContext();

                String name = InstrumentationIvanbApplication.class.getSimpleName();
                // Unlock the device so that the tests can input keystrokes.
                KeyguardManager keyguard = (KeyguardManager) app.getSystemService(KEYGUARD_SERVICE);
                keyguard.newKeyguardLock(name).disableKeyguard();
                // Wake up the screen.
                PowerManager power = (PowerManager) app.getSystemService(POWER_SERVICE);
                power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, name)
                        .acquire();
            }
        });

        super.onStart();
    }
}
