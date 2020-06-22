package OOP.Provided;

import javafx.util.Pair;

import java.lang.reflect.Method;
import java.util.Collection;

public class OOPCoincidentalAmbiguity extends OOPMultipleException {
    public final Collection<Pair<Class<?>, Method>> candidates;


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
