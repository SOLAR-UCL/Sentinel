package br.ufpr.inf.gres;

/*
 * Copyright 2016 Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * @author Jackson Antonio do Prado Lima <jacksonpradolima at gmail.com>
 */
public class TriTypTest {

//	@Rule
//	public Timeout globalTimeout = new Timeout(1000);

	@Test(expected = IllegalArgumentException.class)
	public void test_1() {
		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test(expected = IllegalArgumentException.class)
	public void test_2() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_3() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_4() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_5() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_6() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_7() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_8() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_9() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_10() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_11() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_12() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_13() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_14() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_15() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_16() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_17() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_18() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_19() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_20() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_21() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_22() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_23() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_24() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_25() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_26() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_27() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_28() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_29() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_30() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_31() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_32() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_33() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_34() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_35() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_36() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = -1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_37() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_38() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_39() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_40() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_41() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_42() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_43() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_44() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_45() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_46() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_47() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_48() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_49() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_50() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_51() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_52() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_53() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_54() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_55() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_56() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_57() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_58() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_59() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_60() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_61() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_62() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_63() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_64() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_65() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_66() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_67() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_68() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_69() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_70() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_71() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_72() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 0;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_73() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_74() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_75() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_76() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_77() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_78() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_79() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_80() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_81() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_82() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_83() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_84() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_85() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_86() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_87() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.EQUILATERO, actual);

	}

	@Test
	public void test_88() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_89() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_90() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_91() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_92() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_93() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_94() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_95() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_96() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_97() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_98() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_99() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_100() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_101() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_102() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_103() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_104() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_105() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_106() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_107() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_108() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 1;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_109() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_110() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_111() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_112() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_113() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_114() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_115() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_116() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_117() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_118() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_119() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_120() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_121() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_122() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_123() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_124() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_125() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_126() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_127() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_128() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_129() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_130() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.EQUILATERO, actual);

	}

	@Test
	public void test_131() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_132() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_133() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_134() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_135() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_136() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_137() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_138() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_139() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_140() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_141() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_142() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_143() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, actual);

	}

	@Test
	public void test_144() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 2;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_145() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_146() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_147() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_148() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_149() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_150() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_151() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_152() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_153() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_154() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_155() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_156() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_157() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_158() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_159() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_160() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_161() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_162() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_163() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_164() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_165() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_166() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_167() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_168() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_169() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_170() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_171() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_172() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_173() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.EQUILATERO, actual);

	}

	@Test
	public void test_174() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_175() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_176() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_177() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_178() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, actual);

	}

	@Test
	public void test_179() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_180() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 3;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_181() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_182() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_183() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_184() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_185() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_186() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = -1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_187() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_188() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_189() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_190() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_191() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_192() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 0;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_193() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_194() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_195() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_196() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_197() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_198() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 1;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_199() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_200() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_201() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_202() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_203() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, actual);

	}

	@Test
	public void test_204() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 2;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_205() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_206() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_207() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_208() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, actual);

	}

	@Test
	public void test_209() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_210() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 3;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_211() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = -1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test(expected = IllegalArgumentException.class)
	public void test_212() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 0;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);

	}

	@Test
	public void test_213() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 1;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_214() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 2;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_215() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 3;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, actual);

	}

	@Test
	public void test_216() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 4;
		instance.setI(arg1);
		int arg2 = 4;
		instance.setJ(arg2);
		int arg3 = 4;
		instance.setK(arg3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.EQUILATERO, actual);
	}

	@Test
	public void test_217() {

		TriTyp instance = new TriTyp();
		instance.setI(4);
		assertEquals(instance.i, 4);
	}

	@Test
	public void test_218() {

		TriTyp instance = new TriTyp();
		instance.setJ(4);
		assertEquals(instance.j, 4);
	}

	@Test
	public void test_219() {

		TriTyp instance = new TriTyp();
		instance.setK(4);
		assertEquals(instance.k, 4);
	}

	@Test
	public void test_220() {

		TriTyp instance = new TriTyp();
		assertEquals(instance.tipo, TriTyp.NO_TRIANGULO);
	}

	@Test
	public void test_221() {

		TriTyp instance = new TriTyp();
		instance.i = -1;
		instance.j = -1;
		instance.k = -1;
		assertEquals(instance.getTipo(), TriTyp.NO_TRIANGULO);
	}

	@Test
	public void test_222() {
		TriTyp instance = new TriTyp();
		assertEquals(instance.NO_TRIANGULO, 0);
	}

	@Test
	public void test_223() {
		TriTyp instance = new TriTyp();
		assertEquals(instance.ESCALENO, 1);
	}

	@Test
	public void test_224() {
		TriTyp instance = new TriTyp();
		assertEquals(instance.ISOSCELES, 2);
	}

	@Test
	public void test_225() {
		TriTyp instance = new TriTyp();
		assertEquals(instance.EQUILATERO, 3);
	}

	@Test
	public void test_226() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(99);
		instance.setK(3);
		assertEquals(instance.getTipo(), TriTyp.NO_TRIANGULO);
	}

	@Test
	public void test_227() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(99);
		instance.setK(1);
		assertEquals(instance.getTipo(), TriTyp.NO_TRIANGULO);
	}

	@Test
	public void test_228() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(99);
		instance.setK(1);
		int type = instance.getTipo();

		assertEquals(instance.i, 1);
	}

	@Test
	public void test_229() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(99);
		instance.setK(1);
		int type = instance.getTipo();

		assertEquals(instance.j, 99);
	}

	@Test
	public void test_230() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(99);
		instance.setK(1);
		int type = instance.getTipo();

		assertEquals(instance.k, 1);
	}

	@Test
	public void test_231() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(3);
		instance.setK(3);
		int actual = instance.getTipo();
		assertEquals(instance.j, 3);

	}

	@Test
	public void test_232() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(3);
		instance.setK(3);
		int actual = instance.getTipo();
		assertEquals(instance.k, 3);
	}

	@Test
	public void test_233() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(3);
		instance.setK(3);
		int actual = instance.getTipo();
		assertEquals(instance.i, 1);
	}

	@Test
	public void test_234() {
		TriTyp instance = new TriTyp();
		instance.i = -1;
		instance.j = -1;
		instance.k = -1;
		int actual = instance.getTipo();
		assertEquals(instance.i, -1);
	}

	@Test
	public void test_235() {
		TriTyp instance = new TriTyp();
		instance.i = -1;
		instance.j = -1;
		instance.k = -1;
		int actual = instance.getTipo();
		assertEquals(instance.j, -1);
	}

	@Test
	public void test_236() {
		TriTyp instance = new TriTyp();
		instance.i = -1;
		instance.j = -1;
		instance.k = -1;
		int actual = instance.getTipo();
		assertEquals(instance.k, -1);
	}

	@Test
	public void test_237() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(3);
		instance.setK(3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, instance.tipo);
	}

	@Test
	public void test_238() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(4);
		instance.setK(1);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_239() {
		TriTyp instance = new TriTyp();
		instance.setI(2);
		instance.setJ(2);
		instance.setK(1);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, instance.tipo);
	}

	@Test
	public void test_240() {
		TriTyp instance = new TriTyp();
		instance.setI(2);
		instance.setJ(1);
		instance.setK(2);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ISOSCELES, instance.tipo);
	}

	@Test
	public void test_241() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(1);
		instance.setK(1);
		int actual = instance.getTipo();
		assertEquals(TriTyp.EQUILATERO, instance.tipo);
	}

	@Test
	public void test_242() {
		TriTyp instance = new TriTyp();
		instance.setI(2);
		instance.setJ(3);
		instance.setK(4);
		int actual = instance.getTipo();
		assertEquals(TriTyp.ESCALENO, instance.tipo);
	}

	@Test
	public void test_243() {
		TriTyp instance = new TriTyp();
		instance.setI(1);
		instance.setJ(2);
		instance.setK(3);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);

	}

	@Test
	public void test_244() {

		TriTyp instance = new TriTyp();
		instance.i = -1;
		instance.j = -1;
		instance.k = -1;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_245() {

		TriTyp instance = new TriTyp();
		instance.i = 2;
		instance.j = 2;
		instance.k = 2;
		instance.tipo = 3;
		int actual = instance.getTipo();
		assertEquals(TriTyp.EQUILATERO, instance.tipo);
	}

	@Test
	public void test_246() {

		TriTyp instance = null;
		instance = new TriTyp();
		instance.setI(3);
		instance.setJ(6);
		instance.setK(17);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test
	public void test_247() {

		TriTyp instance = null;
		instance = new TriTyp();
		int arg1 = 100;
		instance.setI(100);
		instance.setJ(6);
		instance.setK(17);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test
	public void test_248() {

		TriTyp instance = null;
		instance = new TriTyp();
		instance.setI(3);
		instance.setJ(100);
		instance.setK(40);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test
	public void test_249() {

		TriTyp instance = null;
		instance = new TriTyp();
		instance.setI(4);
		instance.setJ(4);
		instance.setK(9);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test
	public void test_250() {

		TriTyp instance = null;
		instance = new TriTyp();
		instance.setI(4);
		instance.setJ(9);
		instance.setK(4);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test
	public void test_251() {

		TriTyp instance = null;
		instance = new TriTyp();
		instance.setI(9);
		instance.setJ(4);
		instance.setK(4);
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, actual);
	}

	@Test
	public void test_252() {

		TriTyp instance = new TriTyp();
		instance.i = -1;
		instance.j = 2;
		instance.k = 2;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_253() {

		TriTyp instance = new TriTyp();
		instance.i = 2;
		instance.j = -1;
		instance.k = 2;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_254() {

		TriTyp instance = new TriTyp();
		instance.i = 2;
		instance.j = 2;
		instance.k = -1;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_255() {

		TriTyp instance = new TriTyp();
		instance.i = 0;
		instance.j = 1;
		instance.k = 1;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_256() {

		TriTyp instance = new TriTyp();
		instance.i = 1;
		instance.j = 0;
		instance.k = 1;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_257() {

		TriTyp instance = new TriTyp();
		instance.i = 1;
		instance.j = 1;
		instance.k = 0;
		int actual = instance.getTipo();
		assertEquals(TriTyp.NO_TRIANGULO, instance.tipo);
	}

	@Test
	public void test_258() {

		TriTyp instance = new TriTyp();
		instance.setI(TriTyp.EQUILATERO);
		instance.setJ(TriTyp.ESCALENO);
		instance.setK(TriTyp.ISOSCELES);
		assertEquals(TriTyp.NO_TRIANGULO, instance.getTipo());
	}

	@Test
	public void test_259() {

		TriTyp instance = new TriTyp();
		instance.setI(instance.EQUILATERO);
		instance.setJ(instance.ESCALENO);
		instance.setK(instance.ISOSCELES);
		assertEquals(instance.NO_TRIANGULO, instance.getTipo());
	}

}
