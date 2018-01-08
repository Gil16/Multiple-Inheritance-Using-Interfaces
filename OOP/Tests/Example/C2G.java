package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;

/**
 * Created by danie_000 on 6/6/2017.
 */

public class C2G implements I2G {

    @Override
    public Integer f() throws OOPMultipleException {
        Integer i = new Integer(7);
        return i;
    }

    @Override
    public void g() throws OOPMultipleException {
    }
}
