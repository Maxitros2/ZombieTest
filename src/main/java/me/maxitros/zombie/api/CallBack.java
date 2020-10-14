package me.maxitros.zombie.api;

public interface CallBack<V extends Object, T extends Throwable>
{
    public void call(V result, T thrown);
}
