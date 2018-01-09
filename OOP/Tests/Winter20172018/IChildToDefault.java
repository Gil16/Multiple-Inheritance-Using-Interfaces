package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPInnerMethodCall;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;
import OOP.Tests.Winter20172018.I1;

import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface IChildToDefault extends I1 {
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToPublic.class, methodName = "publicMethod1")
    default String toDefaultMethod1()throws OOPMultipleException {return publicMethod1();}
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToPublic.class, methodName = "publicMethod2", argTypes = {Integer.class})
    default String toDefaultMethod2()throws OOPMultipleException{return publicMethod2(42);}
}

