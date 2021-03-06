package OOP.Solution;

import OOP.Provided.*;
import javafx.util.Pair;

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

    public void validateInheritanceGraph() throws OOPMultipleException {
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        Set<Class<?>> duplicateSet = new HashSet<Class<?>>();
        getAllInterfacesInTheGraph(interfaceClass, interfacesList, duplicateSet);

        // Checking if all the interfaces has OOPMultipleInterface annotation
        for (Class<?> anInterface : interfacesList){
            if(!anInterface.isAnnotationPresent(OOPMultipleInterface.class)) {
                throw new OOPBadClass(anInterface);
            }
        }

        // Checking if all the lower interface methods have annotation
        for(Method aMethod : interfaceClass.getDeclaredMethods()){
            if (!(aMethod.isAnnotationPresent(OOPMultipleMethod.class))) {
                throw new OOPBadClass(aMethod);
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
                    ForbiddenAccess badAccess = new ForbiddenAccess(Caller, Callee, calledMethod);
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
            for(Class<?> aDuplicateInterface : duplicateSet){
                    if(aDuplicateInterface.getDeclaredMethods().length > 0){
                        throw new OOPInherentAmbiguity(interfaceClass, aDuplicateInterface,
                                                            aDuplicateInterface.getDeclaredMethods()[0]);
                }
            }
        }
    }


    public Object invoke(String methodName, Object[] args) throws OOPMultipleException {

        List<Pair<Class<?>, Method>> compatibleMethods = new LinkedList<Pair<Class<?>, Method>>();
        List<ForbiddenAccess> badMethods = new LinkedList<ForbiddenAccess>();

        for(Method aMethod : interfaceClass.getMethods()){
            if(aMethod.getName().equals(methodName)){
                if(aMethod.getAnnotation(OOPMultipleMethod.class).modifier() != OOPMethodModifier.PUBLIC){
                    ForbiddenAccess badAccess = new ForbiddenAccess(null, aMethod.getDeclaringClass(), aMethod);
                    badMethods.add(badAccess);
                } else{
                    Pair<Class<?>, Method> aCompatibleMethod = new Pair<>(aMethod.getDeclaringClass(), aMethod);
                    if(args == null){
                        compatibleMethods.add(aCompatibleMethod);
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
                            compatibleMethods.add(aCompatibleMethod);
                        }
                    }
                }
            }
        }

        if((badMethods.size() > 0) && (compatibleMethods.size() == 0)){
            throw new OOPInaccessibleMethod(badMethods);
        }
        // coincidental ambiguity
        if(args != null) {
            List<Class<?>> mightBeCoincidental = new LinkedList<Class<?>>();
            Class<?>[] minimumDistances = new Class<?>[args.length];
            // Here we find the minimum distances between the compatible method and the given args
            boolean firstAssignment = true;
            for (int i = 0; i < args.length; i++) {
                for (Pair<Class<?>, Method> aPair : compatibleMethods) {
                    Method aMethod = aPair.getValue();
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
            for (Pair<Class<?>, Method> aPair : compatibleMethods) {
                Class<?> anInterface = aPair.getKey();
                Method bMethod = aPair.getValue();
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
                List<Pair<Class<?>, Method>> tempAmbiguities= new LinkedList<Pair<Class<?>, Method>>();
                tempAmbiguities.addAll(mightBeAmbiguity);
                /* iterating on the mightbeAmbiguities and checks if each method*/
                for(Pair<Class<?>, Method> currentPair : tempAmbiguities){
                    Method currentMethod = currentPair.getValue();
                    boolean isNotAmbiguis = false;
                    for(Pair<Class<?>, Method> pairToCheck : mightBeAmbiguity){
                        Method methodfToCheck = pairToCheck.getValue();
                        if(isM1MoreCompatibleThanM2(methodfToCheck,currentMethod)) {
                            isNotAmbiguis = true;
                        }
                    }
                    if(isNotAmbiguis) {
                        mightBeAmbiguity.remove(currentPair);
                    }
                }
                throw new OOPCoincidentalAmbiguity(mightBeAmbiguity);
            }
        }else{
            if(compatibleMethods.size() == 1){
                try {
                    List<Class<?>> tempList = compatibleMethods.stream().map(aPair -> aPair.getKey()).collect(Collectors.toList());
                    Object aClass = null;
                    try {
                        aClass = Class.forName(changeInterfaceNameToClassName(tempList.get(0).getName())).newInstance();
                    }catch(Exception e){

                    }
                    if (compatibleMethods.get(0).getValue().getReturnType() == void.class) {
                        compatibleMethods.get(0).getValue().invoke(aClass, args);
                        return null;
                    } else {
                        return compatibleMethods.get(0).getValue().invoke(aClass, args);
                    }
                } catch (Exception e) {

                }
            }else{
                List<Pair<Class<?>, Method>> ambiguities = new LinkedList<Pair<Class<?>, Method>>();
                for (Pair<Class<?>, Method> aPair : compatibleMethods) {
                    Class<?> anInterface = aPair.getKey();
                    Method aMethod = aPair.getValue();
                    Pair<Class<?>, Method> bPair = new Pair<Class<?>, Method>(anInterface, aMethod);
                    ambiguities.add(bPair);
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

    /* m1 is more compatible than m2 if each argument i in method m1 id lower in the inheritance tree
    than each argument i in method m2 */
    private boolean isM1MoreCompatibleThanM2 (Method m1, Method m2) {
        boolean flag = true;
        for (int i = 0 ; i < m1.getParameterCount() ; i++)
        {
            Class<?> m1CurrentArgType = m1.getParameterTypes()[i];
            Class<?> m2CurrentArgType = m2.getParameterTypes()[i];
            if(!(m1CurrentArgType.equals(m2CurrentArgType)) && !(m2CurrentArgType.isAssignableFrom(m1CurrentArgType))) {
                flag = false;
            }
        }
        return flag;
    }
}

