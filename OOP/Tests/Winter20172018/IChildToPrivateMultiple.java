package OOP.Tests.Winter20172018;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPInnerMethodCall;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;
import OOP.Tests.Winter20172018.I1;
import OOP.Tests.Winter20172018.I2;
import OOP.Tests.Winter20172018.IChildToPrivate;

import static OOP.Solution.OOPMethodModifier.PUBLIC;

@OOPMultipleInterface
public interface IChildToPrivateMultiple extends I1, I2 {
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToPrivateMultiple.class, methodName = "privateMethod1")
    default String toPrivateMethod1()throws OOPMultipleException {return privateMethod1();}
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I1.class, caller = IChildToPrivateMultiple.class, methodName = "privateMethod2", argTypes = {Integer.class})
    default String toPrivateMethod2()throws OOPMultipleException{return privateMethod2(42);}
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I2.class, caller = IChildToPrivateMultiple.class, methodName = "privateMethod4")
    default String toPrivateMethod3()throws OOPMultipleException{return privateMethod4();}
    @OOPMultipleMethod(modifier = PUBLIC)
    @OOPInnerMethodCall(callee=I2.class, caller = IChildToPrivateMultiple.class, methodName = "privateMethod2", argTypes = {String.class})
    default String toPrivateMethod4()throws OOPMultipleException{return privateMethod2("42");}
}
