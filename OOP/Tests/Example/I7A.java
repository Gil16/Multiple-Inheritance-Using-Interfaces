package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/8/2017.
 */
@OOPMultipleInterface
public interface I7A {
    @OOPMultipleMethod
    default String f(D p1,A p2,A p3) throws OOPMultipleException{return "";}
}
