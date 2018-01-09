package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

import static OOP.Solution.OOPMethodModifier.PRIVATE;
import static OOP.Solution.OOPMethodModifier.PROTECTED;
import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface I1 {
    @OOPMultipleMethod(modifier = PRIVATE)
    default String privateMethod2(Integer argument) throws OOPMultipleException {return "I1.privateMethod2()";}
    @OOPMultipleMethod(modifier = PROTECTED)
    default String protectedMethod2(Integer argument) throws OOPMultipleException {return "I1.protectedMethod2()";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String publicMethod2(Integer argument) throws OOPMultipleException {return "I1.publicMethod2";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String defaultMethod2() throws OOPMultipleException {return "I1.defaultMethod2";}
    @OOPMultipleMethod(modifier = PRIVATE)
    default String privateMethod1() throws OOPMultipleException {return "I1.privateMethod1()";}
    @OOPMultipleMethod(modifier = PROTECTED)
    default String protectedMethod1() throws OOPMultipleException {return "I1.protectedMethod1()";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String publicMethod1() throws OOPMultipleException {return "I1.publicMethod1";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String defaultMethod1() throws OOPMultipleException {return "I1.defaultMethod1";}
}