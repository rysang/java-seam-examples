package org.price.test;

import junit.framework.TestCase;

import org.price.test.beans.converters.EntityConverter;
import org.price.test.conversion.annotations.Store;

public class TestConverter extends TestCase {
    public void testNothing() throws Exception {

        Test1 t1 = new Test1("mine", "sd");

        //new EntityConverter().convertToEntity(t1);
    }
}

class Test1 {
    @Store
    private String name;

    @Store
    private String title;

    public Test1() {

    }

    public Test1(String name, String title) {
        super();
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

class Test2 extends Test1 {

    @Store
    private String test2Name;

    public Test2() {

    }

    public Test2(String test2Name) {
        super();
        this.test2Name = test2Name;
    }

    public String getTest2Name() {
        return test2Name;
    }

    public void setTest2Name(String test2Name) {
        this.test2Name = test2Name;
    }

}
