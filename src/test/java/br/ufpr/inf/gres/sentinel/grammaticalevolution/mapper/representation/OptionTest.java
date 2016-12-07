package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class OptionTest {

    public OptionTest() {
    }

    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));

        Option option2 = new Option();
        option2.addRule(new Rule("regra1"));
        option2.addRule(new Rule("regra2"));

        assertEquals(option.hashCode(), option2.hashCode());
    }

    @Test
    public void testHashCode2() {
        System.out.println("hashCode2");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));

        Option option2 = new Option();
        option2.addRule(new Rule("regra1"));

        assertNotEquals(option.hashCode(), option2.hashCode());
    }

    @Test
    public void testHashCode3() {
        System.out.println("hashCode3");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));

        assertEquals(option.hashCode(), option.hashCode());
    }

    @Test
    public void testEquals() {
        System.out.println("equals");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));

        Option option2 = new Option();
        option2.addRule(new Rule("regra1"));
        option2.addRule(new Rule("regra2"));

        assertEquals(option, option2);
    }

    @Test
    public void testEquals2() {
        System.out.println("equals2");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));

        Option option2 = new Option();
        option2.addRule(new Rule("regra1"));
        option2.addRule(new Rule("regra3"));

        assertNotEquals(option, option2);
    }

    @Test
    public void testEquals3() {
        System.out.println("equals3");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));

        assertEquals(option, option);
    }

    @Test
    public void testToString() {
        System.out.println("toString");
        Option option = new Option();
        option.addRule(new Rule("regra1"));
        option.addRule(new Rule("regra2"));
        option.addRule(new Rule("regra3"));

        String expected = "\"regra1\" \"regra2\" \"regra3\"";
        assertEquals(expected, option.toString());
    }

}
