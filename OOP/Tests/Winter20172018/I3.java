package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

import static OOP.Solution.OOPMethodModifier.PRIVATE;
import static OOP.Solution.OOPMethodModifier.PROTECTED;
import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface I3 {
    @OOPMultipleMethod(modifier = PRIVATE)
    default String function(C string) throws OOPMultipleException { return "Private"; }

    @OOPMultipleMethod(modifier = PROTECTED)
    default String function(B string) throws OOPMultipleException { return "Protected"; }

    @OOPMultipleMethod(modifier = PUBLIC)
    default String function(A string) throws OOPMultipleException { return "Public"; }

    @OOPMultipleMethod(modifier = PUBLIC)
    default String anotherFunction(D d) throws OOPMultipleException {return "D"; }

    @OOPMultipleMethod(modifier = PUBLIC)
    default String anotherFunction(A a) throws OOPMultipleException {return "A"; }
}
