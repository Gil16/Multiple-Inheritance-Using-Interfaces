package OOP.Solution;

import OOP.Provided.*;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

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
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        Set<Class<?>> duplicateSet = new HashSet<Class<?>>();
        getAllInterfacesInTheGraph(interfaceClass, interfacesList, duplicateSet);

        // Checking if all the interfaces has OOPMultipleInterface annotation
        for (Class<?> anInterface : interfacesList){
            if(!anInterface.isAnnotationPresent(OOPMultipleInterface.class)) {
                throw new OOPBadClass(anInterface);
            }
        }

        // Checking if there is illegal inner calling in the graph
        List<ForbiddenAccess> badMethods = new LinkedList<ForbiddenAccess>();
        for (Class<?> anInterface : interfacesList) {
            for (Method aMethod : anInterface.getDeclaredMethods()) {
                if (!(aMethod.isAnnotationPresent(OOPMultipleMethod.class))) {
                    throw new OOPBadClass(aMethod);
                }
                if(aMethod.isAnnotationPresent(OOPInnerMethodCall.class)) {
                    Class<?> Callee = aMethod.getAnnotation(OOPInnerMethodCall.class).callee();
                    Class<?> Caller = aMethod.getAnnotation(OOPInnerMethodCall.class).caller();
                    String MethodName = aMethod.getAnnotation(OOPInnerMethodCall.class).methodName();
                    Class<?>[] ArgTypes = aMethod.getAnnotation(OOPInnerMethodCall.class).argTypes();
                    Method calledMethod = null;
                    try {
                        calledMethod = Callee.getDeclaredMethod(MethodName, ArgTypes);
                    } catch (NoSuchMethodException e) {
                                // Should never get here!! if it does, it's your problem!
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
        }
        if(badMethods.size() != 0){
            throw new OOPInaccessibleMethod(badMethods);
        }

        // There are no inheritance ambiguities in the graph
        if(duplicateSet.size() > 0){
            // get all the duplicates in the graph (even their "fathers")
            List<Class<?>> tempList = duplicateSet.stream().collect(Collectors.toList());
            for(Class<?> anInterface : tempList){
                Class<?> interfaceIterator = anInterface;
                while(interfaceIterator.getInterfaces().length > 0){
                    duplicateSet.add(interfaceIterator.getInterfaces()[0]); // get the interface "father"
                    interfaceIterator = interfaceIterator.getInterfaces()[0];
                }
            }
            for(Class<?> anInterface : interfacesList){
                for(Method aMethod : anInterface.getDeclaredMethods()){
                    if(aMethod.isAnnotationPresent(OOPInnerMethodCall.class)){
                        if(duplicateSet.contains(aMethod.getAnnotation(OOPInnerMethodCall.class).callee())){
                            Class<?> highestInterface = aMethod.getAnnotation(OOPInnerMethodCall.class).callee();
                            Class<?> interfaceIterator = highestInterface;
                            while(interfaceIterator.getInterfaces().length > 0){
                                try{
                                    interfaceIterator.getInterfaces()[0].getDeclaredMethod(aMethod.
                                                    getAnnotation(OOPInnerMethodCall.class).methodName(),
                                            aMethod.getAnnotation(OOPInnerMethodCall.class).argTypes());
                                    highestInterface = interfaceIterator.getInterfaces()[0];
                                }catch(NoSuchMethodException e){
                                    // continue
                                }
                                interfaceIterator = interfaceIterator.getInterfaces()[0];
                            }
                            throw new OOPInherentAmbiguity(interfaceClass, highestInterface,aMethod);
                        }
                    }
                }
            }

        }



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


    private void getAllInterfacesInTheGraph(Class<?> interfaceClass, List<Class<?>> anInterfacesList,
                                                                        Set<Class<?>> duplicateInterfaces) {
        if (interfaceClass == null) {
            return ;
        }
        if(anInterfacesList.contains(interfaceClass)){
            duplicateInterfaces.add(interfaceClass);
            return ;
        }
        anInterfacesList.add(interfaceClass);
        if ((interfaceClass.getInterfaces().length == 0)) {
            return;
        }
        for(Class<?> anInterface : interfaceClass.getInterfaces()){
            getAllInterfacesInTheGraph(anInterface, anInterfacesList, duplicateInterfaces);
        }
    }

    private boolean checkIfInterface1IsInInterface2Hierarchy(Class<?> I1, Class<?> I2){
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        Set<Class<?>> duplicateSet = new HashSet<Class<?>>();
        getAllInterfacesInTheGraph(I2, interfacesList, duplicateSet);
        return interfacesList.contains(I1);
    }




}

