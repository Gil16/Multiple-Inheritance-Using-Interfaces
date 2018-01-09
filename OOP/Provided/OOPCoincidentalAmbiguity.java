package OOP.Provided;

import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.Collection;

public class OOPCoincidentalAmbiguity extends OOPMultipleException {

    public final Collection<Pair<Class<?>, Method>> candidates;

    /***
     * Builds an exception with the invocation candidates.
     * i.e. For a call to a method g, if there are more than one conforming method candidate corresponding to the call,
     * then all those candidates should appear in the collection.
     *
     * @param candidates A collection of pairs of (class, Method).
     *                   method - one candidate of invocation
     *                   class  - the class object of the <b>interface</b> that the method was declared in.
     */
    public OOPCoincidentalAmbiguity(Collection<Pair<Class<?>, Method>> candidates) {
        this.candidates = candidates;
    }

    @Override
    public String getMessage() {
        StringBuilder message = new StringBuilder("Coincidental Ambiguous Method Call. candidates are : \n");
        for (Pair<Class<?>, Method> pair : candidates) {
            message.append(pair.getKey().getName())
                    .append(" : ")
                    .append(pair.getValue().getName())
                    .append("\n");
        }
        return message.toString();
    }
}
