package OOP.Solution;
import OOP.Provided.OOPMultipleException;
public class OOPMultiple implements OOP.Tests.Example.I3 { 

private OOPMultipleControl dispatcher;


public OOPMultiple(OOPMultipleControl dispatcher){
this.dispatcher = dispatcher;
}

 
public java.lang.String f () throws OOPMultipleException {
return (java.lang.String) dispatcher.invoke("f", null);
}
 }