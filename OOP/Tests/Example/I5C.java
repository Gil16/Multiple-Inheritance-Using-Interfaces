package OOP.Tests.Example;


import OOP.Provided.OOPMultipleException;
import OOP.Solution.OOPMultipleInterface;
import OOP.Solution.OOPMultipleMethod;

/**
 * Created by danie_000 on 6/7/2017.
 */
@OOPMultipleInterface
public interface I5C extends  I5A,I5B{
    @OOPMultipleMethod
     default void f(C p1, B p2, C p3,A p4) throws OOPMultipleException{}
    @OOPMultipleMethod
     default String g(C p1, B p2, C p3) throws OOPMultipleException{return "";}
}
