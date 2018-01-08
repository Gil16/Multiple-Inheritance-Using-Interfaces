package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/6/2017.
 */
@OOPMultipleInterface
public interface I2C extends I2A,I2B {
    @OOPMultipleMethod
    Integer f() throws OOPMultipleException;
}
