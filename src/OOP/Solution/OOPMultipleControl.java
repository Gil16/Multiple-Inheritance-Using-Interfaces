package OOP.Solution;

import OOP.Provided.*;

import java.io.File;
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
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        interfacesList = getAllInterfacesInTheGraph(interfaceClass);


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


    public List<Class<?>> getAllInterfacesInTheGraph(Class<?> interfaceClass){
        if(interfaceClass == null){
            return null;
        }
        Class<?>[] allInterfaces = interfaceClass.getInterfaces();
        if(allInterfaces == null){
            return null;
        }
        List<Class<?>> interfacesList = new LinkedList<Class<?>>();
        for(Class<?> anInterface : allInterfaces){
            interfacesList.add(anInterface);
            getAllInterfacesInTheGraph(anInterface);
        }
        return interfacesList;
    }
}

