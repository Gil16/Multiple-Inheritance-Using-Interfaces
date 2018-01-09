package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPInnerMethodCall;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

import java.util.Arrays;
import java.util.Collections;

import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface I3Child extends I3 {
    //TODO: important: is it really what is going to be annotated?
    //@OOPMultipleMethod(modifier = PUBLIC)
    //@OOPInnerMethodCall(caller = I3Child.class, callee = I3.class, methodName = "function", argTypes = {C.class})
    //default String functionC(C string) throws OOPMultipleException { return function(string); }

    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(caller = I3Child.class, callee = I3.class, methodName = "function", argTypes = {B.class})
    default String functionB(B string) throws OOPMultipleException { return function(string); }

    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(caller = I3Child.class, callee = I3.class, methodName = "function", argTypes = {A.class})
    default String functionA(A string) throws OOPMultipleException { return function(string); }
}
