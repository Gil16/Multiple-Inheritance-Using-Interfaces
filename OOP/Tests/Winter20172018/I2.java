package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

import static OOP.Solution.OOPMethodModifier.PRIVATE;
import static OOP.Solution.OOPMethodModifier.PROTECTED;
import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface I2 {
    @OOPMultipleMethod(modifier = PRIVATE)
    default String privateMethod2(String argument) throws OOPMultipleException {return "I1.privateMethod2()";}
    @OOPMultipleMethod(modifier = PROTECTED)
    default String protectedMethod2(String argument)throws OOPMultipleException{return "I1.protectedMethod2()";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String publicMethod2(String argument)throws OOPMultipleException{return "I1.publicMethod2";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String defaultMethod3()throws OOPMultipleException{return "I1.defaultMethod2";}
    @OOPMultipleMethod(modifier = PRIVATE)
    default String privateMethod4()throws OOPMultipleException{return "I1.privateMethod1()";}
    @OOPMultipleMethod(modifier = PROTECTED)
    default String protectedMethod4()throws OOPMultipleException{return "I1.protectedMethod1()";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String publicMethod4()throws OOPMultipleException{return "I1.publicMethod1";}
    @OOPMultipleMethod(modifier = PUBLIC)
    default String defaultMethod4()throws OOPMultipleException{return "I1.defaultMethod1";}
}