package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import com.google.common.collect.Lists;
import java.util.ArrayList;
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
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        Option option2 = new Option();
        option2.addRule(new Rule("rule1"));
        option2.addRule(new Rule("rule2"));

        assertEquals(option.hashCode(), option2.hashCode());
    }

    @Test
    public void testHashCode2() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        Option option2 = new Option();
        option2.addRule(new Rule("rule1"));

        assertNotEquals(option.hashCode(), option2.hashCode());
    }

    @Test
    public void testHashCode3() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        assertEquals(option.hashCode(), option.hashCode());
    }

    @Test
    public void testEquals() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        Option option2 = new Option();
        option2.addRule(new Rule("rule1"));
        option2.addRule(new Rule("rule2"));

        assertEquals(option, option2);
    }

    @Test
    public void testEquals2() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        Option option2 = new Option();
        option2.addRule(new Rule("rule1"));
        option2.addRule(new Rule("rule3"));

        assertNotEquals(option, option2);
    }

    @Test
    public void testEquals3() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        assertEquals(option, option);
    }

    @Test
    public void testEquals4() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        Option option2 = null;

        assertNotEquals(option, option2);
    }

    @Test
    public void testEquals5() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));

        Object option2 = new Object();

        assertNotEquals(option, option2);
    }

    @Test
    public void testToString() {
        Option option = new Option();
        option.addRule(new Rule("rule1"));
        option.addRule(new Rule("rule2"));
        option.addRule(new Rule("rule3"));

        String expected = "\"rule1\" \"rule2\" \"rule3\"";
        assertEquals(expected, option.toString());
    }

    @Test
    public void testGetAndSetRules() {
        Option option = new Option();
        ArrayList<Rule> rules = Lists.newArrayList(new Rule("rule1"), new Rule("rule2"), new Rule("rule3"));
        option.setRules(rules);
        assertEquals(rules, option.getRules());
    }

    @Test
    public void testRemoveRule() {
        Option option = new Option();
        ArrayList<Rule> rules = Lists.newArrayList(new Rule("rule1"), new Rule("rule2"), new Rule("rule3"));
        option.setRules(rules);
        option.removeRule(new Rule("rule1"));
        assertEquals(2, option.getRules().size());
    }

    @Test
    public void testClearRules() {
        Option option = new Option();
        ArrayList<Rule> rules = Lists.newArrayList(new Rule("rule1"), new Rule("rule2"), new Rule("rule3"));
        option.clearRules();
        assertEquals(0, option.getRules().size());
    }

}
