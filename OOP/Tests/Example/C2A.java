package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;

/**
 * Created by danie_000 on 6/6/2017.
 */

public class C2A implements I2A {

    @Override
    public Integer f() throws OOPMultipleException {
        Integer i = new Integer(1);
        return i;
    }
}
