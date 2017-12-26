package OOP.Solution;

import OOP.Provided.*;

import java.io.File;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

public class OOPMultipleControl {

    private Class<?> interfaceClass;
    private File sourceFile;

    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateInheritanceGraph() throws OOPMultipleException {
        if(this.interfaceClass.isAnnotationPresent(OOPMultipleInterface.class)) {
            throw new OOPBadClass(interfaceClass);
        }
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();     // maybe set?
        getAllInterfacesInTheGraph(interfaceClass, interfacesList);
        // Checking if all the interfaces has OOPMultipleInterface annotation
        for (Class<?> anInterface : interfacesList){
            if(!anInterface.isAnnotationPresent(OOPMultipleInterface.class)) {
                throw new OOPBadClass(anInterface);
            }
        }
                        // get all classes??
        boolean flag = false;
        // The class and methods are under the given conditions
        for (Class<?> anInterface : interfacesList) {
            for (Method aMethod : anInterface.getDeclaredMethods()) {
                if(!(aMethod.isAnnotationPresent(OOPMultipleMethod.class))) {
                    throw new OOPBadClass(aMethod);
                }
                //
                if(aMethod.isAnnotationPresent(OOPInnerMethodCall.class)){
                    Class<?> Callee = aMethod.getAnnotation(OOPInnerMethodCall.class).callee();
                    try {
                        if(Callee.getMethod(aMethod.getAnnotation(OOPInnerMethodCall.class).methodName(),
                                aMethod.getAnnotation(OOPInnerMethodCall.class).argTypes())
                                .getAnnotation(OOPMultipleMethod.class).modifier() == OOPMethodModifier.PRIVATE ){
                            flag = true;
                        }
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        if(flag){
            throw new ;
        }
        // There are no illegal inner calling in the graph


        // There are no inheritance ambiguities in the graph


    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {
        return null;
    }

    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }


    public void getAllInterfacesInTheGraph(Class<?> interfaceClass, List<Class<?>> anInterfacesList) {
        if (interfaceClass == null) {
            return;
        }
        // circular graph???
        Class<?>[] allInterfaces = interfaceClass.getInterfaces();
        if (allInterfaces == null) {
            return;
        }
        for (Class<?> anInterface : allInterfaces) {
            anInterfacesList.add(anInterface);
            getAllInterfacesInTheGraph(anInterface, anInterfacesList);
        }
    }
}

