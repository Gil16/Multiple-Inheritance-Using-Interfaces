package OOP.Provided;

import OOP.Solution.OOPMultipleControl;

import javax.tools.JavaCompiler;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;

public class OOPMultipleClassGenerator {

    private static final String FS = System.getProperty("file.separator");

    private static final String className = "OOPMultiple";
    private static final String packageName = "OOP.Solution";
    private static final String DispatchClassName = "OOPMultipleControl";
    private static final String sourcePath = System.getProperty("user.dir") + FS + "OOP" + FS + "Solution" + FS;
    private static final String classPath = System.getProperty("user.dir");
    private OOPMultipleControl controller;

    private static String getOutputClassPath() {
        Class<OOPMultipleClassGenerator> thisClass = OOPMultipleClassGenerator.class;
        String outputClassPath = thisClass.getResource(thisClass.getSimpleName() + ".class").toString();
        String str = thisClass.getName().replaceAll("\\.", FS);
        int index = outputClassPath.indexOf(str);
        outputClassPath = outputClassPath.substring(0, index);
        return outputClassPath;
    }

    public Object generateMultipleClass(Class<?> interfaceClass)
            throws OOPMultipleException {

        File sourceFile = new File(sourcePath + className + ".java");
        controller = new OOPMultipleControl(interfaceClass, sourceFile);

        // A call to your Multiple Inheritance Tree validation.
        controller.validateInheritanceGraph();

        Object obj = null;

        String className = OOPMultipleClassGenerator.className;
        String packageName = OOPMultipleClassGenerator.packageName;
        try {
            //  The creation of the source file
            FileWriter writer = new FileWriter(sourceFile);
            writer.write(getClassString(interfaceClass));
            writer.close();

            //  A call to Java's compiler, for the compilation of OOPMultiple
            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
            fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singletonList(new File(classPath)));
            StringWriter out = new StringWriter();
            JavaCompiler.CompilationTask task = compiler.getTask(out, fileManager, null, null, null,
                    fileManager.getJavaFileObjectsFromFiles(Collections.singletonList(sourceFile)));

            task.call();

            fileManager.close();
            File objectFile = new File(classPath);
            URL url = objectFile.toURI().toURL();
            URL[] urls = new URL[]{url};

            // Loading the new class to the memory
            ClassLoader loader = new URLClassLoader(urls);
            Class<?> targetClass = loader.loadClass(packageName + "." + className);
            String pathname = targetClass.getResource(targetClass.getSimpleName() + ".class").toString();
            File classFile = new File(pathname);
            Thread.sleep(50);
            classFile.delete();

             // Using reflection to create an object of OOPMultiple
            obj = targetClass.getConstructors()[0].newInstance(controller);


        } catch (Exception ex) {
            ex.printStackTrace();
            removeSourceFile();
            System.exit(1);
        } finally {
            removeSourceFile();
        }

        return obj;
    }

    public void removeSourceFile() {
        controller.removeSourceFile();
    }

    private static String getClassString(Class<?> interfaceClass) {
        return packageStatementString() + importStatementString() + classHeaderString(interfaceClass) + "\n" +
                dispatcherFieldString() + "\n\n" + constructorFieldString() +
                interfaceMethodsString(interfaceClass) + "\n }";
    }

    private static String importStatementString() {
        return "import OOP.Provided.OOPMultipleException;\n";
    }

    private static String packageStatementString() {
        return "package " + packageName + ";\n";
    }

    private static String classHeaderString(Class<?> interfaceClass) {
        return "public class " + className + " implements " + interfaceClass.getName() + " { \n";
    }

    private static String dispatcherFieldString() {
        return "private " + DispatchClassName + " dispatcher;\n";
    }

    private static String constructorFieldString() {
        return "public " + className + "(" + DispatchClassName + " dispatcher){\n"
                + "this.dispatcher = dispatcher;\n" + "}\n";
    }

    private static String interfaceMethodsString(Class<?> interfaceClass) {

        StringBuilder interfaceMethods = new StringBuilder();
        Set<Method> writtenMethodsSet = new HashSet<>();
        for (Method method : interfaceClass.getMethods()) {

            interfaceMethods.append("\n \n");
            boolean shouldContinue = false;
            for (Method m : writtenMethodsSet) {
                if (method.getName().equals(m.getName()) &&
                        Arrays.equals(method.getParameterTypes(), m.getParameterTypes()) &&
                        method.getReturnType().equals(m.getReturnType())) {
                    shouldContinue = true;
                    break;
                }
            }

            if (shouldContinue) {
                continue;
            }

            writtenMethodsSet.add(method);

            interfaceMethods.append("public ").append(method.getReturnType().getName());
            interfaceMethods.append(" ").append(method.getName()).append(" ").append("(");

            boolean parametersAdded = false;
            int counter = 1;
            List<String> params = new ArrayList<>();
            for (Class<?> param : method.getParameterTypes()) {
                parametersAdded = true;
                String paramName = "param" + counter++;
                params.add(paramName);
                interfaceMethods.append(param.getName()).append(" ").append(paramName).append(" ,");
            }

            interfaceMethods = new StringBuilder(parametersAdded ? interfaceMethods.substring(0, interfaceMethods.length() - 2)
                    : interfaceMethods.toString());
            interfaceMethods.append(") ").append(throwsExceptionString()).append(" {");
            StringBuilder paramsArray = new StringBuilder();

            if (params.size() > 0) {
                paramsArray = new StringBuilder("new Object[]{");
                for (String param : params) {
                    paramsArray.append(param).append(",");
                }
                paramsArray = new StringBuilder(paramsArray.substring(0, paramsArray.length() - 1));
                paramsArray.append("}");
            }

            if (params.size() == 0) {
                paramsArray = new StringBuilder("null");
            }
            interfaceMethods.append("\n");
            StringBuilder dispatchCall = new StringBuilder().append("dispatcher.invoke(\"")
                    .append(method.getName())
                    .append("\", ")
                    .append(paramsArray)
                    .append(");\n");
            if (method.getReturnType().equals(Void.TYPE)) {
                interfaceMethods.append(dispatchCall);
            } else {
                interfaceMethods.append("return " + "(")
                        .append(method.getReturnType().getName())
                        .append(") ")
                        .append(dispatchCall);
            }
            interfaceMethods.append("}");
        }

        return interfaceMethods.toString();
    }

    private static String throwsExceptionString() {
        return "throws OOPMultipleException";
    }

    @Override
    protected void finalize() throws Throwable {
        removeSourceFile();
        super.finalize();
    }
}