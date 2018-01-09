package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPInnerMethodCall;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;
import OOP.Tests.Winter20172018.I1;

import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface IChildToPrivate extends I1 {
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToPrivate.class, methodName = "privateMethod1")
    default String toPrivateMethod1()throws OOPMultipleException {return privateMethod1();}
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToPrivate.class, methodName = "privateMethod2", argTypes = {Integer.class})
    default String toPrivateMethod2()throws OOPMultipleException{return privateMethod2(42);}
}

