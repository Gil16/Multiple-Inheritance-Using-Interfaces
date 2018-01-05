package OOP.Solution;

import OOP.Provided.*;
import javafx.util.Pair;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
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

        // Checking if there are no inheritance ambiguities in the graph
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
            List<Method> publicMethodList = new LinkedList<Method>();
            for(Class<?> aDuplicateInterface : duplicateSet){
                for(Method aPublicMethod : aDuplicateInterface.getDeclaredMethods()){
                    if(aPublicMethod.getAnnotation(OOPMultipleMethod.class).modifier() == OOPMethodModifier.PUBLIC){
                        throw new OOPInherentAmbiguity(interfaceClass, aDuplicateInterface, aPublicMethod);
                    }
                }
            }
        }
    }



    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {
        // Getting all the interfaces in the graph
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        Set<Class<?>> duplicateSet = new HashSet<Class<?>>();   // no need, just for the helping

        Map<Class<?>, Method> compatibleMethods = new HashMap<Class<?>, Method>();
        List<ForbiddenAccess> badMethods = new LinkedList<ForbiddenAccess>();

        getAllInterfacesInTheGraph(interfaceClass, interfacesList, duplicateSet);
        for(Class<?> anInterface : interfacesList){
            for(Method aMethod : anInterface.getDeclaredMethods()){
                if(aMethod.getName().equals(methodName)){
                    if(aMethod.getAnnotation(OOPMultipleMethod.class).modifier() != OOPMethodModifier.PUBLIC){
                        ForbiddenAccess badAccess = new ForbiddenAccess(this.getClass(), anInterface, aMethod); // not sure, need to check
                        badMethods.add(badAccess);
                    } else{
                        if(args == null){
                            compatibleMethods.put(anInterface, aMethod);
                            break;
                        }
                        if(args.length == aMethod.getParameterCount()) {
                            boolean flag = true;
                            for(int i=0 ; i < aMethod.getParameterCount() ; i++){
                                if(!(aMethod.getParameterTypes()[i].isAssignableFrom(args[i].getClass()))){
                                    flag = false;
                                    break;
                                }
                            }
                            if(flag) {
                                compatibleMethods.put(anInterface, aMethod);
                            }
                        }
                    }
                }
            }
        }
        if(badMethods.size() > 0){
            throw new OOPInaccessibleMethod(badMethods);
        }
        // coincidental ambiguity
        if(args != null) {
            List<Class<?>> mightBeCoincidental = new LinkedList<Class<?>>();
            Class<?>[] minimumDistances = new Class<?>[args.length];
            // Here we find the minimum distances between the compatible method and the given args
            boolean firstAssignment = true;
            for (int i = 0; i < args.length; i++) {
                for (Method aMethod : compatibleMethods.values()) {
                    if(firstAssignment){
                        minimumDistances[i] = aMethod.getParameterTypes()[i];
                        firstAssignment = false;
                    }else if(!(aMethod.getParameterTypes()[i].isAssignableFrom(minimumDistances[i]))){
                        minimumDistances[i] = aMethod.getParameterTypes()[i];
                    }
                }
                firstAssignment = true;
            }
            List<Pair<Class<?>, Method>> mightBeAmbiguity = new LinkedList<Pair<Class<?>, Method>>();
            List<Pair<Class<?>, Method>> mostCompatible = new LinkedList<Pair<Class<?>, Method>>();
            for (Class<?> anInterface : compatibleMethods.keySet()) {
                Method bMethod = compatibleMethods.get(anInterface);
                boolean might = false;
                boolean most = true;
                for (int i = 0; i < args.length; i++) {
                    if (bMethod.getParameterTypes()[i].equals(minimumDistances[i])) {
                        might = true;
                    } else {
                        most = false;
                    }
                }
                if (might) {
                    mightBeAmbiguity.add(new Pair<Class<?>, Method>(anInterface, bMethod));
                }
                if (most) {
                    mostCompatible.add(new Pair<Class<?>, Method>(anInterface, bMethod));
                }
            }
            if (mostCompatible.size() > 1) {
                throw new OOPCoincidentalAmbiguity(mostCompatible);
            } else if (mostCompatible.size() == 1) {
                try {
                    Object aClass = null;
                    try {
                        aClass = Class.forName(changeInterfaceNameToClassName(mostCompatible.get(0).getKey().getName())).newInstance();
                    }catch(Exception e){

                    }
                    if (mostCompatible.get(0).getValue().getReturnType() == void.class) {
                        mostCompatible.get(0).getValue().invoke(aClass, args);
                        return null;
                    } else {
                        return mostCompatible.get(0).getValue().invoke(aClass, args);
                    }
                } catch (Exception e) {
                    // tralala
                }
            } else {    // mostCompatible size is 0, checking for the rest. (mightBeAmbiguity)


                ////////////////////
                throw new OOPCoincidentalAmbiguity(mightBeAmbiguity);   // ?????
            }
        }else{
            if(compatibleMethods.size() == 1){
                try {
                    List<Class<?>> tempList = compatibleMethods.keySet().stream().collect(Collectors.toList());
                    Object aClass = null;
                    try {
                        aClass = Class.forName(changeInterfaceNameToClassName(tempList.get(0).getName())).newInstance();
                    }catch(Exception e){

                    }
                    if (compatibleMethods.get(tempList.get(0)).getReturnType() == void.class) {
                        compatibleMethods.get(tempList.get(0)).invoke(aClass, args);
                        return null;
                    } else {
                        return compatibleMethods.get(tempList.get(0)).invoke(aClass, args);
                    }
                } catch (Exception e) {

                }
            }else{
                List<Pair<Class<?>, Method>> ambiguities = new LinkedList<Pair<Class<?>, Method>>();
                for (Class<?> anInterface : compatibleMethods.keySet()) {
                    Method aMethod = compatibleMethods.get(anInterface);
                    Pair<Class<?>, Method> aPair = new Pair<Class<?>, Method>(anInterface, aMethod);
                    ambiguities.add(aPair);
                }
                throw new OOPCoincidentalAmbiguity(ambiguities);
            }
        }
        return null;      // will never get here
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
        if(!(interfaceClass.equals(this.interfaceClass))) {
            anInterfacesList.add(interfaceClass);
        }
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


    private String changeInterfaceNameToClassName(String interfaceName) {
        int indexOfI = interfaceName.lastIndexOf('I');
        return interfaceName.substring(0, indexOfI) + "C" + interfaceName.substring(indexOfI + 1);
    }

}

