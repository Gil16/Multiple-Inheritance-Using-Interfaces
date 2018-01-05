package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

@OOPMultipleInterface
public interface I4 {

    @OOPMultipleMethod
    default String f() throws OOPMultipleException {
        return "I4 : f";
    }
}
