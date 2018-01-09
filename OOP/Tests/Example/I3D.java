package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/6/2017.
 */
public interface I3D extends I3B,I3C {
    @OOPMultipleMethod
    void f() throws OOPMultipleException;
}
