package OOP.Tests.Winter20172018;

import OOP.Provided.*;
import javafx.util.Pair;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class TestInnerCalls
{
    private static OOPMultipleClassGenerator generator = new OOPMultipleClassGenerator();

    @Test
    public void testInterfaceToPrivate() {
        try {
            generator.generateMultipleClass(IChildToPrivateFinal.class);
            Assert.fail();
        } catch (OOPInaccessibleMethod e) {
            try {
                inaccessibleMethodsAreEqual(new OOPInaccessibleMethod(new HashSet<>(Arrays.asList(
                        new OOPInaccessibleMethod.ForbiddenAccess(IChildToPrivate.class, I1.class, I1.class.getMethod("privateMethod1")),
                        new OOPInaccessibleMethod.ForbiddenAccess(IChildToPrivate.class, I1.class, I1.class.getMethod("privateMethod2", Integer.class))))), e);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        }
        finally {
            generator.removeSourceFile();
        }

        try {
            generator.generateMultipleClass(IChildToPrivateMultipleFinal.class);
            Assert.fail();
        } catch (OOPInaccessibleMethod e) {
            try {
                inaccessibleMethodsAreEqual(
                        new OOPInaccessibleMethod(
                                new HashSet<>(
                                        Arrays.asList(
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        IChildToPrivateMultiple.class,
                                                        I1.class,
                                                        I1.class.getMethod("privateMethod1")
                                                ),
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        IChildToPrivateMultiple.class,
                                                        I1.class,
                                                        I1.class.getMethod("privateMethod2", Integer.class)
                                                ),
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        IChildToPrivateMultiple.class,
                                                        I2.class,
                                                        I2.class.getMethod("privateMethod4")
                                                ),
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        IChildToPrivateMultiple.class,
                                                        I2.class,
                                                        I2.class.getMethod("privateMethod2", String.class)
                                                )
                                        )
                                )
                        ),
                        e
                );
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        }
        finally {
            generator.removeSourceFile();
        }

    }

    @Test
    public void testInterfaceToProtected() {
        try {
            generator.generateMultipleClass(IChildToProtectedFinal.class);
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        }
        finally {
            generator.removeSourceFile();
        }
    }

    @Test
    public void testInterfaceToPublic() {
        try {
            generator.generateMultipleClass(IChildToPublicFinal.class);
            generator.removeSourceFile();
            generator.generateMultipleClass(IChildToDefaultFinal.class);
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            generator.removeSourceFile();
        }
    }

    @Test
    public void testUnitTestToPrivateProtectedPublic() {
        IChildToPublicFinal iNewChildToPublic = null;//TODO: i can not use I1 - why?

        try {
            iNewChildToPublic = (IChildToPublicFinal) generator.generateMultipleClass(IChildToPublicFinal.class);
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        }
        finally {
            generator.removeSourceFile();
        }

        try {
            iNewChildToPublic.privateMethod1();
            Assert.fail();
        } catch (OOPInaccessibleMethod e) {
            try {
                inaccessibleMethodsAreEqual(new OOPInaccessibleMethod(new HashSet<>(Collections.singletonList(
                        new OOPInaccessibleMethod.ForbiddenAccess(
                                null, I1.class, I1.class.getMethod("privateMethod1"))))), e);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();

            Assert.fail();
        }

        try {
            iNewChildToPublic.privateMethod2(0xDEADBEEF);
            Assert.fail();
        } catch (OOPInaccessibleMethod e) {
            try {
                inaccessibleMethodsAreEqual(
                        new OOPInaccessibleMethod(
                                new HashSet<>(
                                        Collections.singletonList(
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        null,
                                                        I1.class,
                                                        I1.class.getMethod("privateMethod2", Integer.class)
                                                )
                                        )
                                )
                        ),
                        e
                );
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();

            Assert.fail();
        }

        try {
            iNewChildToPublic.protectedMethod1();
            Assert.fail();
        } catch (OOPInaccessibleMethod e) {
            try {
                inaccessibleMethodsAreEqual(
                        new OOPInaccessibleMethod(
                                new HashSet<>(
                                        Collections.singletonList(
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        null,
                                                        I1.class,
                                                        I1.class.getMethod("protectedMethod1")
                                                )
                                        )
                                )
                        ),
                        e
                );
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();

            Assert.fail();
        }

        try {
            iNewChildToPublic.protectedMethod2(0xDEADBEEF);
            Assert.fail();
        } catch (OOPInaccessibleMethod e) {
            try {
                inaccessibleMethodsAreEqual(
                        new OOPInaccessibleMethod(
                                new HashSet<>(
                                        Collections.singletonList(
                                                new OOPInaccessibleMethod.ForbiddenAccess(
                                                        null,
                                                        I1.class,
                                                        I1.class.getMethod("protectedMethod2", Integer.class)
                                                )
                                        )
                                )
                        ),
                        e
                );
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();

            Assert.fail();
        }
    }

    @Test
    public void testPrivateMoreSuitableThanPublic()
    {
        C string = new C();

        I3Child i3Child = (I3Child) i3;

        try {
            Assert.assertEquals("Public", i3.function(string));
            Assert.assertEquals("Public", i3Child.functionA(string));
            Assert.assertEquals("Protected", i3Child.functionB(string));
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void testSameDistanceArgumentsOnSameLocation() {
        A c = new C();
        try {
            i3.anotherFunction(c);
       //     Assert.fail();
        }
        catch (OOPCoincidentalAmbiguity e) {
            try {
                coincidentalAmbiguityAreEqual(
                        new OOPCoincidentalAmbiguity(Arrays.asList(
                                new Pair<>(I3.class, I3.class.getMethod("anotherFunction", A.class)),
                                new Pair<>(I3.class, I3.class.getMethod("anotherFunction", D.class))
                        )),
                        e);
            } catch (NoSuchMethodException e1) {
                e1.printStackTrace();
            }
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    private void inaccessibleMethodsAreEqual(OOPInaccessibleMethod expectedException, OOPInaccessibleMethod receivedException) {
        //TODO: think about something better (all permutations)

        Assert.assertEquals(new HashSet<>(expectedException.inaccessibleMethods), new HashSet<>(receivedException.inaccessibleMethods));
    }

    private void coincidentalAmbiguityAreEqual(OOPCoincidentalAmbiguity expectedException, OOPCoincidentalAmbiguity receivedException) {
        //TODO: think about something better (all permutations)

        Assert.assertEquals(new HashSet<>(expectedException.candidates), new HashSet<>(receivedException.candidates));
    }

    private static I3 i3 = null;

    @BeforeClass
    public static void createI3()
    {
        try {
            i3 = (I3) generator.generateMultipleClass(I3ChildFinal.class);
        } catch (OOPMultipleException e) {
            e.printStackTrace();
            Assert.fail();
        } finally {
            generator.removeSourceFile();
        }
    }

};

