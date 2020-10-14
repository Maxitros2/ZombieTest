package me.maxitros.zombie.api;

import java.lang.reflect.Field;

public class ReflectionHelper
{
    public static Object getPrivateField(String name, Class clazz, Object object)
    {
        Field field;
        Object o = null;
        try
        {
            field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(object);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return o;
    }
}
