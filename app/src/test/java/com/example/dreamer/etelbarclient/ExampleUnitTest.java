package com.example.dreamer.etelbarclient;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Table table;

    @Before
    void init(){
        table = new Table(1,1,true);
    }

    @Test
    public void setFalse_isCorrect(){
        table.setStatus(false);
        assertEquals(false,table.isFree());
    }

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
}