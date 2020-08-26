package com.app.tool;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个管理生命周期的工具,可以贯穿整个生命周期不必考虑内存泄漏问题,自动释放
 */
public final class LifeHelper implements LifecycleEventObserver {
    private static Map<String, LifeHelper> sHelper;//String:Context.class.getName()
    private Map<String, Object> map;//String:Class<T>.getName()
    private String mTag;

    private LifeHelper(String tag) {
        mTag = tag;
    }

    public static LifeHelper with(@NonNull String mTag) {
        checkHelper();
        LifeHelper life = sHelper.get(mTag);
        if (life == null) {
            life = new LifeHelper(mTag);
            sHelper.put(mTag, life);
        }
        return life;
    }

    public static LifeHelper with(@NonNull LifecycleOwner owner) {
        final String key = owner.toString();
        LifeHelper helper = with(key);
        helper.bindLifeOwner(owner);
        return helper;
    }

    public static void destroy(@NonNull String tag) {
        if (sHelper != null) {
            LifeHelper life = sHelper.get(tag);
            life.destroy();
        }
    }

    public static void destroy(@NonNull LifecycleOwner owner) {
        destroy(owner.toString());
    }

    private static void checkHelper() {
        if (sHelper == null) {
            sHelper = new HashMap<>();
        }
    }

    private void checkMaps() {
        if (map == null) {
            map = new HashMap<>();
        }
    }

    /**
     * 获取
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T get(Class<T> tClass) {
        String className = tClass.getName();
        checkMaps();
        Object o = map.get(className);
        if (o == null) {
            return null;
        }
        return (T) o;
    }

    /**
     * 设置
     *
     * @param tClass
     * @param life
     * @param <T>
     */
    public <T> void set(Class<T> tClass, @NonNull T life) {
        checkMaps();
        map.put(tClass.getName(), life);
    }

    /**
     * 有则返回无则创建
     *
     * @param tClass
     * @param objects
     * @param <T>
     * @return
     */
    public <T> T create(Class<T> tClass, Object... objects) {
        T lifeHelper = get(tClass);
        if (lifeHelper == null) {
            lifeHelper = ClassUtils.newInstance(tClass, objects);
            map.put(tClass.getName(), lifeHelper);
        }
        return lifeHelper;
    }

    /**
     * 绑定生命周期
     *
     * @param mOwner
     * @return
     */
    public LifeHelper bindLifeOwner(LifecycleOwner mOwner) {
        mOwner.getLifecycle().addObserver(this);
        return this;
    }

    /**
     * 注销
     */
    public synchronized void destroy() {
        MapUtils.clear(map);
        sHelper.remove(mTag);
        if (EmptyUtils.isEmpty(sHelper)) {
            sHelper = null;
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            if (map != null) {
                for (String key : map.keySet()) {
                    Object helper = map.get(key);
                    if (helper instanceof LifecycleEventObserver) {
                        ((LifecycleEventObserver) helper).onStateChanged(source, event);
                    }
                    if (helper instanceof LifeRecycler) {
                        ((LifeRecycler) helper).recycler();
                    }
                }
            }
            destroy();
        } else {
            if (map != null) {
                for (String key : map.keySet()) {
                    Object helper = map.get(key);
                    if (helper instanceof LifecycleEventObserver) {
                        ((LifecycleEventObserver) helper).onStateChanged(source, event);
                    }
                }
            }
        }
    }

    public interface LifeRecycler {
        void recycler();
    }
}
