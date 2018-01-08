package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleMethod;

public interface I1C extends I1A, I1B {


    @OOPMultipleMethod
    String h() throws OOPMultipleException;

}
