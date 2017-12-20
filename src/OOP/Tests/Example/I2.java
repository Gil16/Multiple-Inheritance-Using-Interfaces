package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPInnerMethodCall;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

@OOPMultipleInterface
public interface I2 {

    @OOPMultipleMethod
    @OOPInnerMethodCall(caller = I2.class, callee = I1.class, methodName = "f")
    default String g(I1 i) throws OOPMultipleException {
        return i.f();
    }
}
