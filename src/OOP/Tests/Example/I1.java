package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

@OOPMultipleInterface
public interface I1 {

    @OOPMultipleMethod
    default String f() throws OOPMultipleException {
        return "I1 : f";
    }
}
