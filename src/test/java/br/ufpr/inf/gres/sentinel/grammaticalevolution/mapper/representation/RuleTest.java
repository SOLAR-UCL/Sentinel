package br.ufpr.inf.gres.sentinel.grammaticalevolution.mapper.representation;

import com.google.common.collect.Lists;
import java.util.Iterator;
import org.apache.commons.collections4.list.SetUniqueList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author Giovani Guizzo
 */
public class RuleTest {

    public RuleTest() {
    }

    @Test
    public void testGetOption_int() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Rule rule3 = new Rule("rule3");
        Option option1 = new Option(Lists.newArrayList(rule2, rule3));
        rule.addOption(option1);
        Option option2 = new Option(Lists.newArrayList(rule3, rule3));
        rule.addOption(option2);
        assertEquals(option1, rule.getOption(0));
        assertEquals(option2, rule.getOption(1));
        assertEquals(option1, rule.getOption(2));
        assertEquals(option2, rule.getOption(3));
        assertEquals(option1, rule.getOption(4));
    }

    @Test
    public void testGetOption_int2() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Rule rule3 = new Rule("rule3");
        Option option1 = new Option(Lists.newArrayList(rule2, rule3));
        rule.addOption(option1);
        assertEquals(option1, rule.getOption(0));
        assertEquals(option1, rule.getOption(1));
    }

    @Test
    public void testGetOption_Iterator() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Rule rule3 = new Rule("rule3");
        Option option1 = new Option(Lists.newArrayList(rule2, rule3));
        rule.addOption(option1);
        Option option2 = new Option(Lists.newArrayList(rule3, rule3));
        rule.addOption(option2);

        Iterator<Integer> iterator = Lists.newArrayList(0, 1, 2, 3, 4).iterator();
        assertEquals(option1, rule.getOption(iterator));
        assertEquals(option2, rule.getOption(iterator));
        assertEquals(option1, rule.getOption(iterator));
        assertEquals(option2, rule.getOption(iterator));
        assertEquals(option1, rule.getOption(iterator));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testGetOption_Iterator2() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        assertEquals(option1, rule.getOption(iterator));
        assertEquals(option1, rule.getOption(iterator));
        assertTrue(iterator.hasNext());
    }

    @Test
    public void testGetOption_Iterator3() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Rule rule3 = new Rule("rule3");
        Option option1 = new Option(Lists.newArrayList(rule2, rule3));
        rule.addOption(option1);
        Option option2 = new Option(Lists.newArrayList(rule3, rule3));
        rule.addOption(option2);

        Iterator<Integer> iterator = Lists.newArrayList(0).iterator();
        assertEquals(option1, rule.getOption(iterator));
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testHashCode() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        assertEquals(rule.hashCode(), rule.hashCode());
    }

    @Test
    public void testHashCode2() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule1");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        assertEquals(rule.hashCode(), rule2.hashCode());
    }

    @Test
    public void testHashCode3() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");

        assertNotEquals(rule.hashCode(), rule2.hashCode());
    }

    @Test
    public void testEquals() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        assertEquals(rule, rule);
    }

    @Test
    public void testEquals2() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule1");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        assertEquals(rule, rule2);
    }

    @Test
    public void testEquals3() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        assertNotEquals(rule, rule2);
    }

    @Test
    public void testEquals4() {
        Rule rule = new Rule("rule1");

        Rule rule2 = null;

        assertNotEquals(rule, rule2);
    }

    @Test
    public void testEquals5() {
        Rule rule = new Rule("rule1");

        Object rule2 = new Object();

        assertNotEquals(rule, rule2);
    }

    @Test
    public void testToString() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Option option1 = new Option(Lists.newArrayList(rule2, rule2));
        rule.addOption(option1);

        assertEquals("<rule1>", rule.toString());
        assertEquals("\"rule2\"", rule2.toString());
    }

    @Test
    public void testToCompleteString() {
        Rule rule = new Rule("rule1");
        Rule rule2 = new Rule("rule2");
        Option option1 = new Option(Lists.newArrayList(rule, rule2));
        rule.addOption(option1);

        assertEquals("<rule1> ::= <rule1> \"rule2\"", rule.toCompleteString());
        assertEquals("\"rule2\"", rule2.toCompleteString());
    }

    @Test
    public void testGetAndSetName() {
        Rule rule = new Rule("rule1");
        rule.setName("RuleTest");
        assertEquals("RuleTest", rule.getName());
    }

    @Test
    public void testGetAndSetOptions() {
        Rule rule = new Rule("rule1");
        SetUniqueList<Option> options = SetUniqueList.setUniqueList(Lists.newArrayList(new Option()));
        rule.setOptions(options);
        assertEquals(options, rule.getOptions());
    }

    @Test
    public void testRemoveOption() {
        Rule rule = new Rule("rule1");
        SetUniqueList<Option> options = SetUniqueList.setUniqueList(Lists.newArrayList(new Option()));
        rule.setOptions(options);
        rule.removeOption(new Option());
        assertEquals(0, rule.getOptions().size());
    }

    @Test
    public void testClearOptions() {
        Rule rule = new Rule("rule1");
        SetUniqueList<Option> options = SetUniqueList.setUniqueList(Lists.newArrayList(new Option()));
        rule.setOptions(options);
        rule.clearOptions();
        assertEquals(0, rule.getOptions().size());
    }

}
