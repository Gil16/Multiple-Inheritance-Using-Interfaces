package OOP.Provided;

import java.lang.reflect.Method;
import java.util.Collection;

public class OOPInaccessibleMethod extends OOPMultipleException {
    public final Collection<ForbiddenAccess> inaccessibleMethods;


    public OOPInaccessibleMethod(Collection<ForbiddenAccess> inaccessibleMethods) {
        this.inaccessibleMethods = inaccessibleMethods;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Call contained inaccessible methods : \n");
        for (ForbiddenAccess forbiddenAccess : inaccessibleMethods) {
            message.append(forbiddenAccess.toString());
        }
        return message.toString();
    }


    //  A simple class for describing a forbidden access to a method.
    public static class ForbiddenAccess {
        private final Class<?> accessedClass;
        private final Method accessedMethod;
        private final Class<?> accessingClass;

        public ForbiddenAccess(Class<?> accessingClass, Class<?> accessedClass, Method accessedMethod) {
            this.accessedClass = accessedClass;
            this.accessedMethod = accessedMethod;
            this.accessingClass = accessingClass;
        }

        @Override
        public String toString() {
            return (accessingClass != null ? (accessingClass.getSimpleName() + " -> ") : "") +
                    accessedClass.getSimpleName() +
                    " : " +
                    accessedMethod.getName() +
                    "\n";
        }

        @Override
        public boolean equals(Object object) {
            return ForbiddenAccess.class.isAssignableFrom(object.getClass()) && toString().equals(object.toString());
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }
    }

}
