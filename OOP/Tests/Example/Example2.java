package OOP.Tests.Example;

import OOP.Provided.OOPMultipleClassGenerator;
import OOP.Provided.OOPMultipleException;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class Example2 {

    private OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();

    @Rule
    public Timeout timeout = Timeout.millis(1500);

    static I3 obj = null;

    @Test
    public void exampleTest() {
        try {
            obj = (I3) generator.generateMultipleClass(I3.class);
            obj.f();
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            generator.removeSourceFile();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        generator.removeSourceFile();
    }
}