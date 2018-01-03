package OOP.Provided;

import java.lang.reflect.Method;

public class OOPInherentAmbiguity extends OOPMultipleException {

    private final Class<?> faultyClass;
    private final Method faultyMethod;
    private final Class<?> interfaceClass;

    /***
     * Builds an exception with the erroneous class.
     *
     * @param interfaceClass    The class object of of the lowest interface in the inheritance graph.
     * @param faultyClass       The class object of the <b>interface</b> that first defined the method.
     * @param faultyMethod      The method that caused the ambiguity.
     *
     * You can assume there will only be one method causing the ambiguity in the correct interface.
     * If there isn't only one, choose one of them (at random).
     */
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
