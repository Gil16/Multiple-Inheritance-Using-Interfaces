package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/8/2017.
 */
@OOPMultipleInterface
public interface I7B {
    @OOPMultipleMethod
    default String f(A p1,D p2,A p3) throws OOPMultipleException {return "";}
}
