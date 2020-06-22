package OOP.Provided;

import java.lang.reflect.Method;

public class OOPBadClass extends OOPMultipleException {
    private Method corruptedMethod;
    private Class<?> corruptedInterface;


    public OOPBadClass(Method corrupted) {
        this.corruptedMethod = corrupted;
        this.corruptedInterface = null;
    }


    public OOPBadClass(Class<?> corrupted) {
        this.corruptedInterface = corrupted;
        this.corruptedMethod = null;
    }

    @Override
    public String getMessage() {
        if (corruptedInterface == null) {
            Class<?> declaringClass = corruptedMethod.getDeclaringClass();
            return super.getMessage() +
                    declaringClass.getName() +
                    " : " + corruptedMethod.getName() + " is Corrupted!";
        }
        return super.getMessage() +
                corruptedInterface.getName() + " is Corrupted!";
    }
}
