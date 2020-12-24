package com.app.tool;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.ArrayList;

public class IntentBuilder extends Intent {
    private final Context context;
    private Fragment fragment;

    public IntentBuilder(String action, Context context) {
        super(action);
        this.context = context;
    }

    public IntentBuilder(String action, Uri uri, Context context) {
        super(action, uri);
        this.context = context;
    }

    public IntentBuilder(Context packageContext, Class<?> cls) {
        super(packageContext, cls);
        this.context = packageContext;
    }

    public IntentBuilder(String action, Uri uri, Context packageContext, Class<?> cls) {
        super(action, uri, packageContext, cls);
        this.context = packageContext;
    }

    public IntentBuilder(String action, Fragment fragment) {
        super(action);
        this.fragment = fragment;
        context = fragment.getContext();
    }

    public IntentBuilder(String action, Uri uri, Fragment fragment) {
        super(action, uri);
        this.fragment = fragment;
        context = fragment.getContext();
    }

    public IntentBuilder(Fragment fragment, Class<?> cls) {
        super(fragment.getContext(), cls);
        this.fragment = fragment;
        context = fragment.getContext();
    }

    public IntentBuilder(String action, Uri uri, Fragment fragment, Class<?> cls) {
        super(action, uri, fragment.getContext(), cls);
        this.fragment = fragment;
        context = fragment.getContext();
    }

    public static Builder with(Context context) {
        return new Builder(context);
    }

    public static Builder with(Fragment fragment) {
        return new Builder(fragment);
    }

    public void start() {
        if (fragment != null) {
            fragment.startActivity(this);
        } else {
            context.startActivity(this);
        }
    }

    public void startActivityForResult(int requestCode) {
        if (fragment != null) {
            fragment.startActivityForResult(this, requestCode);
        } else if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(this, requestCode);
        }
    }

    public void startService() {
        context.startService(this);
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, boolean value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, byte value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, char value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, short value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, int value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, long value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, float value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, double value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, String value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, CharSequence value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, Parcelable value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, Parcelable[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putParcelableArrayListExtra(String name, ArrayList<? extends Parcelable> value) {
        super.putParcelableArrayListExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putIntegerArrayListExtra(String name, ArrayList<Integer> value) {
        super.putIntegerArrayListExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putStringArrayListExtra(String name, ArrayList<String> value) {
        super.putStringArrayListExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putCharSequenceArrayListExtra(String name, ArrayList<CharSequence> value) {
        super.putCharSequenceArrayListExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, Serializable value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, boolean[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, byte[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, short[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, char[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, int[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, long[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, float[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, double[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, String[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, CharSequence[] value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtra(String name, Bundle value) {
        super.putExtra(name, value);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtras(@NonNull Intent src) {
        super.putExtras(src);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder putExtras(@NonNull Bundle extras) {
        super.putExtras(extras);
        return this;
    }

    @NonNull
    @Override
    public IntentBuilder setAction(@Nullable String action) {
        return (IntentBuilder) super.setAction(action);
    }

    @NonNull
    @Override
    public IntentBuilder setData(@Nullable Uri data) {
        return (IntentBuilder) super.setData(data);
    }

    @NonNull
    @Override
    public IntentBuilder setDataAndNormalize(@NonNull Uri data) {
        return (IntentBuilder) super.setDataAndNormalize(data);
    }

    @NonNull
    @Override
    public IntentBuilder setType(@Nullable String type) {
        return (IntentBuilder) super.setType(type);
    }

    @NonNull
    @Override
    public IntentBuilder setTypeAndNormalize(@Nullable String type) {
        return (IntentBuilder) super.setTypeAndNormalize(type);
    }

    @NonNull
    @Override
    public IntentBuilder setDataAndType(@Nullable Uri data, @Nullable String type) {
        return (IntentBuilder) super.setDataAndType(data, type);
    }

    @NonNull
    @Override
    public IntentBuilder setDataAndTypeAndNormalize(@NonNull Uri data, @Nullable String type) {
        return (IntentBuilder) super.setDataAndTypeAndNormalize(data, type);
    }

    @NonNull
    @Override
    public IntentBuilder setIdentifier(@Nullable String identifier) {
        return (IntentBuilder) super.setIdentifier(identifier);
    }

    @NonNull
    @Override
    public IntentBuilder addCategory(String category) {
        return (IntentBuilder) super.addCategory(category);
    }

    @NonNull
    @Override
    public IntentBuilder replaceExtras(@NonNull Intent src) {
        return (IntentBuilder) super.replaceExtras(src);
    }

    @NonNull
    @Override
    public IntentBuilder replaceExtras(@Nullable Bundle extras) {
        return (IntentBuilder) super.replaceExtras(extras);
    }

    @NonNull
    @Override
    public IntentBuilder setFlags(int flags) {
        return (IntentBuilder) super.setFlags(flags);
    }

    @NonNull
    @Override
    public IntentBuilder addFlags(int flags) {
        return (IntentBuilder) super.addFlags(flags);
    }

    @NonNull
    @Override
    public IntentBuilder setPackage(@Nullable String packageName) {
        return (IntentBuilder) super.setPackage(packageName);
    }

    @NonNull
    @Override
    public IntentBuilder setComponent(@Nullable ComponentName component) {
        return (IntentBuilder) super.setComponent(component);
    }

    @NonNull
    @Override
    public IntentBuilder setClassName(@NonNull Context packageContext, @NonNull String className) {
        return (IntentBuilder) super.setClassName(packageContext, className);
    }

    @NonNull
    @Override
    public IntentBuilder setClassName(@NonNull String packageName, @NonNull String className) {
        return (IntentBuilder) super.setClassName(packageName, className);
    }

    @NonNull
    @Override
    public IntentBuilder setClass(@NonNull Context packageContext, @NonNull Class<?> cls) {
        return (IntentBuilder) super.setClass(packageContext, cls);
    }

    public static class Builder {
        private Context context;
        private Fragment fragment;

        private Builder(Context context) {
            this.context = context;
        }

        private Builder(Fragment fragment) {
            this.fragment = fragment;
        }

        public IntentBuilder action(String action) {
            if (fragment != null) {
                return new IntentBuilder(action, fragment);
            }
            return new IntentBuilder(action, context);
        }

        public IntentBuilder action(String action, Uri uri) {
            if (fragment != null) {
                return new IntentBuilder(action, uri, fragment);
            }
            return new IntentBuilder(action, uri, context);
        }

        public IntentBuilder action(String action, Uri uri, Class<?> cls) {
            if (fragment != null) {
                return new IntentBuilder(action, uri, fragment, cls);
            }
            return new IntentBuilder(action, uri, context, cls);
        }

        public IntentBuilder clazz(Class<?> cls) {
            if (fragment != null) {
                return new IntentBuilder(fragment, cls);
            }
            return new IntentBuilder(context, cls);
        }
    }
}
