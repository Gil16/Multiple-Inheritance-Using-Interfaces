package OOP.Provided;

import java.lang.reflect.Method;
import java.util.Collection;

public class OOPInaccessibleMethod extends OOPMultipleException {
    public final Collection<ForbiddenAccess> inaccessibleMethods;

    /***
     * Builds an exception with the invocation pairs.
     * The pairs contain all the methods that were inaccessible from the source of the call to them.
     * i.e. For <b>each</b> call to any method g (in interface I) from a function g, if g is not accessible from g,
     * then the pair (I, g) should be in the collection
     *
     * @param inaccessibleMethods A collection of ForbiddenAccess's.
     */
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


    /**
     * A simple class for describing a forbidden access to a method.
     */
    public static class ForbiddenAccess {
        private final Class<?> accessedClass;
        private final Method accessedMethod;
        private final Class<?> accessingClass;

        /**
         * Builds an element describing the forbidden method access.
         *
         * @param accessingClass - the class the defined a call to the forbidden method.
         * @param accessedMethod - the method being accessed.
         * @param accessedClass  - the class object of the interface in which the method was <b>defined</b> in.
         */
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
