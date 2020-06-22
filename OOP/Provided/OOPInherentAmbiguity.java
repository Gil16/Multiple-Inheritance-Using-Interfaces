package OOP.Provided;

import java.lang.reflect.Method;

public class OOPInherentAmbiguity extends OOPMultipleException {

    private final Class<?> faultyClass;
    private final Method faultyMethod;
    private final Class<?> interfaceClass;


    public OOPInherentAmbiguity(Class<?> interfaceClass, Class<?> faultyClass, Method faultyMethod) {
        this.interfaceClass = interfaceClass;
        this.faultyClass = faultyClass;
        this.faultyMethod = faultyMethod;
    }

    @Override
    public String getMessage() {
        return "OOPMultiple" + " Could not be generated from " + interfaceClass.getName() + "\n" +
                "because of Inherent Ambiguity, caused by inheriting method: " + faultyMethod.getName() + "\n" +
                "which is first declared in : " + faultyClass.getName();
    }
}
