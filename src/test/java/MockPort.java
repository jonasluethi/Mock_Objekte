package java;

import main.java.Port;
import main.java.ReadError;
import org.testng.Assert;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

public class MockPort extends PrintStream implements Port {

    private ArrayList list;
    private Iterator outIterator;
    private Iterator inIterator;
    private boolean isClosed;
    private  boolean isOpen;

    public MockPort(){
        super(System.out);
        list = new ArrayList();
        list.add("E004010002035FE7");
        list.add("0900000033 E52E12");
        list.add("E004010002035BFB");
        inIterator = list.iterator();
        outIterator = list.iterator();
    }

    @Override
    public void open() {
        isOpen = true;
        isClosed = false;

    }

    @Override
    public void close() {
        isClosed = true;
        isOpen = false;
    }

    @Override
    public String read() throws ReadError {
        if (!inIterator.hasNext()){
            return null;
        }
        return (String) inIterator.next();
    }

    public void println(String s){
        if (!outIterator.hasNext()){
            Assert.fail("overrun");
        }
        if (!s.equals( (String) outIterator.next())){
            Assert.fail("changed sequence");
        }
    }
    public void verify(){
        if (! isClosed){
            Assert.fail("port not closed");
        }
    }
}
