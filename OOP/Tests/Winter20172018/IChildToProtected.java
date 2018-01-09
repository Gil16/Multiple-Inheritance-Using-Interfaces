package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPInnerMethodCall;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;
import OOP.Tests.Winter20172018.I1;

import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface IChildToProtected extends I1 {
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToProtected.class, methodName = "protectedMethod1")
    default String toProtectedMethod1()throws OOPMultipleException {return protectedMethod1();}
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToProtected.class, methodName = "protectedMethod2", argTypes = {Integer.class})
    default String toProtectedMethod2()throws OOPMultipleException{return protectedMethod2(42);}
}
