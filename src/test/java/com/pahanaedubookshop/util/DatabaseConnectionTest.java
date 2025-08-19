package com.pahanaedubookshop.util;

import org.junit.After;
import org.junit.Assume;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class DatabaseConnectionTest {

    /** Try once to see if DB is reachable; skip tests if not */
    private boolean dbAvailable() {
        try {
            DatabaseConnection.getInstance().getConnection();
            return true;
        } catch (Throwable t) {
            return false;
        }
    }

    /** Reset singleton between tests so they don't interfere */
    @After
    public void resetSingleton() throws Exception {
        Field f = DatabaseConnection.class.getDeclaredField("instance");
        f.setAccessible(true);
        f.set(null, null);
    }

    @Test
    public void singletonReturnsSameInstance() throws Exception {
        Assume.assumeTrue("Skipping: DB not reachable", dbAvailable());

        DatabaseConnection a = DatabaseConnection.getInstance();
        DatabaseConnection b = DatabaseConnection.getInstance();

        assertSame("Should be the same singleton instance", a, b);
    }

    @Test
    public void connectionIsOpenAndValid() throws Exception {
        Assume.assumeTrue("Skipping: DB not reachable", dbAvailable());

        Connection c = DatabaseConnection.getInstance().getConnection();

        assertNotNull("Connection must not be null", c);
        assertFalse("Connection should be open", c.isClosed());
        assertTrue("Connection should be valid", c.isValid(5));
    }

    @Test
    public void recreatesConnectionAfterClose() throws Exception {
        Assume.assumeTrue("Skipping: DB not reachable", dbAvailable());

        DatabaseConnection before = DatabaseConnection.getInstance();
        Connection c1 = before.getConnection();
        assertTrue(c1.isValid(5));

        // Simulate drop
        c1.close();
        assertTrue(c1.isClosed());

        // getInstance() should rebuild a new one because the old is closed
        DatabaseConnection after = DatabaseConnection.getInstance();
        Connection c2 = after.getConnection();

        assertNotSame("Singleton instance should be replaced after close", before, after);
        assertFalse("New connection should be open", c2.isClosed());
        assertTrue("New connection should be valid", c2.isValid(5));
    }

    @Test
    public void threadSafety_getInstance() throws Exception {
        Assume.assumeTrue("Skipping: DB not reachable", dbAvailable());

        int threads = 10;
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(threads);
        Set<DatabaseConnection> seen = Collections.synchronizedSet(new HashSet<>());

        for (int i = 0; i < threads; i++) {
            new Thread(() -> {
                try {
                    start.await();
                    seen.add(DatabaseConnection.getInstance());
                } catch (Exception ignored) {
                } finally {
                    done.countDown();
                }
            }).start();
        }

        start.countDown();
        assertTrue("All threads should finish", done.await(5, TimeUnit.SECONDS));
        assertEquals("All threads must see the same instance", 1, seen.size());
    }
}
