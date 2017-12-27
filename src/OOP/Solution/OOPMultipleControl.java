package OOP.Solution;

import OOP.Provided.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import static OOP.Provided.OOPInaccessibleMethod.*;

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
        List<ForbiddenAccess> badMethods = new LinkedList<ForbiddenAccess>();
        for (Class<?> anInterface : interfacesList) {
            for (Method aMethod : anInterface.getDeclaredMethods()) {
                if (!(aMethod.isAnnotationPresent(OOPMultipleMethod.class))) {
                    throw new OOPBadClass(aMethod);
                }
                //
                Class<?> Callee = aMethod.getAnnotation(OOPInnerMethodCall.class).callee();
                Class<?> Caller = aMethod.getAnnotation(OOPInnerMethodCall.class).caller();
                String MethodName = aMethod.getAnnotation(OOPInnerMethodCall.class).methodName();
                Class<?>[] ArgTypes = aMethod.getAnnotation(OOPInnerMethodCall.class).argTypes();
                Method calledMethod = null;
                try {
                    calledMethod = Callee.getDeclaredMethod(MethodName, ArgTypes);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();        // Should never get here!! if it does, it's your problem!
                }
                ForbiddenAccess badAccess = new ForbiddenAccess(Caller, Callee, aMethod);
                if (calledMethod.getAnnotation(OOPMultipleMethod.class).modifier() == OOPMethodModifier.PRIVATE) {
                    badMethods.add(badAccess);
                } else if (calledMethod.getAnnotation(OOPMultipleMethod.class).modifier() == OOPMethodModifier.PROTECTED) {
                    if (!checkIfInterface1IsInInterface2Hierarchy(Callee, Caller)) {
                        badMethods.add(badAccess);
                    }
                }
            }
        }
        if(badMethods.size() != 0){
            throw new OOPInaccessibleMethod(badMethods);
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

    public boolean checkIfInterface1IsInInterface2Hierarchy(Class<?> I1, Class<?> I2){
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        getAllInterfacesInTheGraph(I2, interfacesList);
        return interfacesList.contains(I1);
    }

}

