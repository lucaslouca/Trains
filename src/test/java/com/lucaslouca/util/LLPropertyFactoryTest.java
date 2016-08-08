package com.lucaslouca.util;

import org.junit.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@code LLPropertyFactory}.
 */
@SuppressWarnings("unchecked")
public class LLPropertyFactoryTest {
    private static Locale defaultLocale;

    @BeforeClass
    public static void starting() {
        defaultLocale = Locale.getDefault();
    }

    @AfterClass
    public static void finished() {
        Locale.setDefault(defaultLocale);
    }

    @Test
    public void testLocale1() {
        String ans = LLPropertyFactory.propertyWithArgs("exception.directed_graph.invalid_edge");
        assertEquals("Invalid edge!", ans);
    }

    @Test
    public void testLocale2() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Locale.setDefault(new Locale("de", "DE"));

        // Magic
        Class clazz = Class.forName(LLPropertyFactory.class.getName());
        Method method = clazz.getDeclaredMethod("loadProperties");
        method.setAccessible(true);
        method.invoke(null);

        String ans = LLPropertyFactory.propertyWithArgs("exception.directed_graph.invalid_edge");
        assertEquals("Ungueltige Kante!", ans);
    }
}
