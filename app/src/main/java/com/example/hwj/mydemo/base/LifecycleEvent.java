package com.example.hwj.mydemo.base;

import android.os.Bundle;

public class LifecycleEvent {

    public final boolean positive;
    public final boolean alreadyShown;
    public final String name;

    public LifecycleEvent(String name, boolean goingToShow, boolean alreadyShown) {
        this.name = name;
        this.positive = goingToShow;
        this.alreadyShown = alreadyShown;
    }

    public abstract static class LifeCycleWithBundle extends LifecycleEvent {

        public final Bundle bundle;

        public LifeCycleWithBundle(String name, Bundle bundle, boolean alreadyShown) {
            super(name, true, alreadyShown);
            this.bundle = bundle;
        }
    }

    public static LifecycleEvent Attach = new LifecycleEvent("Attach", true, false);

    public static class Create extends LifeCycleWithBundle {
        public Create(Bundle bundle) {
            super("Create", bundle, false);
        }
    }

    public static class CreateView extends LifeCycleWithBundle {
        public CreateView(Bundle bundle) {
            super("CreateView", bundle, false);
        }
    }

    public static LifecycleEvent AfterCreate = new LifecycleEvent("AfterCreate", true, false);
    public static LifecycleEvent AfterCreateView = new LifecycleEvent("AfterCreateView", true, false);
    public static LifecycleEvent Start = new LifecycleEvent("Start", true, true);
    public static LifecycleEvent Resume = new LifecycleEvent("Resume", true, true);
    public static LifecycleEvent Pause = new LifecycleEvent("Pause", false, true);
    public static LifecycleEvent Stop = new LifecycleEvent("Stop", false, false);
    public static LifecycleEvent DestroyView = new LifecycleEvent("DestroyView", false, false);
    public static LifecycleEvent Destroy = new LifecycleEvent("Destroy", false, false);
    public static LifecycleEvent Detach = new LifecycleEvent("Detach", false, false);
}
