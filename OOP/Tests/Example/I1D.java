package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;

@OOPMultipleInterface
public interface I1D extends I1A, I1B {

    String h() throws OOPMultipleException;

}
