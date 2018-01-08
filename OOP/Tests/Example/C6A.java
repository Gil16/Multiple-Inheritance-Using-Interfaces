package OOP.Tests.Example;

import OOP.Provided.OOPMultipleException;

/**
 * Created by danie_000 on 6/8/2017.
 */
public class C6A implements I6A {
    public void f_override() throws OOPMultipleException{

    }
    public String g() throws OOPMultipleException{
        return "I6A";
    }
    public String h() throws OOPMultipleException{
        return "Summoned h :)";
    }
    public void k() throws OOPMultipleException{}
}
